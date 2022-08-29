package be.shark_zekrom;

import be.shark_zekrom.Commands;
import be.shark_zekrom.Database;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Gui implements Listener {



    public static void gui(Player sender ,Player target) throws SQLException {

        String money = Database.getMoney(target);
        if (!money.equals("§c§lJoueur non inscrit")) {

            listPlayer.put(sender, target);
            moneyPlayer.put(target, money);

            Inventory inventory = Bukkit.createInventory(null, 45, "update point " + target.getName());
            sender.openInventory(inventory);
            update(sender, inventory);
        }
    }







    public static void update(Player player, Inventory inventory) {

        ItemStack confirm = new ItemStack(Material.NETHER_STAR);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName("§7Money");
        confirmMeta.setLore(new ArrayList<>(Collections.singletonList(moneyPlayer.get(listPlayer.get(player)))));
        confirm.setItemMeta(confirmMeta);
        inventory.setItem(4, confirm);

        ItemStack plus100 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta plus100Meta = plus100.getItemMeta();
        plus100Meta.setDisplayName("§7+ 100");
        plus100.setItemMeta(plus100Meta);
        inventory.setItem(19, plus100);

        ItemStack plus10 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta plus10Meta = plus10.getItemMeta();
        plus10Meta.setDisplayName("§7+ 10");
        plus10.setItemMeta(plus10Meta);
        inventory.setItem(20, plus10);



        ItemStack plus1 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta plus1Meta = plus1.getItemMeta();
        plus1Meta.setDisplayName("§7+ 1");
        plus1.setItemMeta(plus1Meta);
        inventory.setItem(21, plus1);

        ItemStack moins1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta moins1Meta = moins1.getItemMeta();
        moins1Meta.setDisplayName("§7- 1");
        moins1.setItemMeta(moins1Meta);
        inventory.setItem(23, moins1);

        ItemStack moins10 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta moins10Meta = moins10.getItemMeta();
        moins10Meta.setDisplayName("§7- 10");
        moins10.setItemMeta(moins10Meta);
        inventory.setItem(24, moins10);

        ItemStack moins100 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta moins100Meta = moins1.getItemMeta();
        moins100Meta.setDisplayName("§7- 100");
        moins100.setItemMeta(moins100Meta);
        inventory.setItem(25, moins100);


    }



    public static HashMap<Player, Player> listPlayer = new HashMap<>();
    public static HashMap<Player, String> moneyPlayer = new HashMap<>();

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) throws SQLException, IOException {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        if (listPlayer.get(player) != null) {
            if (event.getView().getTitle().equalsIgnoreCase("update point " + listPlayer.get(player).getName())) {
                event.setCancelled(true);

                if (slot == 19) {
                    Commands.addMoney(player, listPlayer.get(player), 100.0);
                } else if (slot == 20) {
                    Commands.addMoney(player, listPlayer.get(player), 10.0);
                } else if (slot == 21) {
                    Commands.addMoney(player, listPlayer.get(player), 1.0);
                } else if (slot == 23) {
                    Commands.removeMoney(player, listPlayer.get(player), 1.0);

                } else if (slot == 24) {
                    Commands.removeMoney(player, listPlayer.get(player), 10.0);

                } else if (slot == 25) {
                    Commands.removeMoney(player, listPlayer.get(player), 100.0);

                }
                gui(player, listPlayer.get(player));
            }
        }
    }
}