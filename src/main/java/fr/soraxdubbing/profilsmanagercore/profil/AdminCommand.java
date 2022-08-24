package fr.soraxdubbing.profilsmanagercore.profil;

import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AdminCommand implements CommandExecutor {

    private final ProfilsManagerCore plugin;

    public AdminCommand(ProfilsManagerCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            switch (strings.length) {
                // Dans le cas où il n'y a pas de paramètre
                case 0:
                    player.sendMessage("§c/admin <save,load,add,remove,copy,transfer>");
                    break;
                // Dans le cas où il y a 1 arguments en paramètre
                case 1:
                    switch (strings[0]) {
                        case "save":
                            player.sendMessage("§c/admin save <player> <profilName>");
                            break;
                        case "load":
                            player.sendMessage("§c/admin load <player> <profilName>");
                            break;
                        case "add":
                            player.sendMessage("§c/admin add <player> <profilName>");
                            break;
                        case "remove":
                            player.sendMessage("§c/admin remove <player> <profilName>");
                            break;
                        case "copy":
                            player.sendMessage("§c/admin copy <player> <profilName>");
                            break;
                        case "transfer":
                            player.sendMessage("§c/admin transfer <player> <targetPlayer> <profilName>");
                            break;
                        default:
                            player.sendMessage("§c/admin <save,load,add,remove,copy,transfer>");
                    }
                    break;
                // Dans le cas où il y a 4 arguments en paramètre
                case 2:
                    switch (strings[0]) {
                        case "save":
                            player.sendMessage("§c/admin save "+ strings[1] +" <profilName>");
                            break;
                        case "load":
                            player.sendMessage("§c/admin load "+ strings[1] +" <profilName>");
                            break;
                        case "add":
                            player.sendMessage("§c/admin add "+ strings[1] +" <profilName>");
                            break;
                        case "remove":
                            player.sendMessage("§c/admin remove "+ strings[1] +" <profilName>");
                            break;
                        case "copy":
                            player.sendMessage("§c/admin copy "+ strings[1] +" <profilName> <profilNewName>");
                            break;
                        case "transfer":
                            player.sendMessage("§c/admin transfer "+ strings[1] +" <profilName>");
                            break;
                        default:
                            player.sendMessage("§c/admin <save,load,add,remove,copy,transfer>");
                            break;
                    }
                    break;
                // Dans le cas où il y a 2 arguments en paramètre
                case 3:
                    switch (strings[0]) {
                        case "save":
                            save(strings[1], player);
                            break;
                        case "load":
                            load(strings[1], player);
                            break;
                        case "add":
                            add(strings[1], strings[2], player);
                            break;
                        case "remove":
                            remove(strings[1], strings[2], player);
                            break;
                        case "copy":
                            player.sendMessage("§c/admin copy "+ strings[1] + " "+ strings[2] + " <profilNewName>");
                            break;
                        case "transfer":
                            player.sendMessage("§c/admin transfer "+ strings[1] +" " + strings[2] +" <profilName>");
                            break;
                        default:
                            player.sendMessage("§c/admin <save,load,add,remove,copy,transfer>");
                            break;
                    }
                    break;
                // Dans le cas où il y a 3 arguments en paramètre
                case 4:
                    switch (strings[0]) {
                        case "copy":
                            copy(strings[1], strings[2], strings[3], player);
                            break;
                        case "transfer":
                            transfer(strings[1], strings[2], strings[3], player);
                            break;
                    }
                    break;
                default:
                    player.sendMessage("§cVous mettez trop d'arguments");
                    break;
            }
        }
        else{
            commandSender.sendMessage("§cVous devez être un joueur !");
        }


        return true;
    }

    public void save(String name,Player player) {
        OfflinePlayer target = plugin.getServer().getPlayer(name);
        if (target == null) {
            player.sendMessage("§cLe joueur " + name + " ne s'est jamais connecté");
            return;
        }

        CraftUser user = plugin.getUser(target.getUniqueId());
        if (user == null) {
            player.sendMessage("§cLe joueur " + name + " ne s'est jamais connecté");
            return;
        }

        player.sendMessage("§aLe profil " + name + " a été sauvegardé");
        // faire le serialize de l'objet profil
    }

    public void load(String name,Player player) {
        Player target = plugin.getServer().getPlayer(name);
        if (target == null) {
            player.sendMessage("§cLe joueur " + name + " ne s'est jamais connecté");
            return;
        }

        CraftUser user = plugin.getUser(target.getUniqueId());
        if (user == null) {
            player.sendMessage("§cLe joueur " + name + " ne s'est jamais connecté");
            return;
        }
        player.sendMessage("§aProfil " + name + " chargé");

        // faire le deserialize de l'objet profil
    }

    public void add(String name,String profilName,Player player) {
        CraftProfil profil = new CraftProfil(profilName);
        Player target = plugin.getServer().getPlayer(name);
        if (target == null) {
            player.sendMessage("§cLe joueur " + name + " ne s'est jamais connecté");
            return;
        }

        CraftUser user = plugin.getUser(target.getUniqueId());
        if (user == null) {
            player.sendMessage("§cLe joueur " + name + " ne s'est jamais connecté");
            return;
        }
        user.addProfils(profil);
        player.sendMessage("§aProfil " + profilName + " ajouté à " + name);
    }

    public void remove(String name,String profilName,Player player) {
        Player target = plugin.getServer().getPlayer(name);
        if (target == null) {
            player.sendMessage("§cLe joueur " + name + " ne s'est jamais connecté");
            return;
        }

        CraftUser user = plugin.getUser(target.getUniqueId());
        if (user == null) {
            player.sendMessage("§cLe joueur " + name + " ne s'est jamais connecté");
            return;
        }
        CraftProfil profil = getProfil(profilName, user.getProfils());
        if (profil == null) {
            player.sendMessage("§cLe profil " + profilName + " n'existe pas");
            return;
        }
        user.removeProfils(profil);
        player.sendMessage("§aLe profil " + profilName + " a été supprimé");
        // Serialize le profil
    }

    public void copy(String name,String profilName,String profilNameTarget,Player player) {
        OfflinePlayer target = plugin.getServer().getPlayer(name);
        if (target == null) {
            player.sendMessage("§cLe joueur " + name + " ne s'est jamais connecté");
            return;
        }

        CraftUser user = plugin.getUser(target.getUniqueId());
        if (user == null) {
            player.sendMessage("§cLe joueur " + name + " ne s'est jamais connecté");
            return;
        }
        CraftProfil profil = new CraftProfil(getProfil(profilName, user.getProfils()), profilNameTarget);
        if (profil == null) {
            player.sendMessage("§cLe profil " + profilName + " n'existe pas");
            return;
        }
        user.addProfils(profil);
        player.sendMessage("§aLe profil " + profilName + " a été copié");
    }

    public void transfer(String name,String targetName,String profilName,Player player) {
        Player target = plugin.getServer().getPlayer(name);
        OfflinePlayer target2 = plugin.getServer().getPlayer(targetName);
        if (target == null && target2 == null) {
            player.sendMessage("§cLe joueur " + name + " ou le joueur " + targetName + " ne s'est jamais connecté");
            return;
        }

        CraftUser user = plugin.getUser(target.getUniqueId());
        CraftUser user2 = plugin.getUser(target2.getUniqueId());

        if (user == null && user2 == null) {
            player.sendMessage("§cLe joueur " + name + " ou le joueur " + targetName + " ne s'est jamais connecté");
            return;
        }

        CraftProfil profil = getProfil(profilName, user.getProfils());
        user2.addProfils(profil);
        user.removeProfils(profil);
        player.sendMessage("§aLe profil " + profilName + " a été transféré a " + targetName);
    }

    public boolean HasProfils(String profilName, List<CraftProfil> profils) {
        for (CraftProfil profil : profils) {
            if (profil.getName().toUpperCase().equals(profilName.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public CraftProfil getProfil(String profilName, List<CraftProfil> profils) {
        for (CraftProfil profil : profils) {
            if (profil.getName().toUpperCase().equals(profilName.toUpperCase())) {
                return profil;
            }
        }
        return null;
    }
}
