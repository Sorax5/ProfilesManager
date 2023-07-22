package fr.soraxdubbing.profilesmanagercommands.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import fr.soraxdubbing.profilesmanagercore.ProfilesManagerCore;
import fr.soraxdubbing.profilesmanagercore.model.CraftProfile;
import fr.soraxdubbing.profilesmanagercore.model.CraftUser;
import fr.soraxdubbing.profilesmanagercore.model.UsersManager;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class UserCommands {

    @Command(
            aliases = "consult",
            desc = "Commande de consultation des profils",
            perms = "pmc.profils",
            usage = ""
    )
    public void profiles(@Sender Player player) {
        try{
            CraftUser user = UsersManager.getInstance().getUser(player);
            user.getLoadedProfil().UpdateProfile(player, ProfilesManagerCore.getInstance());

            PaginatedGui gui = Gui.paginated()
                    .title(Component.text("§6Profil"))
                    .rows(6)
                    .create();

            for(int i = 1;i <= 9;i++){
                gui.setItem(6,i, ItemBuilder.from(Material.STAINED_GLASS_PANE).asGuiItem());
            }

            // Previous item
            gui.setItem(6, 3, ItemBuilder.from(Material.PAPER).setName("Previous").asGuiItem(event -> gui.previous()));
            // Next item
            gui.setItem(6, 7, ItemBuilder.from(Material.PAPER).setName("Next").asGuiItem(event -> gui.next()));

            if (user.getProfils().size() > 0) {
                for (CraftProfile profil : user.getProfils()) {
                    ItemStack item = CreateItemProfils(profil, gui);
                    GuiItem guiItem = null;
                    if(profil != user.getLoadedProfil()){
                        guiItem = ItemBuilder.from(item).asGuiItem(event -> {
                            user.getLoadedProfil().UpdateProfile(player, ProfilesManagerCore.getInstance());

                            user.setLoadedProfil(profil.getName());
                            user.getLoadedProfil().LoadingProfile(player, ProfilesManagerCore.getInstance());

                            player.sendMessage("[ProfilsManagerCore]§a Profil " + user.getLoadedProfil().getName() + " chargé.");
                            gui.close(player);
                        });
                    }
                    else {
                        guiItem = ItemBuilder.from(item).asGuiItem();
                    }

                    gui.addItem(guiItem);
                }

            }
            gui.disableItemDrop();
            gui.disableItemTake();
            gui.disableItemPlace();
            gui.disableItemDrop();
            gui.disableItemSwap();
            gui.open(player);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Command(
            aliases = "date",
            desc = "Commande d'administration du plugin ProfilsManagerCore",
            perms = "pmc.profil.get.date",
            usage = ""
    )
    public void date(@Sender Player player) {
        try{
            CraftUser user = UsersManager.getInstance().getUser(player);
            player.sendMessage("§aDate de création du profil : " + user.getLoadedProfil().getDateString());
        }
        catch (Exception e){
            player.sendMessage("§cErreur : §fLe joueur n'est pas enregistré !");
            return;
        }
    }

    @Command(
            aliases = "get",
            desc = "Commande d'administration du plugin ProfilsManagerCore",
            perms = "pmc.profil.get.name",
            usage = ""
    )
    public void get(@Sender Player player) {
        try{
            CraftUser user = UsersManager.getInstance().getUser(player);
            player.sendMessage("§aNom du profil : " + user.getLoadedProfil().getName());
        }
        catch (Exception e){
            player.sendMessage("§cErreur : §fLe joueur n'est pas enregistré !");
            return;
        }
    }

    @Command(
            aliases = "set",
            desc = "Commande d'administration du plugin ProfilsManagerCore",
            perms = "pmc.profil.set.name",
            usage = "[name]"
    )
    public void set(@Sender Player player , String name) {
        try{
            CraftUser user = UsersManager.getInstance().getUser(player);
            user.getLoadedProfil().setName(name);
            player.sendMessage("§aLe nom du profil a été changé en " + name);
        }
        catch (Exception e){
            player.sendMessage("§cErreur : §fLe joueur n'est pas enregistré !");
            return;
        }
    }

    /**
     * Create an item for the gui
     * @param profile the profile
     * @param gui the gui
     * @return the item
     */
    public ItemStack CreateItemProfils(CraftProfile profile, PaginatedGui gui){
        ItemStack itemPaper = new ItemStack(Material.PAPER);
        ItemMeta Meta = itemPaper.getItemMeta();
        Meta.setDisplayName(ChatColor.AQUA + "§l" + profile.getName());
        Meta.setLore(Collections.singletonList(profile.toString()));
        itemPaper.setItemMeta(Meta);
        return itemPaper;
    }
}
