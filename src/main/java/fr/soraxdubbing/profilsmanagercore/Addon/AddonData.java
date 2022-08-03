package fr.soraxdubbing.profilsmanagercore.Addon;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AddonData {

    private final String addonName;

    public AddonData(String addonName) {
        this.addonName = addonName;
    }

    public String getAddonName(){
        return this.addonName;
    }

    public abstract void updateAddonData(Player player, JavaPlugin plugin);

    public abstract void loadAddonData(Player player,JavaPlugin plugin);

}
