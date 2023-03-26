package fr.soraxdubbing.profilsmanagercore;

import app.ashcon.intake.bukkit.BukkitIntake;
import app.ashcon.intake.bukkit.graph.BasicBukkitCommandGraph;
import app.ashcon.intake.fluent.DispatcherNode;
import fr.soraxdubbing.profilsmanagercore.commands.AdminCommand;
import fr.soraxdubbing.profilsmanagercore.commands.ProfilsCommand;
import fr.soraxdubbing.profilsmanagercore.commands.profil.ProfilGetterCommand;
import fr.soraxdubbing.profilsmanagercore.commands.profil.ProfilSetterCommand;
import fr.soraxdubbing.profilsmanagercore.event.PlayerHandlerEvent;
import fr.soraxdubbing.profilsmanagercore.model.UsersManager;
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
        this.getDataFolder().mkdir();
        userFile.mkdir();
        this.saveDefaultConfig();
        getLogger().info("Fichier de configuration créé !");

        // INTAKE FRAMEWORK
        getLogger().info("Enregistrement des commandes...");
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
     * Get instance of ProfilsManagerCore
     * @return ProfilsManagerCore
     */
    public static ProfilsManagerCore getInstance() {
        return ProfilsManagerCore.INSTANCE;
    }
}
