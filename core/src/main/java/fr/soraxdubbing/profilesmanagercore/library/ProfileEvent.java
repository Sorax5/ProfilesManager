package fr.soraxdubbing.profilesmanagercore.library;

import fr.soraxdubbing.profilesmanagercore.model.CraftProfile;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

/**
 * Event that ha a player & profile
 */
public abstract class ProfileEvent extends PlayerEvent {

    private CraftProfile profile;

    public ProfileEvent(Player who, CraftProfile profile) {
        super(who);
        this.profile = profile;
    }

    public CraftProfile getProfile(){
        return this.profile;
    }
}
