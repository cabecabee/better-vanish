package me.cabeca.listeners;

import me.cabeca.commands.VanishCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class JoinListener implements Listener {
    private final VanishCommand vanishCommand;
    private final JavaPlugin plugin;
    public JoinListener(JavaPlugin plugin, VanishCommand vanishCommand){
        this.plugin = plugin;
        this.vanishCommand = vanishCommand;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent joinEvent){
        Player joined = joinEvent.getPlayer();
        for(UUID uuid : vanishCommand.getVanishedPlayers()){
            Player vanished = Bukkit.getPlayer(uuid);
            if(vanished == null) continue;
            joined.hidePlayer(plugin, vanished);
        }

        if(vanishCommand.getVanishedPlayers().contains(joined.getUniqueId())){
            for(Player inServer : Bukkit.getOnlinePlayers()){
                if(inServer.equals(joined)) continue;
                inServer.hidePlayer(plugin, joined);
                joined.sendMessage("Você ainda está escondido!");
            }
        }

    }
}
