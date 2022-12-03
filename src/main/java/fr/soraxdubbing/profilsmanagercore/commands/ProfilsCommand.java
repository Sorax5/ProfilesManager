package fr.soraxdubbing.profilsmanagercore.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.manager.UsersManager;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
import fr.soraxdubbing.profilsmanagercore.profil.CraftProfil;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ProfilsCommand {

    @Command(
            aliases = "profils",
            desc = "Commande d'administration du plugin ProfilsManagerCore",
            perms = "pmc.profils",
            usage = ""
    )
    public void profils(@Sender Player player) {
        try{
            CraftUser user = UsersManager.getInstance().getUser(player);
            user.getLoadedProfil().UpdateProfil(player, ProfilsManagerCore.getInstance());

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

            if (user.getLoadedProfil() != null) {
                ItemStack item = CreateItemProfils(user.getLoadedProfil(), gui);
                GuiItem guiItem = ItemBuilder.from(item).asGuiItem();
                gui.addItem(guiItem);
            }

            if (user.getProfils().size() > 0) {
                for (CraftProfil profil : user.getProfils()) {
                    ItemStack item = CreateItemProfils(profil, gui);

                    GuiItem guiItem = ItemBuilder.from(item).asGuiItem(event -> {
                        user.getLoadedProfil().UpdateProfil(player, ProfilsManagerCore.getInstance());
                        user.setLoadedProfil(profil);
                        user.getLoadedProfil().LoadingProfil(player, ProfilsManagerCore.getInstance());
                        player.sendMessage("[ProfilsManagerCore]§a Profil " + user.getLoadedProfil().getName() + " chargé.");
                        gui.open(player);
                    });

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
        player.sendMessage("[ProfilsManagerCore] §aProfils chargé.");

    }

    public ItemStack CreateItemProfils(CraftProfil profil, PaginatedGui gui){
        ItemStack itemPaper = new ItemStack(Material.PAPER);
        ItemMeta Meta = itemPaper.getItemMeta();
        Meta.setDisplayName(ChatColor.AQUA + "§l" + profil.getName());
        Meta.setLore(Collections.singletonList(profil.toString()));
        itemPaper.setItemMeta(Meta);
        return itemPaper;
    }
}
