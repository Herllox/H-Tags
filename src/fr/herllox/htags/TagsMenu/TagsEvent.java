package fr.herllox.htags.TagsMenu;

import fr.herllox.htags.Main;
import fr.herllox.htags.TagsUtils.UtilsTags;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TagsEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){

        Player p = (Player) e.getWhoClicked();

        if(e.getClickedInventory() == null){
            e.setCancelled(true);
        }
        if(e.getView().getTitle().equalsIgnoreCase("§6§l>> §bTags §6§l<<")) {
            e.setCancelled(true);
            if (e.getInventory() == null || e.getCurrentItem() == null || e.getCurrentItem().getType() == null || !e.getCurrentItem().hasItemMeta()) {
                p.closeInventory();
                return;
            }

            switch (e.getCurrentItem().getType()){
                case NAME_TAG:

                    int i = e.getSlot();

                    p.sendMessage(String.valueOf(i));

                    if(e.getSlot() == i){

                        String lore = String.valueOf(e.getCurrentItem().getItemMeta().getLore());
                        String lores = lore.replace("§aPrix: §e", "").replace(" ", "").replace("]", "");
                        String[] buy = lores.split(",");
                        int price = Integer.parseInt(buy[1]);

                        if(lore.contains("§aClique-droit")){



                            /*

                            Zone où il faut mettre le code pour la selection du TAGS


                             */




                        }else{
                            if(Main.econ.has(p, price)){
                                String name = e.getCurrentItem().getItemMeta().getDisplayName();
                                UtilsTags.TagsBuy(p, price, name);
                            }else{

                                p.sendMessage("§6§lHigh§b§lSky §7§l>> §cTu n'as pas assez d'argent.");

                            }
                        }






                    }
                    break;
                default:
                    break;
            }

        }


    }

}
