package fr.soraxdubbing.profilesplayerstatistics.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SerializableLocation {
    private int x,y,z;
    private float yaw,pitch;
    private String world;

    /**
     * Transform a Location to a SerializableLocation
     * @param location
     */
    public SerializableLocation(Location location){
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.world = location.getWorld().getName();
    }

    public Location toLocation(){
        return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public String getWorld() {
        return world;
    }

}
