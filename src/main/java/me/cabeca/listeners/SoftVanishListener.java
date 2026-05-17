package me.cabeca.listeners;

import me.cabeca.commands.SoftVanishCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SoftVanishListener implements Listener {

    private final SoftVanishCommand softVanishCommand;

    public SoftVanishListener(
            SoftVanishCommand softVanishCommand
    ){
        this.softVanishCommand = softVanishCommand;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        if(softVanishCommand.getSoftVanishedPlayers().contains(player.getUniqueId())){

            softVanishCommand.applySoftVanish(player);

            player.sendMessage("§aVocê ainda está em soft vanish!");

            event.joinMessage(null);

            for(Player p : Bukkit.getOnlinePlayers()){

                if(!p.hasPermission("better-vanish.admin")) continue;

                if(p.equals(player)) continue;

                p.sendMessage("§a" + player.getName() + " entrou no servidor em soft vanish.");
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

        Player player = event.getPlayer();

        if(softVanishCommand.getSoftVanishedPlayers().contains(player.getUniqueId())){

            event.quitMessage(null);

            for(Player p : Bukkit.getOnlinePlayers()){

                if(!p.hasPermission("better-vanish.admin")) continue;

                if(p.equals(player)) continue;

                p.sendMessage("§c" + player.getName() + " saiu do servidor em soft vanish.");
            }
        }
    }
}