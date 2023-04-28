package fr.soraxdubbing.profilesmanagercore;

import fr.soraxdubbing.profilesmanagercore.event.PlayerHandlerEvent;
import fr.soraxdubbing.profilesmanagercore.model.UsersManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Main class of ProfilsManagerCore
 */
public final class ProfilesManagerCore extends JavaPlugin {
    private static ProfilesManagerCore INSTANCE;

    @Override
    public void onEnable() {
        int pluginId = 15930;
        Metrics metrics = new Metrics(this, pluginId);
        this.getServer().getPluginManager().registerEvents(new PlayerHandlerEvent(), this);
        UsersManager.getInstance().loadFileUsers();
    }

    @Override
    public void onDisable() {
        UsersManager.getInstance().saveFileUsers();
    }

    /**
     * Enregistre les commandes du plugin (Intake)
     */
    @Override
    public void onLoad() {
        INSTANCE = this;

        getLogger().info("Création du fichier de configuration...");
        File userFile = new File(getDataFolder().getAbsolutePath() + "/users");

        if(this.getDataFolder().mkdir()){
            getLogger().info("Dossier de stockage des données créé !");
        }
        else {
            getLogger().info("Dossier de stockage des données déjà existant !");
        }

        if(userFile.mkdir()){
            getLogger().info("Dossier de stockage des utilisateurs créé !");
        }
        else {
            getLogger().info("Dossier de stockage des utilisateurs déjà existant !");
        }

        this.saveDefaultConfig();
        getLogger().info("Fichier de configuration créé !");
    }

    /**
     * Get instance of ProfilesManagerCore
     *
     * @return ProfilesManagerCore
     */
    public static ProfilesManagerCore getInstance() {
        return ProfilesManagerCore.INSTANCE;
    }
}
