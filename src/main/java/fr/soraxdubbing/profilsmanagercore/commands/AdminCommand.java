package fr.soraxdubbing.profilsmanagercore.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
import fr.soraxdubbing.profilsmanagercore.profil.CraftProfil;
import org.bukkit.entity.Player;

public class AdminCommand {
    @Command(
            aliases = "save",
            desc = "Commande d'administration du plugin ProfilsManagerCore",
            perms = "pmc.admin.save",
            usage = "[player] [name]"
    )
    public void save(@Sender Player player, Player target, String name) {
        CraftUser user = ProfilsManagerCore.getInstance().getUser(target.getUniqueId());

        if(user == null){
            player.sendMessage("§cErreur : §fLe joueur n'est pas enregistré !");
            return;
        }

        player.sendMessage("§aLe profil " + name + " a été sauvegardé");

        ProfilsManagerCore.getInstance().getSaver().save(user);
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
        CraftProfil profil = new CraftProfil(name);
        CraftUser user = ProfilsManagerCore.getInstance().getUser(target.getUniqueId());

        if(user == null){
            player.sendMessage("§cErreur : §fLe joueur n'est pas enregistré !");
            return;
        }

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
        CraftUser user = ProfilsManagerCore.getInstance().getUser(target.getUniqueId());

        if(user == null){
            player.sendMessage("§cErreur : §fLe joueur n'est pas enregistré !");
            return;
        }

        CraftProfil profil = user.getProfilByName(name);
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
        CraftUser userOrigin = ProfilsManagerCore.getInstance().getUser(origin.getUniqueId());
        CraftUser userTarget = ProfilsManagerCore.getInstance().getUser(target.getUniqueId());

        if(userOrigin == null || userTarget == null){
            player.sendMessage("§cErreur : §fLe joueur n'est pas enregistré !");
            return;
        }

        CraftProfil profil = userOrigin.getProfilByName(name);
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
        CraftUser userOrigin = ProfilsManagerCore.getInstance().getUser(origin.getUniqueId());
        CraftUser userTarget = ProfilsManagerCore.getInstance().getUser(target.getUniqueId());

        if(userOrigin == null || userTarget == null){
            player.sendMessage("§cErreur : §fLe joueur n'est pas enregistré !");
            return;
        }

        CraftProfil profil = userOrigin.getProfilByName(name);
        if(profil == null){
            player.sendMessage("§cErreur : §fLe profil n'existe pas !");
            return;
        }

        userTarget.addProfils(profil);
        userOrigin.removeProfils(profil);
        player.sendMessage("§aProfil " + name + " transféré");
    }
}
