package me.cabeca.listeners;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import me.cabeca.commands.SoftVanishCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Iterator;
import java.util.UUID;

public class TabCompleteListener implements Listener {
    private final SoftVanishCommand softVanishCommand;
    public TabCompleteListener(SoftVanishCommand softVanishCommand){
        this.softVanishCommand = softVanishCommand;
    }
    @EventHandler
    public void onTabComplete(
            AsyncTabCompleteEvent event
    ){

        if(event.getSender().hasPermission("better-vanish.admin")){
            return;
        }

        Iterator<AsyncTabCompleteEvent.Completion> iterator = event.completions().iterator();

        while(iterator.hasNext()){

            AsyncTabCompleteEvent.Completion completion = iterator.next();

            Player player = Bukkit.getPlayer(completion.suggestion());

            if(player == null) continue;

            UUID uuid = player.getUniqueId();

            if(softVanishCommand.getSoftVanishedPlayers().contains(uuid)){
                iterator.remove();
            }
        }
    }
}
