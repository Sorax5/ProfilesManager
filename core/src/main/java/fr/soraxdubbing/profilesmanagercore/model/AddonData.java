package fr.soraxdubbing.profilesmanagercore.model;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AddonData {

    private final String addonName;

    /**
     * constructor of AddonData
     * @param addonName the name of the addon
     */
    public AddonData(String addonName) {
        this.addonName = addonName;
    }

    /**
     * get the name of the addon
     * @return the name of the addon
     */
    public String getAddonName(){
        return this.addonName;
    }

    /**
     * update the data of the addon
     * @param player the player
     * @param plugin the plugin
     */
    public abstract void updateAddonData(Player player, JavaPlugin plugin);

    /**
     * load the data of the addon
     * @param player the player
     * @param plugin the plugin
     */
    public abstract void loadAddonData(Player player,JavaPlugin plugin);

}
