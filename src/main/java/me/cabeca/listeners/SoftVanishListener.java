package me.cabeca.listeners;

import me.cabeca.commands.SoftVanishCommand;
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
            event.joinMessage(null);
            softVanishCommand.applySoftVanish(player);
            player.sendMessage("§aVocê ainda está escondido!");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

        Player player = event.getPlayer();

        if(softVanishCommand
                .getSoftVanishedPlayers()
                .contains(player.getUniqueId())){

            event.quitMessage(null);
        }
    }
}