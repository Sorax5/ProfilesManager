package fr.soraxdubbing.profilsmanagercore.profil;

import fr.soraxdubbing.profilsmanagercore.Addon.AddonData;
import fr.soraxdubbing.profilsmanagercore.Manager.ItemManager;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
import fr.soraxdubbing.profilsmanagercore.event.ProfilLoadedEvent;
import fr.soraxdubbing.profilsmanagercore.event.ProfilUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDate;
import java.util.*;

public class CraftProfil {
    private transient List<AddonData> addons;
    private String name;
    private final String date;
    private double money;
    private List<String> permission;

    public CraftProfil(String name) {
        this.name = name;
        this.money = 0;
        this.date = LocalDate.now().toString();
        this.permission = new ArrayList<>();
        this.addons = new ArrayList<>();
    }

    public CraftProfil(CraftProfil profil, String name) {
        this.name = name;
        this.date = LocalDate.now().toString();
        this.money = profil.getMoney();
        this.permission = profil.getPermission();
        this.addons = profil.getAddons();
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

    public void setMoney(double money){
        this.money = money;
    }

    public double getMoney(){
        return this.money;
    }

    public void setPermission(List<String> permission){
        this.permission = permission;
    }

    public List<String> getPermission(){
        return this.permission;
    }

    public String getDateString(){
        return this.date;
    }

    @Override
    public String toString() {
        StringBuilder list = new StringBuilder();

        String perm = "§7";
        for (String s : getPermission()) {
            list.append(perm).append(" - ").append(s).append('\n');
        }

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

        this.setMoney(plugin.getEconomy().getBalance(player));

        List<String> permissions = new ArrayList<>(Arrays.asList(plugin.getPermission().getPlayerGroups(player)));

        for (String permission : permissions) {
            if (!this.getPermission().contains(permission)) {
                this.getPermission().add(permission);
            }
        }

        this.setPermission(permissions);

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

        plugin.getEconomy().withdrawPlayer(player, plugin.getEconomy().getBalance(player));
        plugin.getEconomy().depositPlayer(player, this.getMoney());

        for (String playerGroup : plugin.getPermission().getPlayerGroups(player)) {
            plugin.getLogger().info(playerGroup);
            plugin.getPermission().getPrimaryGroup(player);
        }

        for (String permission : this.getPermission()) {
            plugin.getPermission().playerAdd(player, permission);
        }

        for (AddonData addon : this.getAddons()) {
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

    public void addAddon(AddonData addon){
        this.addons.add(addon);
    }

    public void removeAddon(AddonData addon){
        this.addons.remove(addon);
    }

    public List<AddonData> getAddons(){
        return this.addons;
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
