package fr.soraxdubbing.profilsmanagercore.event;

import fr.soraxdubbing.profilsmanagercore.profil.CraftProfil;
import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.Manager.CraftUserManager;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.List;

public class Loader implements Listener {

    private CraftUserManager manager;
    private ProfilsManagerCore plugin;
    private List<CraftUser> users;

    public Loader(ProfilsManagerCore plugin){
        this.plugin = plugin;
        this.manager = plugin.getManager();
        this.users = plugin.getUsers();
    }

    @EventHandler
    public void onJoin(PlayerLoginEvent e){
        CraftUser user = manager.loadCraftUser(e.getPlayer());
        users.add(user);

        if (!user.HasActualProfil() && user.getProfils().size() == 0){
            CraftProfil profil = new CraftProfil("Default");
            user.addProfils(profil);
            user.setActualProfil(profil);
            user.getActualProfil().UpdateProfil(e.getPlayer(),plugin);
        }
        else if (!user.HasActualProfil() && user.getProfils().size() > 0){
            user.setActualProfil(user.getProfils().get(0));
        }
        user.getActualProfil().LoadingProfil(e.getPlayer(),plugin);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        CraftUser user = plugin.getUser(e.getPlayer().getUniqueId());
        if(user != null){
            user.getActualProfil().UpdateProfil(e.getPlayer(),plugin);
            manager.saveCraftUser(user);
            user.getActualProfil().LoadingProfil(e.getPlayer(),plugin);
        }
    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent e){
        CraftUser user = plugin.getUser(e.getPlayer().getUniqueId());
        if(user != null){
            user.getActualProfil().UpdateProfil(e.getPlayer(),plugin);
            manager.saveCraftUser(user);
            user.getActualProfil().LoadingProfil(e.getPlayer(),plugin);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        CraftUser user = plugin.getUser(e.getPlayer().getUniqueId());
        if(user != null){
            user.getActualProfil().UpdateProfil(e.getPlayer(),plugin);
            manager.saveCraftUser(user);
            users.remove(user);
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e){
        CraftUser user = plugin.getUser(e.getPlayer().getUniqueId());
        if(user != null){
            user.getActualProfil().UpdateProfil(e.getPlayer(),plugin);
            manager.saveCraftUser(user);
            users.remove(user);
        }
    }



    public void save() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            CraftUser user = plugin.getUser(onlinePlayer.getUniqueId());
            if (user != null) {
                user.getActualProfil().UpdateProfil(onlinePlayer, plugin);
                manager.saveCraftUser(user);
                plugin.getUsers().remove(user);
            }
        }
    }
}
