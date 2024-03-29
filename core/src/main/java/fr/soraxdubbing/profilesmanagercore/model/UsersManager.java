package fr.soraxdubbing.profilesmanagercore.model;

import fr.soraxdubbing.profilesmanagercore.ProfilesManagerCore;
import fr.soraxdubbing.profilesmanagercore.storage.DataManager;
import fr.soraxdubbing.profilesmanagercore.storage.instance.JsonManager;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Manager of the users
 */
public class UsersManager {

    private static UsersManager instance;
    private List<CraftUser> users;
    private String path;
    private List<Class<AddonData>> addonClass;
    private HashMap<String, DataManager> dataManagers;
    private String method;

    private UsersManager() {
        instance = this;
        this.path = ProfilesManagerCore.getInstance().getDataFolder().getAbsolutePath() + File.separator + "users";
        users = new ArrayList<>();
        addonClass = new ArrayList<>();
        dataManagers = new HashMap<>();
        this.registerDataManager("json", new JsonManager(this.path, addonClass));
        this.method = ProfilesManagerCore.getInstance().getConfig().getString("method");
        this.method = "json";
    }

    /**
     * Get the instance of the manager
     * @return the instance of the manager
     */
    public static UsersManager getInstance() {
        if (instance == null) {
            instance = new UsersManager();
        }
        return instance;
    }

    /**
     * Get the user by his id
     * @param player the player
     * @return the user
     */
    public CraftUser getUser(Player player) {
        for (CraftUser user : users) {
            if (user.getUniqueId().equals(player.getUniqueId())) {
                return user;
            }
        }
        return null;
    }

    public void addUser(CraftUser user) {
        users.add(user);
    }

    /**
     * load the users
     */
    public void loadFileUsers() {
        this.users.clear();
        DataManager dataManager = this.dataManagers.get(this.method);
        this.users.addAll(dataManager.loadAll());
    }

    /**
     * save all the users
     */
    public void saveFileUsers() {
        DataManager dataManager = this.dataManagers.get(this.method);
        for (CraftUser user : users) {
            dataManager.save(user);
        }
    }

    /**
     * register a class
     * @param data the class to register
     */
    public void registerClass(Class data) {
        this.addonClass.add(data);
        this.dataManagers.get(this.method).reload();
    }

    /**
     * unregister a class
     * @param data the class to unregister
     */
    public void unRegisterClass(Class data) {
        if(this.addonClass.contains(data)) {
            this.addonClass.remove(data);
        }
        this.dataManagers.get(this.method).reload();
    }

    /**
     * Get a copy of the list of users
     * @return a copy of the list of users
     */
    public List<CraftUser> getUsers() {
        return new ArrayList<>(users);
    }

    /**
     * Get the data class
     * @return the data class
     */
    public List<Class<AddonData>> getAddonClass() {
        return addonClass;
    }

    public CraftProfile CreateProfil(String name){
        CraftProfile profil = new CraftProfile(name);
        for (Class<AddonData> aClass : this.addonClass) {
            try {
                profil.addAddon(aClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return profil;
    }



    /**
     * register a data manager
     * @param name the name of the data manager
     * @param dataManager the data manager
     */
    public void registerDataManager(String name,DataManager dataManager){
        this.dataManagers.put(name,dataManager);
    }
}
