package fr.soraxdubbing.profilsmanagercore.manager;

import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;

import java.util.UUID;

public abstract class DataManager {

    public abstract CraftUser load(UUID uuid);
    public abstract void save(CraftUser user);
    public abstract void reload();
}
