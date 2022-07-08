package fr.soraxdubbing.profilsroadtonincraft.profil;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import fr.soraxdubbing.profilsroadtonincraft.CraftUser.CraftUser;
import fr.soraxdubbing.profilsroadtonincraft.ProfilsRoadToNincraft;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfilsCommand implements CommandExecutor {

    private ProfilsRoadToNincraft plugin;
    private List<CraftUser> users;

    public ProfilsCommand( ProfilsRoadToNincraft plugin ) {
        this.plugin = plugin;
        this.users = plugin.getUsers();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cVous devez être un joueur pour utiliser cette commande.");
            return true;
        }

        Player player = (Player) commandSender;
        CraftUser user = plugin.getUser(player.getUniqueId());

        CraftProfil.UpdateActualProfil(player,plugin);

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
                    CraftProfil.UpdateActualProfil(player,plugin);
                    user.getProfils().add(user.getActualProfil());
                    user.setActualProfil(profil);
                    user.getProfils().remove(profil);
                    CraftProfil.LoadingProfil(player, user,plugin);
                    player.sendMessage("[RoadToNincraft]§a Profil " + user.getActualProfil().getName() + " chargé.");

                    /*Horse horse = plugin.getHorses().get(player.getUniqueId());

                    if(horse != null) {
                        horse.remove();
                        plugin.getHorses().remove(player.getUniqueId());
                    }*/
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

        /*List<String> lLore = new ArrayList<>();
        lLore.add("§a§lStatistics: ");
        lLore.add("§7" + profil.getMaximumHealth() + " §c❤§r " + "| §b" + profil.getMoney() + " $");
        lLore.add("§7§lx: §r§3" + profil.getLastLocation().getX() + " §7§ly: §r§3" + profil.getLastLocation().getY() + " §7§lz: §r§3" + profil.getLastLocation().getZ());
        lLore.add("§6" + profil.getDateString());
        lLore.add("§eGamemode " + profil.getGameMode());

        lLore.add("§a§lPermissions: ");

        String perm = "§7";
        for (String s : profil.getPermission()) {
            lLore.add(perm + " - " + s);
        }*/


        Meta.setLore(Collections.singletonList(profil.toString()));
        itemPaper.setItemMeta(Meta);
        return itemPaper;
    }
}
