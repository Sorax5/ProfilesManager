package fr.soraxdubbing.profilsmanagercore.profil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class AdminCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        switch (strings.length) {
            case 1:
                list.add("add");
                list.add("remove");
                list.add("save");
                list.add("load");
                list.add("copy");
                list.add("transfer");
                return list;
            default:
                return null;
        }
    }
}
