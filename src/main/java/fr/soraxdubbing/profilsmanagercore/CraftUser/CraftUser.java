package fr.soraxdubbing.profilsmanagercore.CraftUser;

import fr.soraxdubbing.profilsmanagercore.profil.CraftProfil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CraftUser {

    private UUID identifier;
    private transient List<CraftProfil> profils;
    private transient CraftProfil loadedProfil;

    public CraftUser(UUID identifier){
        System.out.println("[ProfilsRoadToNincraft] Création de l'utilisateur " + identifier.toString());
        this.identifier = identifier;
        this.profils = new ArrayList<CraftProfil>();
        this.loadedProfil = null;
    }

    public UUID getPlayerUuid(){
        return this.identifier;
    }
    public List<CraftProfil> getProfils(){
        return this.profils;
    }
    public void addProfils(CraftProfil _profils){
        this.profils.add(_profils);
    }
    public void removeProfils(CraftProfil _profils){ this.profils.remove(_profils);}
    public CraftProfil getActualProfil(){
        return this.loadedProfil;
    }

    public Boolean HasActualProfil(){
        return this.loadedProfil != null;
    }
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

    public CraftProfil getProfilByName(String _name){
        for(CraftProfil profil : this.getProfils()){
            if(profil.getName().equals(_name)){
                return profil;
            }
        }
        return null;
    }

    public void removeActualProfil(){
        if(this.getActualProfil() != null){
            this.getProfils().add(this.getActualProfil());
            this.loadedProfil = null;
        }
    }

    public void setProfils(List<CraftProfil> _profils){
        this.profils = _profils;
    }

    public void clearProfils(){
        this.profils.clear();
    }

}
