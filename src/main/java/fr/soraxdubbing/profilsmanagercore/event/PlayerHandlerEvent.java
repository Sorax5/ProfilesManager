package fr.soraxdubbing.profilsmanagercore.event;

import fr.soraxdubbing.profilsmanagercore.Manager.UsersManager;
import fr.soraxdubbing.profilsmanagercore.profil.CraftProfil;
import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerHandlerEvent implements Listener {

    private ProfilsManagerCore plugin;

    public PlayerHandlerEvent(){
        this.plugin = ProfilsManagerCore.getInstance();
    }

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent e){
        CraftUser user = UsersManager.getInstance().getUser(e.getPlayer().getUniqueId());

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
        CraftUser user = UsersManager.getInstance().getUser(e.getPlayer().getUniqueId());
        user.getActualProfil().UpdateProfil(e.getPlayer(),plugin);
        user.getActualProfil().LoadingProfil(e.getPlayer(),plugin);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e){
        CraftUser user = UsersManager.getInstance().getUser(e.getPlayer().getUniqueId());
        user.getActualProfil().UpdateProfil(e.getPlayer(),plugin);
        user.getActualProfil().LoadingProfil(e.getPlayer(),plugin);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        CraftUser user = UsersManager.getInstance().getUser(e.getPlayer().getUniqueId());
        user.getActualProfil().UpdateProfil(e.getPlayer(),plugin);
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e){
        CraftUser user = UsersManager.getInstance().getUser(e.getPlayer().getUniqueId());
        user.getActualProfil().UpdateProfil(e.getPlayer(),plugin);
    }

    public void save() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            CraftUser user = UsersManager.getInstance().getUser(onlinePlayer.getUniqueId());
            user.getActualProfil().UpdateProfil(onlinePlayer, plugin);
        }
    }
}
