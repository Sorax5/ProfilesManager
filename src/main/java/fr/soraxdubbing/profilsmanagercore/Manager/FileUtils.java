package fr.soraxdubbing.profilsmanagercore.Manager;

import java.io.*;

public class FileUtils {
    // Cette Fonction permet de crée un fichier
    public static void createFile(File file) throws IndexOutOfBoundsException, IOException {
        if(!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }

    // Cet fonction permet de sauvegarder un fichier
    public static void save(File file,String text){
        final FileWriter fw;
        try{
            createFile(file);
            fw = new FileWriter(file);
            fw.write(text);
            fw.flush();
            fw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // Cet fonction permet de charger le contenue d'un fichier dans une variable
    public static String loadContent(File file){
        if(file.exists()){
            try{
                final BufferedReader reader = new BufferedReader(new FileReader(file));
                final StringBuilder text = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null){
                    text.append(line);
                }
                reader.close();
                return text.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
