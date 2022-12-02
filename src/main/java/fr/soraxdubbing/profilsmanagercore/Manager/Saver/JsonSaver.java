package fr.soraxdubbing.profilsmanagercore.Manager.Saver;

import com.google.gson.Gson;
import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.Manager.SaverAdapter;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class JsonSaver extends SaverAdapter {

    private Gson gson;

    public JsonSaver(String folderPath) {
        super(folderPath);
        this.gson = new Gson();
    }

    @Override
    public void save(CraftUser user) {
        user.removeActualProfil();
        File file = new File(super.getFolderPath() + "/" + user.getPlayerUuid() + ".json");
        try(PrintWriter writer = new PrintWriter(file)) {
            String json = gson.toJson(user);
            writer.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
