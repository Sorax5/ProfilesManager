package fr.soraxdubbing.profilsmanagercore.event;

import fr.soraxdubbing.profilsmanagercore.Serialisation.SerialisationAddonData;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AddonRegisterEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private SerialisationAddonData data;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public AddonRegisterEvent(SerialisationAddonData data) {
        this.data = data;
    }

    public SerialisationAddonData getData(){
        return data;
    }

    public void registerAddon(Class addon){
        data.addClass(addon);
    }

}
