package fr.soraxdubbing.profilesplayerstatistics;

import fr.soraxdubbing.profilesmanagercore.model.UsersManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ProfilesPlayerStatistics extends JavaPlugin {
    private static ProfilesPlayerStatistics instance;

    @Override
    public void onEnable() {
        UsersManager.getInstance().registerClass(PlayerStats.class);
        instance = this;
        getServer().getPluginManager().registerEvents(new EventRegister(), this);
    }

    public static ProfilesPlayerStatistics getInstance() {
        return instance;
    }
}
