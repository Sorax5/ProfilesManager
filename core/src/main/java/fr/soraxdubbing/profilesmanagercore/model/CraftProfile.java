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
     * Create a new profile
     * @param name the profile's name
     */
    public CraftProfile(String name) {
        this.name = name;
        this.date = LocalDate.now().toString();
        this.addons = new ArrayList<>();
        this.uuid = UUID.randomUUID();
    }

    /**
     * Create a new profile by copy
     * @param profile the profile to copy
     * @param name the profile's name
     */
    public CraftProfile(CraftProfile profile, String name) {
        this.name = name;
        this.date = LocalDate.now().toString();
        this.addons = profile.getAddons();
        this.uuid = UUID.randomUUID();
    }

    /**
     * Get the profile's name
     * @return the profile's name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the profile's name
     * @param name the profile's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the creation date of the profile
     * @return the creation date of the profile
     */
    public String getDate() {
        return date;
    }

    /**
     * Get the creation date of the profile
     * @return the creation date of the profile
     */
    public String getDateString(){
        return this.date;
    }

    /**
     * describe the profile and addons
     * @return the profile's description
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
     * Update a player's profile
     * @param player the player to update
     * @param plugin the plugin
     */
    public void UpdateProfile(Player player, ProfilesManagerCore plugin){
        ProfileUpdateEvent event = new ProfileUpdateEvent(player,this);
        Bukkit.getPluginManager().callEvent(event);

        for (AddonData addon : this.getAddons()) {
            addon.updateAddonData(player,plugin);
        }
    }

    /**
     * Load the player's profile in minecraft
     * @param player the player to load
     * @param plugin the plugin
     */
    public void LoadingProfile(Player player, ProfilesManagerCore plugin){
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
        return this.addons.stream().filter(addon -> addon.getAddonName().equals(addonsName)).findFirst().orElse(null);
    }

    /**
     * Get list of addons
     * @return the list of addons
     */
    public List<AddonData> getAddons(){
        return this.addons;
    }

    /**
     * Add an addon to the profile
     * @param addon the addon to add
     */
    public void addAddon(AddonData addon){
        this.addons.add(addon);
    }

    /**
     * Remove an addon from the profile
     * @param addon the addon to remove
     */
    public void removeAddon(AddonData addon){
        this.addons.remove(addon);
    }

    /**
     * Remove an addon from the profile by name
     * @param addonName the addon's name
     */
    public void removeAddon(String addonName){
        this.addons.removeIf(addon -> addon.getAddonName().equals(addonName));
    }

    /**
     * verify if the profile has addon by name
     * @param addonName the addon's name
     * @return true if the profile has the addon
     */
    public Boolean hasAddon(String addonName){
        return addons.stream().anyMatch(addon -> addon.getAddonName().equals(addonName));
    }

    /**
     * verify if the profile has addon by class
     * @param addonClass the addon's class
     * @return true if the profile has the addon
     */
    public boolean hasAddon(Class<AddonData> addonClass){
        return addons.stream().anyMatch(addon -> addon.getClass().equals(addonClass));
    }

    /**
     * Get the profile's uuid
     * @return the profile's uuid
     */
    public UUID getUuid() {
        return uuid;
    }
}
