package fr.soraxdubbing.profilsmanagercore.profil;

import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProfilCommand implements CommandExecutor {

    private ProfilsManagerCore plugin;

    public ProfilCommand(ProfilsManagerCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cVous devez être un joueur pour utiliser cette commande !");
        }

        Player player = (Player) commandSender;

        CraftUser user = plugin.getUser(player.getUniqueId());

        switch (strings.length) {
            case 0:
                player.sendMessage("§c/profil <setname,get>");
                break;
            case 1:
                switch (strings[0]) {
                    case "setname":
                        player.sendMessage("§c/profil setname <name>");
                        break;
                    case "get":
                        player.sendMessage("§c/profil get <name,date,location,hp,food>");
                        break;
                }
                break;
            case 2:
                switch (strings[0]) {
                    case "setname":
                        user.getActualProfil().setName(strings[1]);
                        player.sendMessage("§aVotre nom a bien été changé !");
                        break;
                    case "get":
                        switch (strings[1]) {
                            case "name":
                                player.sendMessage("[§aProfil§r] §aNom du profil: " + user.getActualProfil().getName());
                                break;
                            case "date":
                                player.sendMessage("[§aProfil§r] §aDate de création du profil: " + user.getActualProfil().getDateString());
                                break;
                            default:
                                player.sendMessage("§c/profil get <name,date,location,hp,food>");
                                break;
                        }
                        break;
                    default:
                        player.sendMessage("§c/profil <setname,get>");
                        break;
                }
                break;
        }
        return true;
    }
}
