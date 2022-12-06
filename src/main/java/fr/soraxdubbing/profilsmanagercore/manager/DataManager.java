package fr.soraxdubbing.profilsmanagercore.manager;

import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;

import java.util.UUID;

public abstract class DataManager {

    private String folderPath;

    public DataManager(String folderPath) {
        this.folderPath = folderPath;
    }

    public abstract CraftUser load(UUID uuid);
    public abstract void save(CraftUser user);
    public abstract void reload();

    protected String getFolderPath() {
        return folderPath;
    }
}
