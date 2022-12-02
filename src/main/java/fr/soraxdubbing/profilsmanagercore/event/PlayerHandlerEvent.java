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

import java.util.List;

public class PlayerHandlerEvent implements Listener {

    private CraftUserManager manager;
    private ProfilsManagerCore plugin;
    private List<CraftUser> users;

    public PlayerHandlerEvent(ProfilsManagerCore plugin, CraftUserManager manager, List<CraftUser> users){
        this.plugin = plugin;
        this.manager = manager;
        this.users = users;
    }

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent e){
        CraftUser user = ProfilsManagerCore.getInstance().getLoader().load(e.getPlayer().getUniqueId());

        if(user == null){
            user = new CraftUser(e.getPlayer().getUniqueId());
            CraftProfil profil = new CraftProfil("default");
            user.setActualProfil(profil);
            ProfilsManagerCore.getInstance().getSaver().save(user);
        }

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
    public void onPlayerRespawn(PlayerRespawnEvent e){
        CraftUser user = plugin.getUser(e.getPlayer().getUniqueId());
        if(user == null){
            user = manager.loadCraftUser(e.getPlayer());
            users.add(user);
        }
        user.getActualProfil().UpdateProfil(e.getPlayer(),plugin);
        ProfilsManagerCore.getInstance().getSaver().save(user);
        user.getActualProfil().LoadingProfil(e.getPlayer(),plugin);

    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e){
        CraftUser user = plugin.getUser(e.getPlayer().getUniqueId());
        if(user == null){
            user = manager.loadCraftUser(e.getPlayer());
            users.add(user);
        }
        user.getActualProfil().UpdateProfil(e.getPlayer(),plugin);
        ProfilsManagerCore.getInstance().getSaver().save(user);
        user.getActualProfil().LoadingProfil(e.getPlayer(),plugin);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        CraftUser user = plugin.getUser(e.getPlayer().getUniqueId());
        if(user != null){
            user.getActualProfil().UpdateProfil(e.getPlayer(),plugin);
            ProfilsManagerCore.getInstance().getSaver().save(user);
            users.remove(user);
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e){
        CraftUser user = plugin.getUser(e.getPlayer().getUniqueId());
        if(user != null){
            user.getActualProfil().UpdateProfil(e.getPlayer(),plugin);
            ProfilsManagerCore.getInstance().getSaver().save(user);
            users.remove(user);
        }
    }

    public void save() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            CraftUser user = plugin.getUser(onlinePlayer.getUniqueId());
            if (user != null) {
                user.getActualProfil().UpdateProfil(onlinePlayer, plugin);
                ProfilsManagerCore.getInstance().getSaver().save(user);
                plugin.getUsers().remove(user);
            }
        }
    }
}