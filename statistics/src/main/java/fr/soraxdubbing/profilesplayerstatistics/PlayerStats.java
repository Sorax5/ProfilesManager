package fr.soraxdubbing.profilesplayerstatistics;

import fr.soraxdubbing.profilesmanagercore.model.AddonData;
import fr.soraxdubbing.profilesmanagercore.storage.ItemManager;
import fr.soraxdubbing.profilesplayerstatistics.vanilla.SerializableLocation;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Addon data for ProfilsManagerCore
 */
public class PlayerStats extends AddonData {

    private List<String> enderChest;
    private List<String> armor;
    private List<String> storage;
    private List<String> extra;
    private double maximumHealth;
    private double actualHealth;
    private int foodLevel;
    private int level;
    private float experience;
    private float exhaustion;
    private float saturation;
    private SerializableLocation lastLocation;
    private GameMode gameMode;

    /**
     * Addon data for player stats
     * @param player the based player
     */
    public PlayerStats(Player player) {
        super("playerstats");
        this.enderChest = new ArrayList<>();
        this.armor = new ArrayList<>();
        this.storage = new ArrayList<>();
        this.extra = new ArrayList<>();
        this.maximumHealth = 20;
        this.actualHealth = 20;
        this.foodLevel = 20;
        this.setLastLocation(player.getLocation());
        this.gameMode = GameMode.SURVIVAL;
        this.exhaustion = player.getExhaustion();
        this.saturation = player.getSaturation();
        this.level = player.getLevel();
        this.experience = player.getExp();
    }

    @Override
    public void updateAddonData(Player player, JavaPlugin javaPlugin) {
        this.setLastLocation(player.getLocation());

        this.setEnderChest(player.getEnderChest().getContents());
        this.setArmor(player.getInventory().getArmorContents());
        this.setStorage(player.getInventory().getStorageContents());
        this.setExtra(player.getInventory().getExtraContents());

        this.setActualHealth(player.getHealth());
        this.setMaximumHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        this.setGameMode(player.getGameMode());
        this.setFoodLevel(player.getFoodLevel());
        this.setLevel(player.getLevel());
        this.setExperience(player.getExp());
        this.setExhaustion(player.getExhaustion());
        this.setSaturation(player.getSaturation());
    }

    @Override
    public void loadAddonData(Player player, JavaPlugin javaPlugin) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(this.getMaximumHealth());
        player.setHealth(this.getActualHealth());

        player.getEnderChest().setContents(this.getEnderChest());
        player.getInventory().setArmorContents(this.getArmor());
        player.getInventory().setStorageContents(this.getStorage());
        player.getInventory().setExtraContents(this.getExtra());

        player.teleport(this.getLastLocation());
        player.setFoodLevel(this.getFoodLevel());
        player.setGameMode(this.getGameMode());

        player.setLevel(this.getLevel());
        player.setExp(this.getExperience());
        player.setExhaustion(this.getExhaustion());
        player.setSaturation(this.getSaturation());
    }

    public ItemStack[] getEnderChest() {
        return ItemManager.StringListToItemStack(this.enderChest);
    }
    public  void setEnderChest(ItemStack[] inventory) {
        this.enderChest = ItemManager.ItemStackToStringList(inventory);
    }

    public ItemStack[] getArmor() {
        return ItemManager.StringListToItemStack(this.armor);
    }
    public void setArmor(ItemStack[] inventory) {
        this.armor = ItemManager.ItemStackToStringList(inventory);
    }

    public ItemStack[] getStorage() {
        return ItemManager.StringListToItemStack(this.storage);
    }
    public void setStorage(ItemStack[] inventory) {
        this.storage = ItemManager.ItemStackToStringList(inventory);
    }

    public ItemStack[] getExtra() {
        return ItemManager.StringListToItemStack(this.extra);
    }
    public void setExtra(ItemStack[] inventory) {
        this.extra = ItemManager.ItemStackToStringList(inventory);
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
        this.lastLocation = new SerializableLocation(location);
    }
    public Location getLastLocation(){
        return lastLocation.toLocation();
    }

    public GameMode getGameMode(){
        return this.gameMode;
    }

    public void setGameMode(GameMode gameMode){
        this.gameMode = gameMode;
    }

    public float getExhaustion() {
        return exhaustion;
    }

    public void setExhaustion(float exhaustion) {
        this.exhaustion = exhaustion;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getExperience() {
        return experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        StringBuilder description = new StringBuilder().append("§r⇨§a§lPlayerStats:").append("\n");
        description.append("   §7").append(getActualHealth()).append(" §clife§r -> ").append(getMaximumHealth()).append(" §clide§r").append("\n");
        description.append("    §7§lx: §r§3").append(lastLocation.getX()).append(" §7§ly: §r§3").append(lastLocation.getY()).append(" §7§lz: §r§3").append(lastLocation.getZ()).append("\n");
        description.append("    §7Food: ").append(getFoodLevel()).append("§6").append("§r\n");
        description.append("    §7Exhaustion: ").append(getExhaustion()).append("§6").append("§r\n");
        description.append("    §7Saturation: ").append(getSaturation()).append("§6").append("§r\n");
        description.append("    §7Level: ").append(getLevel()).append("§6⚔").append("§r\n");
        description.append("    §7Experience: ").append(getExperience()).append("§6⚔").append("§r\n");

        int EnderChestCount = Arrays.stream(getEnderChest().clone())
                .filter(s -> (s != null))
                .toArray(ItemStack[]::new).length;

        int ArmorCount = Arrays.stream(getArmor().clone())
                .filter(s -> (s != null))
                .toArray(ItemStack[]::new).length;

        int StorageCount = Arrays.stream(getStorage().clone())
                .filter(s -> (s != null))
                .toArray(ItemStack[]::new).length;

        int ExtraCount = Arrays.stream(getExtra().clone())
                .filter(s -> (s != null))
                .toArray(ItemStack[]::new).length;

        description.append("    §7EnderChest: ").append(EnderChestCount).append("✉§6").append("§r\n");
        description.append("    §7Armor: ").append(ArmorCount).append("✉§r\n");
        description.append("    §7Storage: ").append(StorageCount).append("✉§r\n");
        description.append("    §7Extra: ").append(ExtraCount).append("✉§r\n");
        description.append("    §eGamemode ").append(getGameMode()).append("\n");

        return description.toString();
    }
}
