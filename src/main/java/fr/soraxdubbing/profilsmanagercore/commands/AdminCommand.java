package fr.soraxdubbing.profilsmanagercore.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import fr.soraxdubbing.profilsmanagercore.model.CraftUser;
import fr.soraxdubbing.profilsmanagercore.model.CraftProfil;
import fr.soraxdubbing.profilsmanagercore.model.UsersManager;
import org.bukkit.entity.Player;

public class AdminCommand {
    @Command(
            aliases = "save",
            desc = "Commande d'administration du plugin ProfilsManagerCore",
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

    /*@Command(
            aliases = "load",
            desc = "Commande d'administration du plugin ProfilsManagerCore",
            perms = "pmc.admin.load",
            usage = "[player] [name]"
    )
    public void load(@Sender Player player, Player target, String name) {
        try{
            CraftUser user = ProfilsManagerCore.getInstance().getLoader().load(target.getUniqueId());

        } catch (Exception e) {
            player.sendMessage("§cErreur : §fLe profil n'existe pas !");
            return;
        }


    }*/

    @Command(
            aliases = "add",
            desc = "Commande d'administration du plugin ProfilsManagerCore",
            perms = "pmc.admin.add",
            usage = "[player] [name]"
    )
    public void add(@Sender Player player, Player target, String name) {
        CraftProfil profil = UsersManager.getInstance().CreateProfil(name);
        CraftUser user = UsersManager.getInstance().getUser(target);

        user.addProfils(profil);
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

        CraftProfil profil = user.getProfil(name);
        if(profil == null){
            player.sendMessage("§cErreur : §fLe profil n'existe pas !");
            return;
        }

        user.removeProfils(profil);
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

        CraftProfil profil = userOrigin.getProfil(name);
        if(profil == null){
            player.sendMessage("§cErreur : §fLe profil n'existe pas !");
            return;
        }

        userTarget.addProfils(profil);
        player.sendMessage("§aProfil " + name + " copié");
    }

    @Command(
            aliases = "transfer",
            desc = "Commande d'administration du plugin ProfilsManagerCore",
            perms = "pmc.admin.transfer",
            usage = "[player origin] [player target] [name]"
    )
    public void transfer(@Sender Player player, Player origin, Player target , String name) {
        CraftUser userOrigin = UsersManager.getInstance().getUser(origin);
        CraftUser userTarget = UsersManager.getInstance().getUser(target);

        CraftProfil profil = userOrigin.getProfil(name);
        if(profil == null){
            player.sendMessage("§cErreur : §fLe profil n'existe pas !");
            return;
        }

        userTarget.addProfils(profil);
        userOrigin.removeProfils(profil);
        player.sendMessage("§aProfil " + name + " transféré");
    }
}
