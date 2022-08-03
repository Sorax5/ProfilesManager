package fr.soraxdubbing.profilsmanagercore.Manager;

import fr.soraxdubbing.profilsmanagercore.Addon.AddonData;
import fr.soraxdubbing.profilsmanagercore.Serialisation.SerialisationAddonData;
import fr.soraxdubbing.profilsmanagercore.event.AddonRegisterEvent;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AddonDataManager {

    private final SerialisationAddonData serialize;

    public AddonDataManager(){
        this.serialize = new SerialisationAddonData();

    }

    public List<AddonData> loadAddonData(String directory) {
        AddonRegisterEvent event = new AddonRegisterEvent(serialize);
        Bukkit.getPluginManager().callEvent(event);
        List<AddonData> allData = new ArrayList<>();
        Path path = Paths.get(directory );
        if(path.toFile().exists()) {
            File[] files = path.toFile().listFiles();
            assert files != null;
            for (File file : files) {
                final String json = FileUtils.loadContent(file);
                allData.add(serialize.deSerialize(json));
            }
        }
        System.out.println("[ProfilsRoadToNincraft] Loaded " + allData.size() + " addon data");
        return allData;
    }

    public void saveAddonData(AddonData data, String directory) {
        final File file = new File(directory, data.getAddonName() + ".json");
        String json = serialize.serialize(data);
        FileUtils.save(file,json);
    }

    public void saveAddonDatas(List<AddonData> allData, String directory) {
        AddonRegisterEvent event = new AddonRegisterEvent(serialize);
        Bukkit.getPluginManager().callEvent(event);
        Path path = Paths.get(directory + "/data");
        if(!path.toFile().exists()){
            try{
                Files.createDirectory(path);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        for(AddonData data : allData){
            saveAddonData(data, path.toFile().getAbsolutePath());
        }
    }
}
