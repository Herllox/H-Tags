package fr.herllox.htags.TagsUtils;

import fr.herllox.htags.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class UtilsTags {


    public static void TagsMenu(String name, String perm, Player p, Integer price, Inventory inv, Integer slot){

        ItemStack i = new ItemStack(Material.NAME_TAG);
        ItemMeta i2 = i.getItemMeta();
        i2.setDisplayName(name.replace("&", "§"));
        i2.setLore(Arrays.asList("","§aPrix: §e" + price));
        if(p.hasPermission("tags." + perm)){
            i2.setLore(Arrays.asList("","§aClique-droit pour séléctionner"));
        }

        i.setItemMeta(i2);

        inv.setItem(slot, i);

    }



    public static void TagsBuy(Player p, Integer price, String name){



        Main.econ.withdrawPlayer(p, price);
        p.sendMessage("§6§lHigh§b§lSky §7§l>> §aTu viens d'acheté le tags "+name+" §apour §e"+price+"$§a.");
        p.closeInventory();

    }


}
