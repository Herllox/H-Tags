package fr.herllox.htags.commands;

import fr.herllox.htags.Main;
import fr.herllox.htags.TagsUtils.TagsExpension;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TagCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 3){
                if(args[0].equalsIgnoreCase("set")){
                    String target = args[1];
                   if(Bukkit.getPlayer(target) != null){
                       String tagWork = args[2];
                       if (tagWork != null){
                           Player targetPlayer = Bukkit.getPlayer(target);
                           String tag = tagWork.replace("&", "§");
                           Main.map.put(targetPlayer.getUniqueId(), tag);
                           p.sendMessage("§6§lHigh§b§lSky " + "§bVous venez de definir le tag de §l§6[" + targetPlayer.getDisplayName() + "]§r");
                       }
                   }
                }
            }else if(args.length == 1){
                if (args[0].equalsIgnoreCase("help")){
                    p.sendMessage("§6§lHigh§b§lSky " + "§bVous avez a votre disposition la commande /tag set <Player> <tag>");
                }
            }
        }

        return false;
    }

}
