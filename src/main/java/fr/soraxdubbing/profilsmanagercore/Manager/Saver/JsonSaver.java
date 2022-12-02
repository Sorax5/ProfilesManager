package fr.soraxdubbing.profilsmanagercore.Manager.Saver;

import com.google.gson.Gson;
import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.Manager.SaverAdapter;

import java.io.File;
import java.io.StringWriter;

public class JsonSaver extends SaverAdapter {

    private Gson gson;

    public JsonSaver(String folderPath) {
        super(folderPath);
        this.gson = new Gson();
    }

    @Override
    public void save(CraftUser user) {
        String json = gson.toJson(user);
        File file = new File(super.getFolderPath() + "/" + user.getPlayerUuid() + ".json");

        try(StringWriter builder = new StringWriter()) {
            builder.append(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
