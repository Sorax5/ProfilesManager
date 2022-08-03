package fr.soraxdubbing.profilsmanagercore.Manager;

import org.bukkit.entity.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    public void createPluginDirectory(Player player,String directory){
        try{
            Path userFolder = Paths.get(directory + "/" + player.getUniqueId());
            Files.createDirectory(userFolder);
            Path profilFolder = Paths.get(directory + "/profils");
            Files.createDirectory(profilFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
