package be.shark_zekrom;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Gui implements Listener {

    public static HashMap<Player, String> playerEditing = new HashMap<>();
    public static HashMap<String, String> moneyPlayer = new HashMap<>();
    public static ArrayList<Player> playerEditingCustomMoney = new ArrayList<>();

    public static void gui(Player sender ,String target) throws SQLException {

        String money = Database.getMoney(target);
        if (money != null) {

            playerEditing.put(sender, target);
            moneyPlayer.put(target, money);

            Inventory inventory = Bukkit.createInventory(null, 18, Main.getInstance().getConfig().getString("GuiCreditEditing").replaceAll("%player%", target));
            sender.openInventory(inventory);
            update(sender, inventory);
        } else {
            sender.sendMessage(Main.getInstance().getConfig().getString("UnregisteredPlayer"));
        }
    }



    public static void update(Player player, Inventory inventory) {

        ItemStack confirm = new ItemStack(Material.NETHER_STAR);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName(Main.getInstance().getConfig().getString("GuiCreditEditing"));
        confirmMeta.setLore(new ArrayList<>(Collections.singletonList("§a" + moneyPlayer.get(playerEditing.get(player)))));
        confirm.setItemMeta(confirmMeta);
        inventory.setItem(4, confirm);

        ItemStack custom = new ItemStack(Material.SIGN);
        ItemMeta customMeta = custom.getItemMeta();
        customMeta.setDisplayName(Main.getInstance().getConfig().getString("CustomAmount"));
        custom.setItemMeta(customMeta);
        inventory.setItem(13, custom);

        ItemStack plus100 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta plus100Meta = plus100.getItemMeta();
        plus100Meta.setDisplayName("§a+ 100");
        plus100.setItemMeta(plus100Meta);
        inventory.setItem(10, plus100);

        ItemStack plus10 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta plus10Meta = plus10.getItemMeta();
        plus10Meta.setDisplayName("§a+ 10");
        plus10.setItemMeta(plus10Meta);
        inventory.setItem(11, plus10);

        ItemStack plus1 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta plus1Meta = plus1.getItemMeta();
        plus1Meta.setDisplayName("§a+ 1");
        plus1.setItemMeta(plus1Meta);
        inventory.setItem(12, plus1);

        ItemStack moins1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta moins1Meta = moins1.getItemMeta();
        moins1Meta.setDisplayName("§c- 1");
        moins1.setItemMeta(moins1Meta);
        inventory.setItem(14, moins1);

        ItemStack moins10 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta moins10Meta = moins10.getItemMeta();
        moins10Meta.setDisplayName("§c- 10");
        moins10.setItemMeta(moins10Meta);
        inventory.setItem(15, moins10);

        ItemStack moins100 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta moins100Meta = moins1.getItemMeta();
        moins100Meta.setDisplayName("§c- 100");
        moins100.setItemMeta(moins100Meta);
        inventory.setItem(16, moins100);


    }



    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) throws SQLException {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        if (playerEditing.get(player) != null) {
            if (event.getView().getTitle().equalsIgnoreCase(Main.getInstance().getConfig().getString("GuiCreditEditing").replaceAll("%player%", playerEditing.get(player)))) {
                event.setCancelled(true);

                if (slot == 13) {
                    customAmount(player);
                } else {
                    switch (slot) {
                        case 10:
                            Database.addCredit(player ,playerEditing.get(player), 100.0);
                            break;
                        case 11:
                            Database.addCredit(player ,playerEditing.get(player), 10.0);
                            break;
                        case 12:
                            Database.addCredit(player ,playerEditing.get(player), 1.0);
                            break;
                        case 14:
                            Database.removeCredit(player ,playerEditing.get(player), 1.0);
                            break;
                        case 15:
                            Database.removeCredit(player ,playerEditing.get(player), 10.0);
                            break;
                        case 16:
                            Database.removeCredit(player ,playerEditing.get(player), 100.0);
                            break;
                    }
                    gui(player, playerEditing.get(player));
                }
            }
        }
    }

    public void customAmount(Player player)  {
        player.sendMessage(Main.getInstance().getConfig().getString("EnterCustomAmount"));
        player.closeInventory();
        playerEditingCustomMoney.add(player);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (playerEditingCustomMoney.contains(player)) {
                    playerEditingCustomMoney.remove(player);
                    player.sendMessage(Main.getInstance().getConfig().getString("TooLong"));
                }
            }
        }.runTaskLater(Main.getInstance(), 100L);
    }

    @EventHandler
    private void onChat(AsyncPlayerChatEvent event) throws SQLException {
        Player player = event.getPlayer();

        if (playerEditingCustomMoney.contains(player)) {
            playerEditingCustomMoney.remove(player);
            Database.setCredit(player, playerEditing.get(player), Double.valueOf(event.getMessage()));
        }

    }
}
