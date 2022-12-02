package fr.soraxdubbing.profilsmanagercore;

import app.ashcon.intake.bukkit.BukkitIntake;
import app.ashcon.intake.bukkit.graph.BasicBukkitCommandGraph;
import app.ashcon.intake.fluent.DispatcherNode;
import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.Manager.CraftUserManager;
import fr.soraxdubbing.profilsmanagercore.Manager.Loader.JsonLoader;
import fr.soraxdubbing.profilsmanagercore.Manager.LoaderAdapter;
import fr.soraxdubbing.profilsmanagercore.Manager.Saver.JsonSaver;
import fr.soraxdubbing.profilsmanagercore.Manager.SaverAdapter;
import fr.soraxdubbing.profilsmanagercore.commands.AdminCommand;
import fr.soraxdubbing.profilsmanagercore.commands.ProfilsCommand;
import fr.soraxdubbing.profilsmanagercore.commands.profil.ProfilGetterCommand;
import fr.soraxdubbing.profilsmanagercore.commands.profil.ProfilSetterCommand;
import fr.soraxdubbing.profilsmanagercore.event.PlayerHandlerEvent;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public final class ProfilsManagerCore extends JavaPlugin {

    private List<CraftUser> users;
    private CraftUserManager manager;
    private static ProfilsManagerCore INSTANCE;
    private SaverAdapter saver;
    private LoaderAdapter loader;

    @Override
    public void onEnable() {
        INSTANCE = this;

        int pluginId = 15930; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

        // Plugin startup logic
        File userFile = new File(getDataFolder().getAbsolutePath() + "/users");
        this.getDataFolder().mkdir();
        userFile.mkdir();
        this.saveDefaultConfig();

        this.loader = new JsonLoader(this.getDataFolder().getAbsolutePath() + "/users");
        this.saver = new JsonSaver(this.getDataFolder().getAbsolutePath() + "/users");

        //this.manager = new CraftUserManager(userFile.getAbsolutePath(),this);
        this.users = new ArrayList<>();

        this.getServer().getPluginManager().registerEvents(new PlayerHandlerEvent(this,this.getManager(),this.getUsers()), this);
    }

    @Override
    public void onDisable() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            CraftUser user = getUser(onlinePlayer.getUniqueId());
            if (user != null) {
                user.getActualProfil().UpdateProfil(onlinePlayer, this);
                saver.save(user);
                getUsers().remove(user);
            }
        }
    }

    /**
     * Enregistre les commandes du plugin (Intake)
     */
    @Override
    public void onLoad() {
        BasicBukkitCommandGraph cmdGraph = new BasicBukkitCommandGraph();

        // ADMIN COMMANDS
        DispatcherNode admin = cmdGraph.getRootDispatcherNode().registerNode("admin");
        admin.registerCommands(new AdminCommand());

        // PROFIL COMMANDS
        DispatcherNode profil = cmdGraph.getRootDispatcherNode().registerNode("profil");
        DispatcherNode get = profil.registerNode("get");
        get.registerCommands(new ProfilGetterCommand());

        DispatcherNode set = profil.registerNode("set");
        set.registerCommands(new ProfilSetterCommand());

        // PROFILS COMMANDS
        cmdGraph.getRootDispatcherNode().registerCommands(new ProfilsCommand());

        BukkitIntake bukkitIntake = new BukkitIntake(this, cmdGraph);
        bukkitIntake.register();
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

    /**
     * Get SaverAdapter
     * @return SaverAdapter
     */
    public LoaderAdapter getLoader() {
        return loader;
    }

    /**
     * Get LoaderAdapter
     * @return LoaderAdapter
     */
    public SaverAdapter getSaver() {
        return saver;
    }
}
