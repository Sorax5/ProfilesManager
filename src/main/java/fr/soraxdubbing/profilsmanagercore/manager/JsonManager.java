package fr.soraxdubbing.profilsmanagercore.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import fr.soraxdubbing.profilsmanagercore.Addon.AddonData;
import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JsonManager extends DataManager {

    private Gson gson;

    private List<Class> list;

    public JsonManager(String folderPath) {
        super(folderPath);
        this.list = new ArrayList<Class>();
        reload();
    }

    private void reload(){
        RuntimeTypeAdapterFactory<AddonData> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(AddonData.class, "type");
        for (Class aClass : this.list) {
            System.out.println("----------------------");
            System.out.println(aClass.getName());
            runtimeTypeAdapterFactory.registerSubtype(aClass);
        }
        this.gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .disableHtmlEscaping()
                    .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
                    .create();
    }

    @Override
    public CraftUser load(UUID uuid) {
        try(Reader reader = Files.newBufferedReader(Paths.get(getFolderPath() + "/" + uuid + ".json"))){
            return gson.fromJson(reader, CraftUser.class);
        }catch (IllegalStateException | JsonSyntaxException | IOException exception){
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(CraftUser user) {
        File file = new File(getFolderPath() + File.separator + user.getPlayerUuid().toString() + ".json");

        try(PrintWriter printWriter = new PrintWriter(file)) {
            String json = this.gson.toJson(user,CraftUser.class);
            printWriter.write(json);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void registerClass(AddonData addon){
        this.list.add(addon.getClass());
        reload();
    }

    public void unRegisterClass(AddonData addon){
        if(this.list.contains(addon.getClass())){
            this.list.remove(addon.getClass());
            reload();
        }
    }
}
