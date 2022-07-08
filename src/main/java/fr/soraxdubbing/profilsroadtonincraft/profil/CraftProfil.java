package fr.soraxdubbing.profilsroadtonincraft.profil;

import com.google.gson.annotations.SerializedName;
import fr.soraxdubbing.profilsroadtonincraft.Addon.AddonData;
import fr.soraxdubbing.profilsroadtonincraft.CraftUser.CraftUser;
import fr.soraxdubbing.profilsroadtonincraft.Manager.ItemManager;
import fr.soraxdubbing.profilsroadtonincraft.ProfilsRoadToNincraft;
import fr.soraxdubbing.profilsroadtonincraft.event.ProfilLoadedEvent;
import fr.soraxdubbing.profilsroadtonincraft.event.ProfilUpdateEvent;
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

    public static void UpdateActualProfil(Player player, ProfilsRoadToNincraft plugin){
        CraftProfil profil = Objects.requireNonNull(plugin.getUser(player.getUniqueId())).getActualProfil();

        if(profil != null){
            UpdateProfil(profil,player,plugin);
        }
    }

    /**
     * Update a player's profil
     * @param profil the profil to update
     * @param player the player to update
     */
    public static void UpdateProfil(CraftProfil profil,Player player,ProfilsRoadToNincraft plugin){
        if(profil != null){
            ProfilUpdateEvent event = new ProfilUpdateEvent(player,profil);
            Bukkit.getPluginManager().callEvent(event);
            profil.setLastLocation(player.getLocation());

            ItemStack[] inventory = player.getInventory().getContents();
            if (inventory != null) {
                profil.setInventory(inventory);
            }

            ItemStack[] enderchest = player.getEnderChest().getContents();
            if (enderchest != null) {
                profil.setEnderChest(enderchest);
            }

            profil.setActualHealth(player.getHealth());

            profil.setMaximumHealth(player.getMaxHealth());

            profil.setGameMode(player.getGameMode());

            profil.setFoodLevel(player.getFoodLevel());

            profil.setMoney(plugin.getEconomy().getBalance(player));

            List<String> permissions = new ArrayList<>(Arrays.asList(plugin.getPermission().getPlayerGroups(player)));

            profil.setPermission(permissions);
        }
    }

    /**
     * Load the player's profil in minecraft
     * @param player the player to load
     * @param user the user to load
     */
    public static void LoadingProfil(Player player, CraftUser user,ProfilsRoadToNincraft plugin){
        ProfilLoadedEvent event = new ProfilLoadedEvent(player,user);
        Bukkit.getPluginManager().callEvent(event);

        CraftProfil profil = user.getActualProfil();
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(profil.getMaximumHealth());
        player.setHealth(profil.getActualHealth());

        ItemStack[] inventory = profil.getInventory();
        if (inventory != null) {
            player.getInventory().setContents(inventory);
        }

        ItemStack[] enderchest = profil.getEnderChest();
        if (enderchest != null) {
            player.getEnderChest().setContents(enderchest);
        }
        player.teleport(profil.getLastLocation());
        player.setFoodLevel(profil.getFoodLevel());

        plugin.getEconomy().withdrawPlayer(player, plugin.getEconomy().getBalance(player));
        plugin.getEconomy().depositPlayer(player, profil.getMoney());

        player.setGameMode(profil.getGameMode());

        for (String playerGroup : profil.getPermission()) {
            plugin.getPermission().playerAddGroup(player, playerGroup);
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
