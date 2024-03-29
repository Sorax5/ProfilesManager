package fr.soraxdubbing.profilesmanagercore.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CraftUser {

    private UUID identifier;
    private List<CraftProfile> profils;
    private int selectedProfil;

    /**
     * Constructor of CraftUser
     * @param identifier UUID
     */
    public CraftUser(UUID identifier){
        this.identifier = identifier;
        this.profils = new ArrayList<CraftProfile>();
        this.selectedProfil = 0;
        this.profils.add(new CraftProfile("default"));
    }

    /**
     * Get the identifier of the user
     * @return UUID
     */
    public UUID getUniqueId(){
        return this.identifier;
    }

    /**
     * Get the list of profils of the user
     * @return list of CraftProfil
     */
    public List<CraftProfile> getProfils(){
        return this.profils;
    }

    /**
     * add a profil to the user
     * @param _profils CraftProfil
     */
    public void addProfils(CraftProfile _profils){
        this.profils.add(_profils);
    }

    /**
     * remove a profil to the user
     * @param _profils CraftProfil
     */
    public void removeProfils(CraftProfile _profils){ this.profils.remove(_profils);}
    public CraftProfile getLoadedProfil(){
        return this.profils.get(this.selectedProfil);
    }

    /**
     * set a profil to the user
     * @param _actualProfil CraftProfil
     */
    public void setLoadedProfil(CraftProfile _actualProfil){
        this.selectedProfil = this.profils.indexOf(_actualProfil);
    }

    /**
     * set a profil to the user
     * @param index int
     */
    public void setLoadedProfil(int index){
        try{
            this.selectedProfil = index;
        } catch (Exception e) {
            this.selectedProfil = 0;
        }
    }

    /**
     * set a profil to the user
     * @param _name String
     */
    public void setLoadedProfil(String _name){
        try{
            this.selectedProfil = this.profils.indexOf(this.getProfil(_name));
        } catch (Exception e) {
            this.selectedProfil = 0;
        }
    }

    /**
     * get a profil by his name
     * @param _name String
     * @return CraftProfil
     */
    public CraftProfile getProfil(String _name){
        for(CraftProfile profil : this.getProfils()){
            if(profil.getName().equals(_name)){
                return profil;
            }
        }
        return null;
    }
}
