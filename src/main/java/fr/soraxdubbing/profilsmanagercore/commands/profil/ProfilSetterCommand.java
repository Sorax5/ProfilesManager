package fr.soraxdubbing.profilsmanagercore.commands.profil;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.manager.UsersManager;
import org.bukkit.entity.Player;

public class ProfilSetterCommand {
    @Command(
            aliases = "set",
            desc = "Commande d'administration du plugin ProfilsManagerCore",
            perms = "pmc.profil.set",
            usage = "[name]"
    )
    public void set(@Sender Player player , String name) {
        try{
            CraftUser user = UsersManager.getInstance().getUser(player);
            user.getLoadedProfil().setName(name);
            player.sendMessage("§aLe nom du profil a été changé en " + name);
        }
        catch (Exception e){
            player.sendMessage("§cErreur : §fLe joueur n'est pas enregistré !");
            return;
        }
    }
}
