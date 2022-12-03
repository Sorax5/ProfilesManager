package fr.soraxdubbing.profilsmanagercore.manager;

import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
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
            if (user.getPlayerUuid().equals(player.getUniqueId())) {
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
            if (user.getPlayerUuid().equals(uuid)) {
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
            CraftProfil profile = new CraftProfil("default");
            profile.UpdateProfil(player, ProfilsManagerCore.getInstance());
            user.setLoadedProfil(profile);
            this.users.add(user);
        }
    }

    /**
     * unregister a user
     * @param player the user to unregister
     */
    public void unregisterUser(Player player) {
        if(hasUser(player.getUniqueId())) {
            CraftUser user = getUser(player);
            this.users.remove(user);
            player.kickPlayer("§cVotre profil a été supprimé");
        }
    }

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

    public void saveFileUsers() {
        for (CraftUser user : users) {
            dataManager.save(user);
        }
    }

    /**
     * Get a copy of the list of users
     * @return a copy of the list of users
     */
    public List<CraftUser> getUsers() {
        return new ArrayList<>(users);
    }
}
