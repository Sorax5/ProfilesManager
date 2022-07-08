package fr.soraxdubbing.profilsroadtonincraft.Manager;

import fr.soraxdubbing.profilsroadtonincraft.CraftUser.CraftUser;
import fr.soraxdubbing.profilsroadtonincraft.ProfilsRoadToNincraft;
import fr.soraxdubbing.profilsroadtonincraft.Serialisation.SerialisationCraftUser;
import fr.soraxdubbing.profilsroadtonincraft.profil.CraftProfil;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CraftUserManager {
    SerialisationCraftUser serialize;
    String saveDirectory;
    private final CraftProfilManager craftProfilManager;
    private ProfilsRoadToNincraft plugin;

    public CraftUserManager(String _saveDir,ProfilsRoadToNincraft _plugin){
        this.serialize = new SerialisationCraftUser();
        this.saveDirectory = _saveDir;
        this.craftProfilManager = new CraftProfilManager();
        this.plugin = _plugin;
    }

    public CraftUser loadCraftUser(Player player){
        Path path = Paths.get(this.saveDirectory + "\\" + player.getUniqueId());
        if(!path.toFile().exists()){
            try{
                Files.createDirectory(path);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        File file = new File(path.toFile().getAbsolutePath(),  player.getUniqueId() + ".json");
        if(!file.exists()){
            CraftUser craftUser = new CraftUser(player.getUniqueId());
            CraftProfil craftProfil = new CraftProfil("default");
            CraftProfil.UpdateProfil(craftProfil, player, this.plugin);
            craftUser.addProfils(craftProfil);
            craftUser.setActualProfil("default");
            return craftUser;
        }
        else{
            CraftUser craftUser = this.serialize.deSerialize(FileUtils.loadContent(file));
            List<CraftProfil> profils = craftProfilManager.loadCraftProfil(path.toFile().getAbsolutePath());
            craftUser.setProfils(new ArrayList<>());
            for (CraftProfil profil : profils) {
                craftUser.addProfils(profil);
                if(craftUser.getActualProfil() == null){
                    craftUser.setActualProfil(profil.getName());
                }
            }
            return craftUser;
        }
    }

    public void saveCraftUser(CraftUser craftUser){
        Path path = Paths.get(this.saveDirectory + "\\" + craftUser.getPlayerUuid());
        if(!path.toFile().exists()){
            try{
                Files.createDirectory(path);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        craftUser.removeActualProfil();
        List<CraftProfil> profils = craftUser.getProfils();
        craftProfilManager.saveCraftProfils(profils, path.toFile().getAbsolutePath());
        craftUser.clearProfils();
        File file = new File(path.toString(), craftUser.getPlayerUuid() + ".json");
        String json = this.serialize.serialize(craftUser);
        FileUtils.save(file, json);
    }
}
