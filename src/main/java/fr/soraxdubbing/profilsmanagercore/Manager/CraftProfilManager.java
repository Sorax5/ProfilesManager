package fr.soraxdubbing.profilsmanagercore.Manager;

import fr.soraxdubbing.profilsmanagercore.Addon.AddonData;
import fr.soraxdubbing.profilsmanagercore.Serialisation.SerialisationCraftProfil;
import fr.soraxdubbing.profilsmanagercore.profil.CraftProfil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CraftProfilManager {

    private final SerialisationCraftProfil serialize;
    private final AddonDataManager addonDataManager;

    public CraftProfilManager(){
        this.serialize = new SerialisationCraftProfil();
        this.addonDataManager = new AddonDataManager();
    }

    public List<CraftProfil> loadCraftProfil(String directory) {
        List<CraftProfil> profils = new ArrayList<>();
        Path path = Paths.get(directory + "\\" +"profils");
        if(path.toFile().exists()){
            File[] files = path.toFile().listFiles();
            assert files != null;
            for(File file : files){
                File profilFile = new File(directory + "\\" +"profils" + "\\" + file.getName(), file.getName() + ".json");
                Path dataFile = Paths.get(directory + "\\" +"profils" + "\\" + file.getName() + "\\" + "data");

                final String json = FileUtils.loadContent(profilFile);
                CraftProfil profil = serialize.deSerialize(json);

                List<AddonData> allData = addonDataManager.loadAddonData(dataFile.toFile().getAbsolutePath());
                profil.setAddons(allData);
                profils.add(profil);
            }
        }
        return profils;
    }

    public void saveCraftProfil(CraftProfil profil, String directory) {
        Path path = Paths.get(directory + "\\" + profil.getName());
        if(!path.toFile().exists()){
            try{
                Files.createDirectory(path);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        File file = new File(directory + "\\" + profil.getName(), profil.getName() + ".json");
        String json = serialize.serialize(profil);
        FileUtils.save(file,json);
        addonDataManager.saveAddonDatas(profil.getAddons(), directory + "\\" + profil.getName());
    }

    public void saveCraftProfils(List<CraftProfil> profils, String directory) {
        Path path = Paths.get(directory + "\\" +"profils");
        if(!path.toFile().exists()){
            try{
                Files.createDirectory(path);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        for(CraftProfil profil : profils){
            saveCraftProfil(profil, path.toString());
        }
    }


    public String fileExtension(String json){
        int index = json.lastIndexOf('.');
        if(index > 0) {
            return json.substring(index + 1);
        }
        return "";
    }

    public File getJsonFile(File directory){
        File[] files = directory.listFiles();
        for (File file : files) {
            if(fileExtension(file.toString()).equals("json")){
                return file;
            }
        }
        return null;
    }
}
