package com.connorlinfoot.blockcounter;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


public class Main extends JavaPlugin implements Listener {
    private static Plugin instance;

    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        Server server = getServer();
        ConsoleCommandSender console = server.getConsoleSender();

        console.sendMessage(ChatColor.GREEN + "=========== BlockCounter ===========");
        console.sendMessage(ChatColor.GREEN + "=========== VERSION: 1.0 ===========");
        console.sendMessage(ChatColor.GREEN + "======== BY CONNOR LINFOOT! ========");
    }

    public void onDisable() {
        getLogger().info(getDescription().getName() + " has been disabled!");
    }

    public static Plugin getInstance() {
        return instance;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        ItemStack item = e.getPlayer().getItemInHand();
        if( item == null || item.getType() == Material.AIR ) return;

        if( item.getType().toString().toLowerCase().contains("pickaxe") ){
            if( item.getItemMeta().getDisplayName().contains(ChatColor.GRAY + " [Blocks: ") ){
                Integer place = item.getItemMeta().getDisplayName().lastIndexOf(ChatColor.GRAY + " [Blocks: ");
                String itemName = item.getItemMeta().getDisplayName().substring(0, Math.min(item.getItemMeta().getDisplayName().length(), place));
                Integer place2 = item.getItemMeta().getDisplayName().lastIndexOf(" [Blocks: ");
                Integer currentCount = Integer.valueOf(item.getItemMeta().getDisplayName().substring(0, Math.min(item.getItemMeta().getDisplayName().length(), place2 + 10)));

                ItemMeta im = item.getItemMeta();
                im.setDisplayName(itemName + ChatColor.GRAY + " [Blocks: " + (currentCount + 1) + "]" );
                item.setItemMeta(im);
                e.getPlayer().setItemInHand(item);
            } else {
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(im.getDisplayName() + ChatColor.GRAY + " [Blocks: 1]" );
                item.setItemMeta(im);
                e.getPlayer().setItemInHand(item);
            }
        }
    }
}
