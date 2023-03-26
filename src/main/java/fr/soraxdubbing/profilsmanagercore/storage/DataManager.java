package fr.soraxdubbing.profilsmanagercore.storage;

import fr.soraxdubbing.profilsmanagercore.model.CraftUser;

import java.util.List;
import java.util.UUID;

public abstract class DataManager {

    public abstract CraftUser load(UUID uuid);
    public abstract void save(CraftUser user);
    public abstract List<CraftUser> loadAll();
    public abstract void reload();
}
