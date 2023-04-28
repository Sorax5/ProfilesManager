package fr.soraxdubbing.profilesmanagercore.storage;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import fr.soraxdubbing.profilesmanagercore.ProfilesManagerCore;
import org.bukkit.configuration.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public final class PluginLocalisation {
    public static final PluginLocalisation INSTANCE = new PluginLocalisation();

    private HashMap<String, String> localisation = new HashMap<>();

    private String lang;

    private PluginLocalisation() {
        Configuration config = ProfilesManagerCore.getInstance().getConfig();
        this.lang = config.getString("lang");

        // create lang folder if not exist
        File langFolder = new File(ProfilesManagerCore.getInstance().getDataFolder(), "lang");
        if (!langFolder.exists()) {
            langFolder.mkdir();
        }

        // connect to distant repository to get the language file
        String url = config.getString("lang-repo");


        try(Scanner scanner = new Scanner(new File(ProfilesManagerCore.getInstance().getDataFolder(), "lang/" + this.lang + ".yml"))){
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(":")) {
                    String[] split = line.split(":");
                    this.localisation.put(split[0], split[1]);
                }
            }
        } catch (FileNotFoundException e) {
            ProfilesManagerCore.getInstance().getLogger().warning("The language file " + this.lang + " was not found. The default language will be used.");
            this.lang = "en";
        }
    }

    public String get(String key) {
        return this.localisation.get(key);
    }


}
