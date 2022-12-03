package fr.soraxdubbing.profilsmanagercore.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.soraxdubbing.profilsmanagercore.Addon.AddonData;
import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;

import java.io.File;
import java.io.PrintWriter;
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
        File file = new File(getFolderPath() + File.separator + uuid.toString() + ".json");
        if(file.exists()){
            return this.gson.fromJson(file.toString(),CraftUser.class);
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
