package fr.soraxdubbing.profilsroadtonincraft.Addon;

public abstract class AddonData {

    private final String addonName;

    public AddonData(String addonName) {
        this.addonName = addonName;
    }

    public String getAddonName(){
        return this.addonName;
    }

}
