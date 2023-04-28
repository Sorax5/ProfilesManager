package fr.soraxdubbing.profilesplayerstatistics;

import fr.soraxdubbing.profilesmanagercore.library.ProfileLoadedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventRegister implements Listener {

    @EventHandler
    public void onProfileLoad(ProfileLoadedEvent e){
        if(!e.getProfile().hasAddon("playerstats")){
            e.getProfile().addAddon(new PlayerStats(e.getPlayer()));
        }
    }
}
