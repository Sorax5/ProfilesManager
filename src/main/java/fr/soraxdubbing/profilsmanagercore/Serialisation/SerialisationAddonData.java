package fr.soraxdubbing.profilsmanagercore.Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.soraxdubbing.profilsmanagercore.Addon.AddonData;

import java.util.ArrayList;
import java.util.List;

public class SerialisationAddonData {

    private Gson gson;
    private List<Class> list;

    public SerialisationAddonData(){
        this.list = new ArrayList<Class>();
        this.gson = createGsonInstance();
    }

    private Gson createGsonInstance(){
        RuntimeTypeAdapterFactory<AddonData> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(AddonData.class, "type");
        for (Class aClass : this.list) {
            System.out.println("----------------------");
            System.out.println(aClass.getName());
            runtimeTypeAdapterFactory.registerSubtype(aClass);
        }
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
                .create();
    }

    public String serialize(AddonData addon){
        return this.gson.toJson(addon,AddonData.class);
    }

    public AddonData deSerialize(String json){
        return this.gson.fromJson(json,AddonData.class);
    }

    public void addClass(Class addon){
        this.list.add(addon);
        this.gson = createGsonInstance();
    }

    public void removeClass(Class addon){
        this.list.remove(addon);
        this.gson = createGsonInstance();
    }

    public boolean containsClass(Class addon){
        return this.list.contains(addon);
    }
}
