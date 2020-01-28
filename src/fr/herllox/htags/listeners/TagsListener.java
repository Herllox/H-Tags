package fr.herllox.htags.listeners;

import fr.herllox.htags.Main;
import fr.herllox.htags.TagsUtils.Database;
import fr.herllox.htags.TagsUtils.TagsExpension;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TagsListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws SQLException {
        Player p = e.getPlayer();
        Connection cn = Database.connect();
        String query = "SELECT Tag FROM Users WHERE UUID = ?";
        PreparedStatement ps = cn.prepareStatement(query);
        ps.setString(1, p.getUniqueId().toString());
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String tags = rs.getString("Tag");
            if(tags != null){
                Main.map.put(p.getUniqueId(), tags);
            }
        }
        ps.close();
        cn.close();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) throws SQLException {

        Player p = e.getPlayer();
        if(Main.map.containsKey(p.getUniqueId())){
            String tag = Main.map.get(p.getUniqueId());
            if(tag != null && tag != ""){
                Connection cn = Database.connect();
                String query = "SELECT Tag FROM Users WHERE UUID = ?";
                PreparedStatement ps = cn.prepareStatement(query);
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

                ps.close();
                cn.close();
                Main.map.remove(p.getUniqueId());
            }
        }


    }

}
