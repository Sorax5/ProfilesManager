package fr.soraxdubbing.profilesmanagercommands.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import fr.soraxdubbing.profilesmanagercore.model.CraftProfile;
import fr.soraxdubbing.profilesmanagercore.model.CraftUser;
import fr.soraxdubbing.profilesmanagercore.model.UsersManager;
import org.bukkit.entity.Player;

public class AdministrationCommands {
    @Command(
            aliases = "save",
            desc = "Commande d'administration du plugin ProfilesManagerCore",
            perms = "pmc.admin.save",
            usage = "[player] [name]"
    )
    public void save(@Sender Player player, Player target, String name) {
        CraftUser user = UsersManager.getInstance().getUser(target);

        if(user == null){
            player.sendMessage("§cErreur : §fLe joueur n'est pas enregistré !");
            return;
        }

        player.sendMessage("§aLe profil " + name + " a été sauvegardé");

        UsersManager.getInstance().saveFileUsers();
    }

    @Command(
            aliases = "add",
            desc = "Commande d'administration du plugin ProfilesManagerCore",
            perms = "pmc.admin.add",
            usage = "[player] [name]"
    )
    public void add(@Sender Player player, Player target, String name) {
        CraftProfile profile = UsersManager.getInstance().CreateProfil(name);
        CraftUser user = UsersManager.getInstance().getUser(target);

        user.addProfils(profile);
        player.sendMessage("§aProfil " + name + " ajouté");
    }

    @Command(
            aliases = "remove",
            desc = "Commande d'administration du plugin ProfilsManagerCore",
            perms = "pmc.admin.remove",
            usage = "[player] [name]"
    )
    public void remove(@Sender Player player, Player target, String name) {
        CraftUser user = UsersManager.getInstance().getUser(target);

        CraftProfile profile = user.getProfil(name);
        if(profile == null){
            player.sendMessage("§cErreur : §fLe profil n'existe pas !");
            return;
        }

        user.removeProfils(profile);
        player.sendMessage("§aProfil " + name + " supprimé");
    }

    @Command(
            aliases = "copy",
            desc = "Commande d'administration du plugin ProfilsManagerCore",
            perms = "pmc.admin.copy",
            usage = "[player origin] [player target] [name]"
    )
    public void copy(@Sender Player player, Player origin, Player target , String name) {
        CraftUser userOrigin = UsersManager.getInstance().getUser(origin);
        CraftUser userTarget = UsersManager.getInstance().getUser(target);

        CraftProfile profile = userOrigin.getProfil(name);
        if(profile == null){
            player.sendMessage("§cErreur : §fLe profil n'existe pas !");
            return;
        }

        userTarget.addProfils(profile);
        player.sendMessage("§aProfile " + name + " copié");
    }

    @Command(
            aliases = "transfer",
            desc = "Commande d'administration du plugin ProfilesManagerCore",
            perms = "pmc.admin.transfer",
            usage = "[player origin] [player target] [name]"
    )
    public void transfer(@Sender Player player, Player origin, Player target , String name) {
        CraftUser userOrigin = UsersManager.getInstance().getUser(origin);
        CraftUser userTarget = UsersManager.getInstance().getUser(target);

        CraftProfile profile = userOrigin.getProfil(name);
        if(profile == null){
            player.sendMessage("§cErreur : §fLe profil n'existe pas !");
            return;
        }

        userTarget.addProfils(profile);
        userOrigin.removeProfils(profile);
        player.sendMessage("§aProfil " + name + " transféré");
    }
}
