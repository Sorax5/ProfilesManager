package fr.soraxdubbing.profilsmanagercore.commands.profil;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import fr.soraxdubbing.profilsmanagercore.model.CraftUser;
import fr.soraxdubbing.profilsmanagercore.model.UsersManager;
import org.bukkit.entity.Player;

public class ProfilGetterCommand {
    @Command(
            aliases = "date",
            desc = "Commande d'administration du plugin ProfilsManagerCore",
            perms = "pmc.profil.get.date",
            usage = ""
    )
    public void date(@Sender Player player) {
        try{
            CraftUser user = UsersManager.getInstance().getUser(player);
            player.sendMessage("§aDate de création du profil : " + user.getLoadedProfil().getDateString());
        }
        catch (Exception e){
            player.sendMessage("§cErreur : §fLe joueur n'est pas enregistré !");
            return;
        }
    }

    @Command(
            aliases = "name",
            desc = "Commande d'administration du plugin ProfilsManagerCore",
            perms = "pmc.profil.get.name",
            usage = ""
    )
    public void name(@Sender Player player) {
        try{
            CraftUser user = UsersManager.getInstance().getUser(player);
            player.sendMessage("§aNom du profil : " + user.getLoadedProfil().getName());
        }
        catch (Exception e){
            player.sendMessage("§cErreur : §fLe joueur n'est pas enregistré !");
            return;
        }
    }
}
