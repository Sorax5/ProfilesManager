package fr.soraxdubbing.profilsmanagercore.manager;

import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
import fr.soraxdubbing.profilsmanagercore.addon.AddonData;
import fr.soraxdubbing.profilsmanagercore.profil.CraftProfil;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsersManager {

    private static UsersManager instance;
    private List<CraftUser> users;
    private DataManager dataManager;
    private String path;
    private List<Class<AddonData>> addonClass;

    private UsersManager() {
        instance = this;
        this.path = ProfilsManagerCore.getInstance().getDataFolder().getAbsolutePath() + "/users";
        users = new ArrayList<>();
        addonClass = new ArrayList<>();
        this.dataManager = new JsonManager(this.path, addonClass);
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

    /**
     * load the users
     */
    public void loadFileUsers() {
        this.users.clear();
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
        for (CraftUser user : users) {
            dataManager.save(user);
        }
    }

    /**
     * register a class
     * @param data the class to register
     */
    public void registerClass(Class<AddonData> data) {
        this.addonClass.add(data);
        this.dataManager.reload();
    }

    /**
     * unregister a class
     * @param data the class to unregister
     */
    public void unRegisterClass(Class<AddonData> data) {
        if(this.addonClass.contains(data)) {
            this.addonClass.remove(data);
        }
        this.dataManager.reload();
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
        CraftProfil profil = UsersManager.getInstance().CreateProfil(name);
        for (Class<AddonData> aClass : this.addonClass) {
            try {
                profil.addAddon(aClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return profil;
    }
}
