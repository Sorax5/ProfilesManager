package fr.soraxdubbing.profilsroadtonincraft.profil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ProfilCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        switch (strings.length) {
            case 1:
                list.add("setname");
                list.add("get");
                break;
            case 2:
                if (strings[0].equalsIgnoreCase("get")) {
                    list.add("name");
                    list.add("date");
                    list.add("location");
                    list.add("hp");
                    list.add("food");
                }
                break;
        }
        return list;
    }
}
