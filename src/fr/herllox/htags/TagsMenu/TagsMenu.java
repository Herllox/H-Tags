package fr.herllox.htags.TagsMenu;

import fr.herllox.htags.TagsUtils.UtilsTags;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TagsMenu implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        Player p = (Player)sender;


        Inventory inv = Bukkit.createInventory(null, 27, "§6§l>> §bTags §6§l<<");

        UtilsTags.TagsMenu("&6&lHigh&b&lSky", "highsky", p, 100000, inv, 0);
        UtilsTags.TagsMenu("&d2K20", "years", p, 150000, inv, 1);


        p.openInventory(inv);


        return false;
    }

}
