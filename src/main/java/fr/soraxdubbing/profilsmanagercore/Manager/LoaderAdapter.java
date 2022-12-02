package fr.soraxdubbing.profilsmanagercore.Manager;

import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;

import java.util.UUID;

public abstract class LoaderAdapter {

    private String folderPath;

    public LoaderAdapter(String folderPath) {
        this.folderPath = folderPath;
    }

    public abstract CraftUser load(UUID uuid);

    protected String getFolderPath() {
        return folderPath;
    }
}
