package fr.soraxdubbing.profilsmanagercore.manager;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ItemManager {

    public static String ItemStackToStringByte(ItemStack itemStack) {
        String encodedObject = "";

        try {
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
            os.writeObject(itemStack);
            os.flush();

            byte[] data = io.toByteArray();

            encodedObject = Base64.getEncoder().encodeToString(data);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedObject;
    }

    public static ItemStack StringByteToItemStack(String encodedObject) {
        ItemStack itemStack = null;

        try {
            byte[] data = Base64.getDecoder().decode(encodedObject);

            ByteArrayInputStream in = new ByteArrayInputStream(data);
            BukkitObjectInputStream is = new BukkitObjectInputStream(in);

            itemStack = (ItemStack) is.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return itemStack;
    }

    public static List<String> ItemStackToStringList(ItemStack[] itemStack) {
        List<String> encodedObject = new ArrayList<>();
        for(int i = 0; i < itemStack.length; i++) {
            encodedObject.add(ItemStackToStringByte(itemStack[i]));
        }
        return encodedObject;
    }

    public static ItemStack[] StringListToItemStack(List<String> encodedObject) {
        ItemStack[] itemStack = new ItemStack[encodedObject.size()];
        for(int i = 0; i < encodedObject.size(); i++) {
            itemStack[i] = StringByteToItemStack(encodedObject.get(i));
        }
        return itemStack;
    }
}
