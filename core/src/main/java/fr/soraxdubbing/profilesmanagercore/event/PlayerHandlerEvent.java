package fr.soraxdubbing.profilesmanagercore.event;

import fr.soraxdubbing.profilesmanagercore.ProfilesManagerCore;
import fr.soraxdubbing.profilesmanagercore.model.CraftUser;
import fr.soraxdubbing.profilesmanagercore.model.UsersManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

/**
 * Event called when a player join, quit, respawn or change world
 */
public class PlayerHandlerEvent implements Listener {

    private ProfilesManagerCore plugin;

    public PlayerHandlerEvent(){
        this.plugin = ProfilesManagerCore.getInstance();
    }

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent e){
        CraftUser user = UsersManager.getInstance().getUser(e.getPlayer());
        if(user == null){
            user = new CraftUser(e.getPlayer().getUniqueId());
            UsersManager.getInstance().addUser(user);
        }
        user.getLoadedProfil().LoadingProfile(e.getPlayer(),ProfilesManagerCore.getInstance());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        CraftUser user = UsersManager.getInstance().getUser(e.getPlayer());
        user.getLoadedProfil().UpdateProfile(e.getPlayer(),plugin);
        user.getLoadedProfil().LoadingProfile(e.getPlayer(),plugin);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e){
        CraftUser user = UsersManager.getInstance().getUser(e.getPlayer());
        user.getLoadedProfil().UpdateProfile(e.getPlayer(),plugin);
        user.getLoadedProfil().LoadingProfile(e.getPlayer(),plugin);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        CraftUser user = UsersManager.getInstance().getUser(e.getPlayer());
        user.getLoadedProfil().UpdateProfile(e.getPlayer(),plugin);
        UsersManager.getInstance().saveFileUsers();
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e){
        CraftUser user = UsersManager.getInstance().getUser(e.getPlayer());
        user.getLoadedProfil().UpdateProfile(e.getPlayer(),plugin);
        UsersManager.getInstance().saveFileUsers();
    }
}
