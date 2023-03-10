package fr.soraxdubbing.profilsmanagercore.library;

import fr.soraxdubbing.profilsmanagercore.model.CraftProfil;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a profil is loaded
 */
public class ProfilLoadedEvent extends Event {

    private final Player target;
    private final CraftProfil profil;
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public ProfilLoadedEvent(Player target, CraftProfil profil){
        this.target = target;
        this.profil = profil;
    }

    public Player getTarget(){
        return this.target;
    }

    public CraftProfil getProfil(){
        return profil;
    }
}
