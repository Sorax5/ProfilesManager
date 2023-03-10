package fr.soraxdubbing.profilsmanagercore.model;

import fr.soraxdubbing.profilsmanagercore.addon.AddonData;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
import fr.soraxdubbing.profilsmanagercore.library.ProfilLoadedEvent;
import fr.soraxdubbing.profilsmanagercore.library.ProfilUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.util.*;

public class CraftProfil {
    private List<AddonData> addons;
    private String name;
    private final String date;

    /**
     * Create a new profil
     * @param name the profil's name
     */
    public CraftProfil(String name) {
        this.name = name;
        this.date = LocalDate.now().toString();
        this.addons = new ArrayList<>();
    }

    /**
     * Create a new profil by copy
     * @param profil the profil to copy
     * @param name the profil's name
     */
    public CraftProfil(CraftProfil profil, String name) {
        this.name = name;
        this.date = LocalDate.now().toString();
        this.addons = new ArrayList<>();
    }

    /**
     * Get the profil's name
     * @return the profil's name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the profil's name
     * @param name the profil's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the creation date of the profil
     * @return the creation date of the profil
     */
    public String getDate() {
        return date;
    }

    /**
     * Get the creation date of the profil
     * @return the creation date of the profil
     */
    public String getDateString(){
        return this.date;
    }

    /**
     * describe the profil & addons
     * @return the profil's description
     */
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

        this.getAddons().removeIf(Objects::isNull);

        for (AddonData addon : this.getAddons()) {
            addon.loadAddonData(player,plugin);
        }
    }

    /**
     * Get the addon by name
     * @param addonsName the addon's name
     * @return the addon
     */
    public AddonData getAddons(String addonsName){
        AddonData addon = null;
        for(AddonData data : this.addons){
            if(data.getAddonName().equals(addonsName)){
                addon = data;
            }
        }
        return addon;
    }

    /**
     * Get list of addons
     * @return the list of addons
     */
    public List<AddonData> getAddons(){
        return this.addons;
    }

    /**
     * Add an addon to the profil
     * @param addon the addon to add
     */
    public void addAddon(AddonData addon){
        this.addons.add(addon);
    }

    /**
     * Remove an addon from the profil
     * @param addon the addon to remove
     */
    public void removeAddon(AddonData addon){
        this.addons.remove(addon);
    }

    /**
     * verify if the profil has addon by name
     * @param addonName the addon's name
     * @return true if the profil has the addon
     */
    public Boolean hasAddon(String addonName){
        for(AddonData addon : this.addons){
            if(addon.getAddonName().equals(addonName)){
                return true;
            }
        }
        return false;
    }

    /**
     * verify if the profil has addon by class
     * @param addonClass the addon's class
     * @return true if the profil has the addon
     */
    public boolean hasAddon(Class<AddonData> addonClass){
        for(AddonData addon : this.addons){
            if(addon.getClass().equals(addonClass)){
                return true;
            }
        }
        return false;
    }
}
