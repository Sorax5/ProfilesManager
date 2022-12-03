package fr.soraxdubbing.profilsmanagercore.profil;

import fr.soraxdubbing.profilsmanagercore.Addon.AddonData;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
import fr.soraxdubbing.profilsmanagercore.API.ProfilLoadedEvent;
import fr.soraxdubbing.profilsmanagercore.API.ProfilUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.util.*;

public class CraftProfil {
    private List<AddonData> addons;
    private String name;
    private final String date;

    public CraftProfil(String name) {
        this.name = name;
        this.date = LocalDate.now().toString();
        this.addons = new ArrayList<>();
    }

    public CraftProfil(CraftProfil profil, String name) {
        this.name = name;
        this.date = LocalDate.now().toString();
        this.addons = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public String getDateString(){
        return this.date;
    }

    @Override
    public String toString() {
        StringBuilder list = new StringBuilder();

        for (AddonData addon : this.addons) {
            list.append(addon.toString());
        }

        return list.toString();
    }

    /**
     * Update a player's profil
     * @param player the player to update
     */
    public void UpdateProfil(Player player, ProfilsManagerCore plugin){
        ProfilUpdateEvent event = new ProfilUpdateEvent(player,this);
        Bukkit.getPluginManager().callEvent(event);

        for (AddonData addon : this.getAddons()) {
            addon.updateAddonData(player,plugin);
        }
    }

    /**
     * Load the player's profil in minecraft
     * @param player the player to load
     */
    public void LoadingProfil(Player player, ProfilsManagerCore plugin){
        ProfilLoadedEvent event = new ProfilLoadedEvent(player,this);
        Bukkit.getPluginManager().callEvent(event);

        for (AddonData addon : this.getAddons()) {
            plugin.getLogger().info("Loading addon " + addon.getAddonName());
            addon.loadAddonData(player,plugin);
        }
    }

    public AddonData getAddons(String addonsName){
        AddonData addon = null;
        for(AddonData data : this.addons){
            if(data.getAddonName().equals(addonsName)){
                addon = data;
            }
        }
        return addon;
    }
    public List<AddonData> getAddons(){
        return this.addons;
    }

    public void addAddon(AddonData addon){
        this.addons.add(addon);
    }

    public void removeAddon(AddonData addon){
        this.addons.remove(addon);
    }


    public void setAddons(List<AddonData> addons){
        this.addons = addons;
    }

    public Boolean hasAddon(String addonName){
        for(AddonData addon : this.addons){
            if(addon.getAddonName().equals(addonName)){
                return true;
            }
        }
        return false;
    }
}
