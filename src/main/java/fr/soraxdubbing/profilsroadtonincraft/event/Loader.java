package fr.soraxdubbing.profilsroadtonincraft.event;

import fr.soraxdubbing.profilsroadtonincraft.profil.CraftProfil;
import fr.soraxdubbing.profilsroadtonincraft.CraftUser.CraftUser;
import fr.soraxdubbing.profilsroadtonincraft.Manager.CraftUserManager;
import fr.soraxdubbing.profilsroadtonincraft.ProfilsRoadToNincraft;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class Loader implements Listener {

    private CraftUserManager manager;
    private ProfilsRoadToNincraft plugin;
    private List<CraftUser> users;

    public Loader(ProfilsRoadToNincraft plugin){
        this.plugin = plugin;
        this.manager = plugin.getManager();
        this.users = plugin.getUsers();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        System.out.println("[RoadToNincraft] " + e.getPlayer().getName() + " a rejoint le serveur");
        CraftUser user = manager.loadCraftUser(e.getPlayer());

        System.out.println("[RoadToNincraft] " + e.getPlayer().getName() + " ajouté à la liste des utilisateurs");
        users.add(user);

        if (user.getActualProfil() == null && user.getProfils().size() == 0){
            System.out.println("[RoadToNincraft] " + e.getPlayer().getName() + " n'a aucun profils");
            CraftProfil profil = new CraftProfil("Default");
            user.setActualProfil(profil);
            CraftProfil.UpdateActualProfil(e.getPlayer(),plugin);
        }
        else if (user.getActualProfil() == null && user.getProfils().size() > 0){
            System.out.println("[RoadToNincraft] " + e.getPlayer().getName() + " n'a pas de profil actuel");
            user.setActualProfil(user.getProfils().get(0));
            user.getProfils().remove(user.getActualProfil());
        }

        System.out.println("[RoadToNincraft] " + e.getPlayer().getName() + " charge le profil actuel");
        CraftProfil.LoadingProfil(e.getPlayer(), user,plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        System.out.println("[RoadToNincraft] " + e.getPlayer().getName() + " a quitté le serveur");
        CraftUser user = plugin.getUser(e.getPlayer().getUniqueId());
        if(user != null){
            CraftProfil.UpdateActualProfil(Bukkit.getPlayer(user.getPlayerUuid()),plugin);
            plugin.getLogger().info("[RoadToNincraft] " + e.getPlayer().getName() + " a sauvegardé son profil");
            manager.saveCraftUser(user);
            users.remove(user);
        }
    }



    public void save() {
        plugin.getLogger().info("Sauvegarde des Users");
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            CraftUser user = plugin.getUser(onlinePlayer.getUniqueId());
            if (user != null) {
                CraftProfil.UpdateActualProfil(Bukkit.getPlayer(user.getPlayerUuid()),plugin);
                manager.saveCraftUser(user);
                plugin.getUsers().remove(user);
            }
        }
    }
}
