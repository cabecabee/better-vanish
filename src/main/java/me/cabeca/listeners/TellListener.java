package me.cabeca.listeners;

import me.cabeca.commands.SoftVanishCommand;
import me.cabeca.commands.VanishCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class TellListener implements Listener {
    private final VanishCommand vanishCommand;
    private final SoftVanishCommand softVanishCommand;

    public TellListener(SoftVanishCommand softVanishCommand, VanishCommand vanishCommand){
        this.softVanishCommand = softVanishCommand;
        this.vanishCommand = vanishCommand;
    }

    @EventHandler
    public void onMessage(PlayerCommandPreprocessEvent event){
        String[] args = event.getMessage().trim().split("\\s+");

        if(args.length < 2) return;

        String command = args[0].toLowerCase();

        if(!command.endsWith("tell") && !command.endsWith("w") && !command.endsWith("whisper") && !command.endsWith("msg")) return;

        Player targetPlayer = Bukkit.getPlayer(args[1]);

        if(targetPlayer == null) return;

        // para op poder dar /tell pra outros vanished
        if(event.getPlayer().hasPermission("better-vanish.admin")) return;

        // caso nao seja op:
        boolean vanished = vanishCommand.getVanishedPlayers().contains(targetPlayer.getUniqueId());
        boolean softVanished = softVanishCommand.getSoftVanishedPlayers().contains(targetPlayer.getUniqueId());
        if(vanished || softVanished){
            event.setCancelled(true);
            event.getPlayer().sendMessage(Component.translatable("argument.entity.notfound.player").color(NamedTextColor.RED));
        }
    }
}
