package fr.soraxdubbing.profilsmanagercore.Manager;

import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;

public abstract class SaverAdapter {

    private String folderPath;

    public SaverAdapter(String folderPath) {
        this.folderPath = folderPath;
    }

    public abstract void save(CraftUser user);

    protected String getFolderPath() {
        return folderPath;
    }

}
