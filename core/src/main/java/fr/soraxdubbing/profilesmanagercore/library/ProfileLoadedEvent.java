package fr.soraxdubbing.profilesmanagercore.library;

import fr.soraxdubbing.profilesmanagercore.model.CraftProfile;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a profil is loaded
 */
public class ProfileLoadedEvent extends ProfileEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public ProfileLoadedEvent(Player target, CraftProfile profil){
        super(target,profil);
    }
}
