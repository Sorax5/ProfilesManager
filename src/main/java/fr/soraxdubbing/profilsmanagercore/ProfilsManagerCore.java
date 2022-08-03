package fr.soraxdubbing.profilsmanagercore;

import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.Manager.CraftUserManager;
import fr.soraxdubbing.profilsmanagercore.event.Loader;
import fr.soraxdubbing.profilsmanagercore.profil.*;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public final class ProfilsManagerCore extends JavaPlugin {

    private List<CraftUser> users;
    private Economy economy;
    private Permission permission;
    private Chat chat;
    private CraftUserManager manager;
    private static ProfilsManagerCore INSTANCE;

    @Override
    public void onEnable() {

        int pluginId = 15930; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

        // Plugin startup logic
        File userFile = new File(getDataFolder()+"/user/");
        this.getDataFolder().mkdir();
        userFile.mkdir();
        this.saveDefaultConfig();


        if (!setupEconomy() ) {
            this.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        setupPermissions();
        setupChat();

        this.manager = new CraftUserManager(userFile.getAbsolutePath(),this);
        this.users = new ArrayList<>();
        this.getServer().getPluginManager().registerEvents(new Loader(this), this);

        profilsRegister();

        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Sauvegarde des Users");
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

    public List<CraftUser> getUsers(){
        return this.users;
    }

    public CraftUser getUser(UUID identifier){
        for(CraftUser user : this.users){
            if(user.getPlayerUuid().equals(identifier)){
                return user;
            }
        }
        return null;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        permission = rsp.getProvider();
        return permission != null;
    }

    public Economy getEconomy() {
        return economy;
    }
    public Permission getPermission() {
        return permission;
    }
    public Chat getChat() {
        return chat;
    }



    public CraftUserManager getManager() {
        return manager;
    }

    public static ProfilsManagerCore getInstance() {
        return ProfilsManagerCore.INSTANCE;
    }
}
