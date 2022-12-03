package fr.soraxdubbing.profilsmanagercore;

import app.ashcon.intake.bukkit.BukkitIntake;
import app.ashcon.intake.bukkit.graph.BasicBukkitCommandGraph;
import app.ashcon.intake.fluent.DispatcherNode;
import fr.soraxdubbing.profilsmanagercore.manager.UsersManager;
import fr.soraxdubbing.profilsmanagercore.commands.AdminCommand;
import fr.soraxdubbing.profilsmanagercore.commands.ProfilsCommand;
import fr.soraxdubbing.profilsmanagercore.commands.profil.ProfilGetterCommand;
import fr.soraxdubbing.profilsmanagercore.commands.profil.ProfilSetterCommand;
import fr.soraxdubbing.profilsmanagercore.event.PlayerHandlerEvent;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ProfilsManagerCore extends JavaPlugin {
    private static ProfilsManagerCore INSTANCE;

    @Override
    public void onEnable() {
        int pluginId = 15930;
        Metrics metrics = new Metrics(this, pluginId);

        this.getServer().getPluginManager().registerEvents(new PlayerHandlerEvent(), this);
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

        // FILE
        File userFile = new File(getDataFolder().getAbsolutePath() + "/users");
        this.getDataFolder().mkdir();
        userFile.mkdir();
        this.saveDefaultConfig();

        // INTAKE FRAMEWORK

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

        // LOAD USERS
        UsersManager.getInstance().loadFileUsers();
    }



    /**
     * Get instance of ProfilsManagerCore
     * @return ProfilsManagerCore
     */
    public static ProfilsManagerCore getInstance() {
        return ProfilsManagerCore.INSTANCE;
    }
}
