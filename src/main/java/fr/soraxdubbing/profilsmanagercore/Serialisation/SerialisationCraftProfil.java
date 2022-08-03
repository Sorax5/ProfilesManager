package fr.soraxdubbing.profilsmanagercore.Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.soraxdubbing.profilsmanagercore.profil.CraftProfil;

public class SerialisationCraftProfil {

    private Gson gson;

    public SerialisationCraftProfil(){
        this.gson = createGsonInstance();
    }

    private Gson createGsonInstance(){
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }

    public String serialize(CraftProfil profil){
        gson = createGsonInstance();
        return this.gson.toJson(profil,CraftProfil.class);
    }

    public CraftProfil deSerialize(String json){
        gson = createGsonInstance();
        return this.gson.fromJson(json,CraftProfil.class);
    }

}
