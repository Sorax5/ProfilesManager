package fr.soraxdubbing.profilsroadtonincraft.event;

import fr.soraxdubbing.profilsroadtonincraft.CraftUser.CraftUser;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ProfilLoadedEvent extends Event {

    private final Player target;
    private final CraftUser user;
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public ProfilLoadedEvent(Player target,CraftUser user){
        this.target = target;
        this.user = user;
    }

    public Player getTarget(){
        return this.target;
    }

    public CraftUser getUser(){
        return user;
    }
}
