package fr.soraxdubbing.profilsmanagercore.model;

import junit.framework.TestCase;

import java.util.UUID;

public class CraftUserTest extends TestCase {

    private CraftUser user;
    private UUID uuid;
    private CraftProfil profil;
    public void setUp() throws Exception {
        super.setUp();
        this.uuid = UUID.randomUUID();
        user = new CraftUser(this.uuid);
        profil = new CraftProfil("test");
        user.addProfils(profil);
        user.setLoadedProfil(profil);
    }

    public void testGetUniqueId() {
        assertNotNull(user.getUniqueId());
        assertEquals(user.getUniqueId(), this.uuid);
    }

    public void testGetProfils() {
        assertNotNull(user.getProfils());
    }

    public void testAddProfils() {
        CraftProfil profil = new CraftProfil("test");
        user.addProfils(profil);
        assertTrue(user.getProfils().contains(profil));
    }

    public void testRemoveProfils() {
        CraftProfil profil = new CraftProfil("test");
        user.addProfils(profil);
        user.removeProfils(profil);
        assertFalse(user.getProfils().contains(profil));
    }

    public void testGetLoadedProfil() {
        assertNotNull(user.getLoadedProfil());
        assertEquals(user.getLoadedProfil(), profil);

    }

    public void testSetLoadedProfil() {
        CraftProfil profil = new CraftProfil("test");
        user.addProfils(profil);
        user.setLoadedProfil(profil);
        assertEquals(user.getLoadedProfil(), profil);
    }

    public void testGetProfil() {
        assertNotNull(user.getProfil("test"));
        assertEquals(user.getProfil("test"), profil);
    }
}