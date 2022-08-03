package fr.soraxdubbing.profilsmanagercore.profil;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import fr.soraxdubbing.profilsmanagercore.CraftUser.CraftUser;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ProfilsCommand implements CommandExecutor {

    private ProfilsManagerCore plugin;

    public ProfilsCommand( ProfilsManagerCore plugin ) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cVous devez être un joueur pour utiliser cette commande.");
            return true;
        }

        Player player = (Player) commandSender;
        CraftUser user = plugin.getUser(player.getUniqueId());

        user.getActualProfil().UpdateProfil(player, plugin);

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

        if (user.getActualProfil() != null) {
            ItemStack item = CreateItemProfils(user.getActualProfil(), gui);
            GuiItem guiItem = ItemBuilder.from(item).asGuiItem();
            gui.addItem(guiItem);
        }

        if (user.getProfils().size() > 0) {
            for (CraftProfil profil : user.getProfils()) {
                ItemStack item = CreateItemProfils(profil, gui);

                GuiItem guiItem = ItemBuilder.from(item).asGuiItem(event -> {
                    user.getActualProfil().UpdateProfil(player, plugin);
                    user.setActualProfil(profil);
                    user.getActualProfil().LoadingProfil(player, plugin);
                    player.sendMessage("[ProfilsManagerCore]§a Profil " + user.getActualProfil().getName() + " chargé.");
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
        return true;
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
