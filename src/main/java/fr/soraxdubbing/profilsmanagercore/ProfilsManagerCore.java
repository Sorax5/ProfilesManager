package fr.soraxdubbing.profilsmanagercore;

import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.Manager.CraftUserManager;
import fr.soraxdubbing.profilsmanagercore.event.Loader;
import fr.soraxdubbing.profilsmanagercore.profil.*;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public final class ProfilsManagerCore extends JavaPlugin {

    private List<CraftUser> users;
    private CraftUserManager manager;
    private static ProfilsManagerCore INSTANCE;

    @Override
    public void onEnable() {

        int pluginId = 15930; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

        // Plugin startup logic
        File userFile = new File(getDataFolder().getAbsolutePath() + "/" +"user");
        this.getDataFolder().mkdir();
        userFile.mkdir();
        this.saveDefaultConfig();

        this.manager = new CraftUserManager(userFile.getAbsolutePath(),this);
        this.users = new ArrayList<>();

        this.getServer().getPluginManager().registerEvents(new Loader(this,this.getManager(),this.getUsers()), this);

        profilsRegister();

        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            CraftUser user = getUser(onlinePlayer.getUniqueId());
            if (user != null) {
                user.getActualProfil().UpdateProfil(onlinePlayer, this);
                manager.saveCraftUser(user);
                getUsers().remove(user);
            }
        }
    }

    private void profilsRegister(){
        PluginCommand profilsCommand = getCommand("profils");
        profilsCommand.setExecutor(new ProfilsCommand(this));
        profilsCommand.setTabCompleter(new ProfilCompletion());

        PluginCommand profilCommand = getCommand("profil");
        profilCommand.setExecutor(new ProfilCommand(this));
        profilCommand.setTabCompleter(new ProfilCompletion());

        PluginCommand admin = getCommand("admin");
        admin.setExecutor(new AdminCommand(this));
        admin.setTabCompleter(new AdminCompletion());
    }

    /**
     * Get list of loaded users
     * @return List of loaded users
     */
    public List<CraftUser> getUsers(){
        return this.users;
    }

    /**
     * Get user by player id
     * @return CraftUser
     */
    public CraftUser getUser(UUID identifier){
        for(CraftUser user : this.users){
            if(user.getPlayerUuid().equals(identifier)){
                return user;
            }
        }
        return null;
    }

    /**
     * get CraftUserManager who manage all users
     * @return CraftUserManager
     */
    public CraftUserManager getManager() {
        return manager;
    }

    /**
     * Get instance of ProfilsManagerCore
     * @return ProfilsManagerCore
     */
    public static ProfilsManagerCore getInstance() {
        return ProfilsManagerCore.INSTANCE;
    }
}
