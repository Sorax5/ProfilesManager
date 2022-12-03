package fr.soraxdubbing.profilsmanagercore.event;

import fr.soraxdubbing.profilsmanagercore.manager.UsersManager;
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
        CraftUser user = UsersManager.getInstance().getUser(e.getPlayer());

        if (!user.hasLoadedProfil() && user.getProfils().size() == 0){
            CraftProfil profil = new CraftProfil("Default");
            user.addProfils(profil);
            user.setLoadedProfil(profil);
            user.getLoadedProfil().UpdateProfil(e.getPlayer(),plugin);
        }
        else if (!user.hasLoadedProfil() && user.getProfils().size() > 0){
            user.setLoadedProfil(user.getProfils().get(0));
        }
        user.getLoadedProfil().LoadingProfil(e.getPlayer(),plugin);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        CraftUser user = UsersManager.getInstance().getUser(e.getPlayer());
        user.getLoadedProfil().UpdateProfil(e.getPlayer(),plugin);
        user.getLoadedProfil().LoadingProfil(e.getPlayer(),plugin);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e){
        CraftUser user = UsersManager.getInstance().getUser(e.getPlayer());
        user.getLoadedProfil().UpdateProfil(e.getPlayer(),plugin);
        user.getLoadedProfil().LoadingProfil(e.getPlayer(),plugin);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        CraftUser user = UsersManager.getInstance().getUser(e.getPlayer());
        user.getLoadedProfil().UpdateProfil(e.getPlayer(),plugin);
        UsersManager.getInstance().saveFileUsers();
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e){
        CraftUser user = UsersManager.getInstance().getUser(e.getPlayer());
        user.getLoadedProfil().UpdateProfil(e.getPlayer(),plugin);
        UsersManager.getInstance().saveFileUsers();
    }

    public void save() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            CraftUser user = UsersManager.getInstance().getUser(onlinePlayer);
            user.getLoadedProfil().UpdateProfil(onlinePlayer, plugin);
        }
    }
}
