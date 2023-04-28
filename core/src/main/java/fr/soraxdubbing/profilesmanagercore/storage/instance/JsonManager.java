package fr.soraxdubbing.profilesmanagercore.storage.instance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import fr.soraxdubbing.profilesmanagercore.ProfilesManagerCore;
import fr.soraxdubbing.profilesmanagercore.model.AddonData;
import fr.soraxdubbing.profilesmanagercore.model.CraftUser;
import fr.soraxdubbing.profilesmanagercore.storage.DataManager;
import fr.soraxdubbing.profilesmanagercore.storage.RuntimeTypeAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JsonManager extends DataManager {

    private Gson gson;
    private List<Class<AddonData>> list;
    private String path;

    public JsonManager(String folderPath, List<Class<AddonData>> addonClass) {
        this.path = folderPath;
        this.list = addonClass;
        reload();
    }

    public void reload(){
        RuntimeTypeAdapterFactory<AddonData> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(AddonData.class, "type");

        for (Class classType : this.list) {
            System.out.println(classType.getName());
            runtimeTypeAdapterFactory.registerSubtype(classType);
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
        CraftUser user = null;
        try(Reader reader = Files.newBufferedReader(Paths.get(path + "/" + uuid + ".json"))){
            user = gson.fromJson(reader, CraftUser.class);
        }catch (JsonSyntaxException | JsonIOException | IOException exception){
            exception.printStackTrace();
        }
        return user;
    }

    @Override
    public void save(CraftUser user) {
        File file = new File(path + File.separator + user.getUniqueId().toString() + ".json");

        try(PrintWriter printWriter = new PrintWriter(file)) {
            String json = this.gson.toJson(user,CraftUser.class);
            printWriter.write(json);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<CraftUser> loadAll() {
        File folder = new File(path);
        File[] files = folder.listFiles();
        List<CraftUser> users = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.getName().endsWith(".json")) {
                        continue;
                    }
                    try (Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()))) {
                        users.add(gson.fromJson(reader, CraftUser.class));
                        ProfilesManagerCore.getInstance().getLogger().info("User " + file.getName() + " loaded");
                    } catch (JsonSyntaxException | JsonIOException | IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
        return users;
    }


}
