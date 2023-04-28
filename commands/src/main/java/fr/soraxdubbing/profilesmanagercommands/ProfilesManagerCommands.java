package fr.soraxdubbing.profilesmanagercommands;

import app.ashcon.intake.bukkit.BukkitIntake;
import app.ashcon.intake.bukkit.graph.BasicBukkitCommandGraph;
import app.ashcon.intake.fluent.DispatcherNode;
import fr.soraxdubbing.profilesmanagercommands.commands.AdministrationCommands;
import fr.soraxdubbing.profilesmanagercommands.commands.UserCommands;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of the plugin
 * @author SoraxDubbing
 */
public final class ProfilesManagerCommands extends JavaPlugin {

    private static ProfilesManagerCommands instance;

    @Override
    public void onLoad() {
        instance = this;

        getLogger().info("Enregistrement des commandes...");

        BasicBukkitCommandGraph commandGraph = new BasicBukkitCommandGraph();

        // ADMINISTRATION COMMANDS
        DispatcherNode administation = commandGraph.getRootDispatcherNode().registerNode("administration");
        administation.registerCommands(new AdministrationCommands());

        // USER COMMANDS
        DispatcherNode profile = commandGraph.getRootDispatcherNode().registerNode("profile");
        profile.registerCommands(new UserCommands());

        // SETTINGS COMMANDS
        DispatcherNode settings = commandGraph.getRootDispatcherNode().registerNode("settings");
        settings.registerCommands(new UserCommands());

        BukkitIntake bukkitIntake = new BukkitIntake(this,commandGraph);
        bukkitIntake.register();
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
    }

    /**
     * Instance of the plugin
     * @return ProfilesManagerCommands instance
     */
    public static ProfilesManagerCommands getInstance(){
        return instance;
    }
}
