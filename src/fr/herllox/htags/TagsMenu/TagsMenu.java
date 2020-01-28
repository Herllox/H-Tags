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
        UtilsTags.TagsMenu("&bSkyBlock", "skyb", p, 75000, inv, 2);
        UtilsTags.TagsMenu("&4L♥ve", "love", p, 100000, inv, 3);
        UtilsTags.TagsMenu("&c&lSupreme", "supreme", p, 10000000, inv, 4);


        p.openInventory(inv);


        return false;
    }

}
