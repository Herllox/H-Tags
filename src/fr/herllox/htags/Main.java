package fr.herllox.htags;

import fr.herllox.htags.TagsUtils.Database;
import fr.herllox.htags.TagsMenu.TagsEvent;
import fr.herllox.htags.TagsMenu.TagsMenu;
import fr.herllox.htags.TagsUtils.TagsExpension;
import fr.herllox.htags.commands.TagCommand;
import fr.herllox.htags.listeners.TagsListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;
    public static HashMap<UUID, String> map = new HashMap<>();


    @Override
    public void onEnable() {

        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        //Enregistrement de l'extension PlaceHolderAPI
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new TagsExpension(this).register();
        }
        try {reloadTag();}catch(SQLException e){}

        Bukkit.getPluginManager().registerEvents(new TagsListener(), this);
        getCommand("tag").setExecutor(new TagCommand());

        Bukkit.getPluginManager().registerEvents(new TagsEvent(), this);

        getCommand("tags").setExecutor(new TagsMenu());

    }

    @Override
    public void onDisable() {
        try{saveActiveTag();}catch(SQLException e){e.printStackTrace();}
        map.clear();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private void reloadTag() throws SQLException {
        Connection cn = Database.connect();
        String query = "SELECT Tag FROM Users WHERE UUID = ?";
        PreparedStatement ps = cn.prepareStatement(query);
        for (Player online : Bukkit.getOnlinePlayers()){
            ps.setString(1, online.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String tag = rs.getString("Tag");
                if(tag != null){
                    map.put(online.getUniqueId(), tag);
                }

            }
        }
    }

    private void saveActiveTag() throws SQLException {
        Connection cn = Database.connect();
        String query = "SELECT Tag FROM Users WHERE UUID = ?";
        PreparedStatement ps = cn.prepareStatement(query);

        for (Player p : Bukkit.getOnlinePlayers()){
            if(Main.map.containsKey(p.getUniqueId())){
                String tag = Main.map.get(p.getUniqueId());
                if(tag != null && tag != ""){
                    ps.setString(1, p.getUniqueId().toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()){
                        if (!tag.equalsIgnoreCase(rs.getString("Tag"))){
                            System.out.println("Update dans la BDD" + " >> " + tag + " >> " + rs.getString("Tag"));
                            query = "UPDATE Users SET Tag = ? WHERE UUID = ?";
                            ps = cn.prepareStatement(query);
                            ps.setString(1, tag);
                            ps.setString(2, p.getUniqueId().toString());
                            ps.executeUpdate();
                        }
                    }else{
                        System.out.println("Inscription dans la BDD");
                        query = "INSERT INTO Users (UUID, Tag) VALUES (?, ?)";
                        ps = cn.prepareStatement(query);
                        ps.setString(1, p.getUniqueId().toString());
                        ps.setString(2, tag);
                        ps.executeUpdate();
                    }

                    Main.map.remove(p.getUniqueId());
                }
            }
        }
        ps.close();
        cn.close();
    }

}
