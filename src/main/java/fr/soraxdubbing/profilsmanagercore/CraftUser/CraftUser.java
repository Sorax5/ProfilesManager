package fr.soraxdubbing.profilsmanagercore.CraftUser;

import fr.soraxdubbing.profilsmanagercore.profil.CraftProfil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CraftUser {

    private UUID identifier;
    private List<CraftProfil> profils;
    private transient CraftProfil loadedProfil;

    /**
     * Constructor of CraftUser
     * @param identifier
     */
    public CraftUser(UUID identifier){
        System.out.println("[ProfilsRoadToNincraft] Création de l'utilisateur " + identifier.toString());
        this.identifier = identifier;
        this.profils = new ArrayList<CraftProfil>();
        this.loadedProfil = null;
    }

    /**
     * Get the identifier of the user
     * @return UUID
     */
    public UUID getPlayerUuid(){
        return this.identifier;
    }

    /**
     * Get the list of profils of the user
     * @return List<CraftProfil>
     */
    public List<CraftProfil> getProfils(){
        return this.profils;
    }

    /**
     * add a profil to the user
     * @param CraftProfil _profil
     */
    public void addProfils(CraftProfil _profils){
        this.profils.add(_profils);
    }

    /**
     * remove a profil to the user
     * @param _profils
     */
    public void removeProfils(CraftProfil _profils){ this.profils.remove(_profils);}
    public CraftProfil getActualProfil(){
        return this.loadedProfil;
    }

    /**
     * see if the user have a profil
     * @return boolean
     */
    public Boolean HasActualProfil(){
        return this.loadedProfil != null;
    }

    /**
     * set a profil to the user
     * @param _actualProfil
     */
    public void setActualProfil(CraftProfil _actualProfil){
        if(!this.HasActualProfil()){
            this.loadedProfil = _actualProfil;
            this.profils.remove(this.loadedProfil);
        }
        else if(this.getActualProfil().getName().equals(_actualProfil.getName())){
            return;
        }
        else{
            this.profils.remove(_actualProfil);
            this.profils.add(this.loadedProfil);
            this.loadedProfil = _actualProfil;
        }
    }

    /**
     * set a profil to the user
     * @param _name
     */
    public void setActualProfil(String _name){
        if(this.loadedProfil == null){
            CraftProfil profil = getProfilByName(_name);
            if(profil == null){
                return;
            }
            this.profils.remove(profil);
            this.loadedProfil = profil;
        }
        else if (this.loadedProfil.getName().equals(_name)){
            return;
        }
        else{
            this.getProfils().add(this.loadedProfil);
            CraftProfil profil = getProfilByName(_name);
            if(profil == null){
                return;
            }
            this.profils.remove(profil);
            this.loadedProfil = profil;
        }
    }

    /**
     * get a profil by his name
     * @param _name
     * @return CraftProfil
     */
    public CraftProfil getProfilByName(String _name){
        for(CraftProfil profil : this.getProfils()){
            if(profil.getName().equals(_name)){
                return profil;
            }
        }
        return null;
    }

    /**
     * remove actual profil
     */
    public void removeActualProfil(){
        if(this.getActualProfil() != null){
            this.getProfils().add(this.getActualProfil());
            this.loadedProfil = null;
        }
    }

    /**
     * set whole list of profils
     * @param _profils
     */
    public void setProfils(List<CraftProfil> _profils){
        this.profils = _profils;
    }

    /**
     * clear the list of profils
     */
    public void clearProfils(){
        this.profils.clear();
    }

}
