package me.cabeca.listeners;

import me.cabeca.commands.VanishCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class JoinLeftListener implements Listener {

    private final VanishCommand vanishCommand;
    private final JavaPlugin plugin;
    public JoinLeftListener(JavaPlugin plugin, VanishCommand vanishCommand){
        this.plugin = plugin;
        this.vanishCommand = vanishCommand;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent joinEvent){

        Player joined = joinEvent.getPlayer();

        for(UUID uuid : vanishCommand.getVanishedPlayers()){

            Player vanished = Bukkit.getPlayer(uuid);

            if(vanished == null) continue;

            if(!joined.hasPermission("better-vanish.admin")){
                joined.hidePlayer(plugin, vanished);
            }
            else if(!vanished.equals(joined)){
                joined.sendMessage("§a" + vanished.getName() + " está vanished."
                );
            }
        }

        if(vanishCommand.getVanishedPlayers().contains(joined.getUniqueId())){

            joinEvent.joinMessage(null);

            for(Player inServer : Bukkit.getOnlinePlayers()){

                if(inServer.equals(joined)) continue;

                if(!inServer.hasPermission("better-vanish.admin")){
                    inServer.hidePlayer(plugin, joined);
                }
                else{

                    inServer.sendMessage("§a" + joined.getName() + " entrou no servidor enquanto vanished."
                    );
                }
            }

            joined.sendMessage("§aVocê ainda está escondido!");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent quitEvent){

        Player quit = quitEvent.getPlayer();

        if(vanishCommand.getVanishedPlayers().contains(quit.getUniqueId())){

            quitEvent.quitMessage(null);

            for(Player p : Bukkit.getOnlinePlayers()){

                if(!p.hasPermission("better-vanish.admin")) continue;

                if(p.equals(quit)) continue;

                p.sendMessage(
                        "§c" + quit.getName() + " saiu do servidor vanished."
                );
            }
        }
    }

}
