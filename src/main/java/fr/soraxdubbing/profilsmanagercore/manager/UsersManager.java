package fr.soraxdubbing.profilsmanagercore.manager;

import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
import fr.soraxdubbing.profilsmanagercore.addon.AddonData;
import fr.soraxdubbing.profilsmanagercore.manager.instance.JsonManager;
import fr.soraxdubbing.profilsmanagercore.profil.CraftProfil;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UsersManager {

    private static UsersManager instance;
    private List<CraftUser> users;
    private String path;
    private List<Class<AddonData>> addonClass;
    private HashMap<String,DataManager> dataManagers;
    private String method;

    private UsersManager() {
        instance = this;
        this.path = ProfilsManagerCore.getInstance().getDataFolder().getAbsolutePath() + File.separator + "users";
        users = new ArrayList<>();
        addonClass = new ArrayList<>();
        dataManagers = new HashMap<>();
        this.registerDataManager("json", new JsonManager(this.path, addonClass));
        this.method = ProfilsManagerCore.getInstance().getConfig().getString("method");
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
        File userFile = new File(this.path);
        for (File file : userFile.listFiles()) {
            if (file.getName().endsWith(".json")) {
                UUID uuid = UUID.fromString(file.getName().replace(".json", ""));
                CraftUser user = dataManager.load(uuid);
                if (user == null) {
                    ProfilsManagerCore.getInstance().getLogger().warning("The user " + file.getName() + " is not loaded");
                    user = new CraftUser(uuid);
                }

                for (Class<AddonData> aClass : this.addonClass) {
                    for (CraftProfil profil : user.getProfils()) {
                        if(!profil.hasAddon(aClass)){
                            try{
                                profil.addAddon(aClass.newInstance());
                            }
                            catch (InstantiationException | IllegalAccessException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                users.add(user);
            }
        }
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

    public CraftProfil CreateProfil(String name){
        CraftProfil profil = new CraftProfil(name);
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
