package fr.soraxdubbing.profilsmanagercore.manager;

import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
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

    private UsersManager() {
        instance = this;
        this.path = ProfilsManagerCore.getInstance().getDataFolder().getAbsolutePath() + "/users";
        users = new ArrayList<>();
        this.dataManager = new JsonManager(this.path);
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
        if(!hasUser(player.getUniqueId())) {
            this.registerUser(player);
        }
        for (CraftUser user : users) {
            if (user.getUniqueId().equals(player.getUniqueId())) {
                return user;
            }
        }
        return null;
    }

    /**
     * verify if the user is loaded
     * @param uuid the id of the user
     * @return true if the user is loaded
     */
    public boolean hasUser(UUID uuid) {
        boolean has = false;
        for (CraftUser user : users) {
            if (user.getUniqueId().equals(uuid)) {
                has = true;
            }
        }
        return has;
    }

    /**
     * register a user
     * @param player the player to register
     */
    public void registerUser(Player player) {
        if(!hasUser(player.getUniqueId())) {
            CraftUser user = new CraftUser(player.getUniqueId());
            user.getLoadedProfil().UpdateProfil(player, ProfilsManagerCore.getInstance());
            this.users.add(user);
        }
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
                if (user != null) {
                    users.add(user);
                }
                else {
                    ProfilsManagerCore.getInstance().getLogger().warning("The user " + file.getName() + " is not loaded");
                }
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
    public void registerClass(Class data) {
        dataManager.registerClass(data);
    }

    /**
     * unregister a class
     * @param data the class to unregister
     */
    public void unRegisterClass(Class data) {
        dataManager.unRegisterClass(data);
    }

    /**
     * Get a copy of the list of users
     * @return a copy of the list of users
     */
    public List<CraftUser> getUsers() {
        return new ArrayList<>(users);
    }
}
