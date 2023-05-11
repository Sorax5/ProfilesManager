package fr.soraxdubbing.profilesmanagercore.model;

import fr.soraxdubbing.profilesmanagercore.ProfilesManagerCore;
import fr.soraxdubbing.profilesmanagercore.library.ProfileLoadedEvent;
import fr.soraxdubbing.profilesmanagercore.library.ProfileUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * class that represent a profil
 */
public class CraftProfile {
    private List<AddonData> addons;
    private String name;
    private final String date;
    private UUID uuid;

    /**
     * Create a new profil
     * @param name the profil's name
     */
    public CraftProfile(String name) {
        this.name = name;
        this.date = LocalDate.now().toString();
        this.addons = new ArrayList<>();
        this.uuid = UUID.randomUUID();
    }

    /**
     * Create a new profil by copy
     * @param profil the profil to copy
     * @param name the profil's name
     */
    public CraftProfile(CraftProfile profil, String name) {
        this.name = name;
        this.date = LocalDate.now().toString();
        this.addons = profil.getAddons();
        this.uuid = UUID.randomUUID();
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
     * describe the profil and addons
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
     * @param plugin the plugin
     */
    public void UpdateProfil(Player player, ProfilesManagerCore plugin){
        ProfileUpdateEvent event = new ProfileUpdateEvent(player,this);
        Bukkit.getPluginManager().callEvent(event);

        for (AddonData addon : this.getAddons()) {
            addon.updateAddonData(player,plugin);
        }
    }

    /**
     * Load the player's profil in minecraft
     * @param player the player to load
     * @param plugin the plugin
     */
    public void LoadingProfil(Player player, ProfilesManagerCore plugin){
        this.getAddons().removeIf(Objects::isNull);

        ProfileLoadedEvent event = new ProfileLoadedEvent(player,this);
        Bukkit.getPluginManager().callEvent(event);

        for (AddonData addon : this.getAddons()) {
            addon.loadAddonData(player,plugin);
        }
    }

    /**
     * Get the addon by name
     * @param addonsName the addon's name
     * @return the addon
     */
    public AddonData getAddon(String addonsName){
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
     * Remove an addon from the profil by name
     * @param addonName the addon's name
     */
    public void removeAddon(String addonName){
        this.addons.removeIf(addon -> addon.getAddonName().equals(addonName));
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

    /**
     * Get the profil's uuid
     * @return the profil's uuid
     */
    public UUID getUuid() {
        return uuid;
    }
}
