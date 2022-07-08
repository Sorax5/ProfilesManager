package fr.soraxdubbing.profilsroadtonincraft.Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.soraxdubbing.profilsroadtonincraft.CraftUser.CraftUser;

public class SerialisationCraftUser {
    private Gson gson;

    public SerialisationCraftUser(){
        this.gson = createGsonInstance();
    }

    private Gson createGsonInstance(){
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }

    public String serialize(CraftUser user){
        return this.gson.toJson(user,user.getClass());
    }

    public CraftUser deSerialize(String json){
        return this.gson.fromJson(json,CraftUser.class);
    }

}
