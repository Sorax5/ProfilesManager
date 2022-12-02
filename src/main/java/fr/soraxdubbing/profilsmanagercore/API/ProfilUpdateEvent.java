package fr.soraxdubbing.profilsmanagercore.API;

import fr.soraxdubbing.profilsmanagercore.profil.CraftProfil;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ProfilUpdateEvent extends Event {

    private Player target;
    private CraftProfil profil;
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public ProfilUpdateEvent(Player target,CraftProfil profil){
        this.target = target;
        this.profil = profil;
    }

    public CraftProfil getProfil(){
        return this.profil;
    }

    public Player getTarget(){
        return this.target;
    }
}
