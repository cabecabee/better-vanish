package me.cabeca.listeners;

import me.cabeca.commands.SoftVanishCommand;
import me.cabeca.commands.VanishCommand;
import me.cabeca.integration.DiscordSrvPermissionService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SoftVanishListener implements Listener {

    private final SoftVanishCommand softVanishCommand;
    private final DiscordSrvPermissionService discordPermService;

    private boolean hasDiscordSRV() {
        return Bukkit.getPluginManager().isPluginEnabled("DiscordSRV");
    }

    public SoftVanishListener(
            SoftVanishCommand softVanishCommand, DiscordSrvPermissionService discordPermService
    ){
        this.softVanishCommand = softVanishCommand;
        this.discordPermService = discordPermService;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        if(softVanishCommand.getSoftVanishedPlayers().contains(player.getUniqueId())){

            softVanishCommand.applySoftVanish(player);

            player.sendMessage("§aVocê ainda está em soft vanish!");
            if(hasDiscordSRV()) discordPermService.applySilent(player);
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

            if(hasDiscordSRV()) discordPermService.removeSilent(player);
            event.quitMessage(null);

            for(Player p : Bukkit.getOnlinePlayers()){

                if(!p.hasPermission("better-vanish.admin")) continue;

                if(p.equals(player)) continue;

                p.sendMessage("§c" + player.getName() + " saiu do servidor em soft vanish.");
            }
        }
    }
}