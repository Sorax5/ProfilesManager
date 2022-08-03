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
    private List<String> enderChest;
    private List<String> inventory;
    private final String date;
    private double maximumHealth;
    private double actualHealth;
    private int foodLevel;
    private int x;
    private int y;
    private int z;
    private float yaw;
    private float pitch;
    private String world;
    private GameMode gameMode;
    private double money;
    private List<String> permission;

    public CraftProfil(String name) {
        this.name = name;
        this.enderChest = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.maximumHealth = 20;
        this.actualHealth = 20;
        this.foodLevel = 20;
        this.setLastLocation(Bukkit.getServer().getWorld("world").getSpawnLocation());
        this.gameMode = GameMode.SURVIVAL;
        this.money = 0;
        this.date = LocalDate.now().toString();
        this.permission = new ArrayList<>();
        this.addons = new ArrayList<>();
    }

    public CraftProfil(CraftProfil profil, String name) {
        this.name = name;
        this.inventory = ItemManager.ItemStackToStringList(profil.getInventory());
        this.date = LocalDate.now().toString();
        this.setLastLocation(profil.getLastLocation());
        this.actualHealth = profil.getActualHealth();
        this.maximumHealth = profil.getMaximumHealth();
        this.gameMode = profil.getGameMode();
        this.foodLevel = profil.getFoodLevel();
        this.enderChest = ItemManager.ItemStackToStringList(profil.getEnderChest());
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

    public ItemStack[] getInventory() {
        return ItemManager.StringListToItemStack(this.inventory);
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = ItemManager.ItemStackToStringList(inventory);
    }

    public  ItemStack[] getEnderChest() {
        return ItemManager.StringListToItemStack(this.enderChest);
    }
    public  void setEnderChest(ItemStack[] inventory) {
        this.enderChest = ItemManager.ItemStackToStringList(inventory);
    }

    public String getDate() {
        return date;
    }

    public double getMaximumHealth() {
        return maximumHealth;
    }

    public void setMaximumHealth(double maximumHealth) {
        this.maximumHealth = maximumHealth;
    }

    public double getActualHealth() {
        return actualHealth;
    }

    public void setActualHealth(double actualHealth) {
        this.actualHealth = actualHealth;
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }

    public void setLastLocation(Location location){
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.world = location.getWorld().getName();
    }
    public Location getLastLocation(){
        return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public GameMode getGameMode(){
        return this.gameMode;
    }

    public void setGameMode(GameMode gameMode){
        this.gameMode = gameMode;
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
        StringBuilder list = new StringBuilder("§a§lStatistics: " + '\n' +
                "§7" + getMaximumHealth() + " §c❤§r " + "| §b" + getMoney() + " $" + '\n' +
                "§7§lx: §r§3" + x + " §7§ly: §r§3" + y + " §7§lz: §r§3" + z + '\n' +
                "§6" + getDateString() + '\n' +
                "§eGamemode " + getGameMode() + '\n' +
                "§a§lPermissions: " + '\n');

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
        this.setLastLocation(player.getLocation());

        ItemStack[] inventory = player.getInventory().getContents();
        if (inventory != null) {
            this.setInventory(inventory);
        }

        ItemStack[] enderchest = player.getEnderChest().getContents();
        if (enderchest != null) {
            this.setEnderChest(enderchest);
        }

        this.setActualHealth(player.getHealth());

        this.setMaximumHealth(player.getMaxHealth());

        this.setGameMode(player.getGameMode());

        this.setFoodLevel(player.getFoodLevel());

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

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(this.getMaximumHealth());
        player.setHealth(this.getActualHealth());

        ItemStack[] inventory = this.getInventory();
        if (inventory != null) {
            player.getInventory().setContents(inventory);
        }

        ItemStack[] enderchest = this.getEnderChest();
        if (enderchest != null) {
            player.getEnderChest().setContents(enderchest);
        }
        player.teleport(this.getLastLocation());
        player.setFoodLevel(this.getFoodLevel());

        plugin.getEconomy().withdrawPlayer(player, plugin.getEconomy().getBalance(player));
        plugin.getEconomy().depositPlayer(player, this.getMoney());

        player.setGameMode(this.getGameMode());

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
