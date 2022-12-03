package fr.soraxdubbing.profilsmanagercore.manager.Loader;

import com.google.gson.Gson;
import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.manager.LoaderAdapter;

import java.io.File;
import java.util.UUID;

public class JsonLoader extends LoaderAdapter {

    private Gson gson;

    public JsonLoader(String folderPath) {
        super(folderPath);
        this.gson = new Gson();

    }

    @Override
    public CraftUser load(UUID uuid) {
        try{
            File file = new File(super.getFolderPath() + "/" + uuid + ".json");
            return gson.fromJson(file.toString(), CraftUser.class);
        } catch (Exception e) {
            return null;
        }
    }
}
