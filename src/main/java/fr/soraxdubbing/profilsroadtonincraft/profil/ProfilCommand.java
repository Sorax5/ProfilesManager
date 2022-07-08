package fr.soraxdubbing.profilsroadtonincraft.profil;

import fr.soraxdubbing.profilsroadtonincraft.CraftUser.CraftUser;
import fr.soraxdubbing.profilsroadtonincraft.ProfilsRoadToNincraft;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProfilCommand implements CommandExecutor {

    private ProfilsRoadToNincraft plugin;

    public ProfilCommand(ProfilsRoadToNincraft plugin) {
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
                            case "location":
                                player.sendMessage("[§aProfil§r] §a" + "x: "+user.getActualProfil().getLastLocation().getX() + " y: "+user.getActualProfil().getLastLocation().getY() + " z: "+user.getActualProfil().getLastLocation().getZ());
                                break;
                            case "hp":
                                player.sendMessage("[§aProfil§r] §aVie max du profil: " + user.getActualProfil().getMaximumHealth());
                                break;
                            case "food":
                                player.sendMessage("[§aProfil§r] §aFood du profil: " + user.getActualProfil().getFoodLevel());
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
