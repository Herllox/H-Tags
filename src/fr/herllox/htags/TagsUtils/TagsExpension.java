package fr.herllox.htags.TagsUtils;

import fr.herllox.htags.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class TagsExpension extends PlaceholderExpansion {

    private Main plugin;

    public TagsExpension(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier(){
        return "highsky";
    }

    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier){

        if(p== null){
            return "";
        }

        // %highsky_tag%
        // <identifier>_<placeholder>
        if(identifier.equals("tag")){
            return "";
        }

        return null;
    }


}
