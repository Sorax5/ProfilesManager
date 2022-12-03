package fr.soraxdubbing.profilsmanagercore.Manager;

import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.Manager.Loader.JsonLoader;
import fr.soraxdubbing.profilsmanagercore.Manager.Saver.JsonSaver;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
import fr.soraxdubbing.profilsmanagercore.profil.CraftProfil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsersManager {

    private static UsersManager instance;
    private List<CraftUser> users;
    private SaverAdapter saver;
    private LoaderAdapter loader;
    private String path;

    private UsersManager() {
        instance = this;
        this.path = ProfilsManagerCore.getInstance().getDataFolder().getAbsolutePath() + "/users";
        users = new ArrayList<>();
        this.loader = new JsonLoader(this.path);
        this.saver = new JsonSaver(this.path);
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
     * @param uudi the id of the user
     * @return the user
     */
    public CraftUser getUser(UUID uudi) {
        for (CraftUser user : users) {
            if (user.getPlayerUuid().equals(uudi)) {
                return user;
            }
        }
        CraftUser user = new CraftUser(uudi);
        CraftProfil profile = new CraftProfil("default");
        user.setActualProfil(profile);
        this.registerUser(user);
        return user;
    }

    /**
     * verify if the user is loaded
     * @param uuid the id of the user
     * @return true if the user is loaded
     */
    public boolean hasUser(UUID uuid) {
        return getUser(uuid) != null;
    }

    /**
     * register a user
     * @param user the user to register
     */
    public void registerUser(CraftUser user) {
        if (!hasUser(user.getPlayerUuid())) {
            users.add(user);
        }
    }

    /**
     * unregister a user
     * @param user the user to unregister
     */
    public void unregisterUser(CraftUser user) {
        if (hasUser(user.getPlayerUuid())) {
            users.remove(user);
        }
    }

    public void loadFileUsers() {
        this.users.clear();
        File userFile = new File(this.path);
        for (File file : userFile.listFiles()) {
            if (file.getName().endsWith(".json")) {
                CraftUser user = loader.load(UUID.fromString(file.getName().replace(".json", "")));
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
            saver.save(user);
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
