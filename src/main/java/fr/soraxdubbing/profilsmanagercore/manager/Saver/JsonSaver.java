package fr.soraxdubbing.profilsmanagercore.manager.Saver;

import com.google.gson.Gson;
import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.manager.SaverAdapter;

import java.io.File;
import java.io.PrintWriter;

public class JsonSaver extends SaverAdapter {

    private Gson gson;

    public JsonSaver(String folderPath) {
        super(folderPath);
        this.gson = new Gson();
    }

    @Override
    public void save(CraftUser user) {
        user.removeLoadedProfil();
        File file = new File(super.getFolderPath() + "/" + user.getPlayerUuid() + ".json");
        try(PrintWriter writer = new PrintWriter(file)) {
            String json = gson.toJson(user);
            writer.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
