package fr.soraxdubbing.profilsmanagercore.manager;

import fr.soraxdubbing.profilsmanagercore.Addon.AddonData;
import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;

import java.util.List;
import java.util.UUID;

public abstract class DataManager {

    private String folderPath;

    public DataManager(String folderPath) {
        this.folderPath = folderPath;
    }

    public abstract CraftUser load(UUID uuid);
    public abstract void save(CraftUser user);
    public abstract void registerClass(Class data);

    public abstract void unRegisterClass(Class data);

    protected String getFolderPath() {
        return folderPath;
    }
}
