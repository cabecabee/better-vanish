package me.cabeca.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VanishCommand implements CommandExecutor {

    // lista de players vanished
    private final Set<UUID> vanishedPlayers = new HashSet<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        Player targetPlayer = null;
        if(args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cApenas jogadores podem executar esse comando!");
                return true;
            }

            targetPlayer = (Player) sender;
        }
        else{
            targetPlayer = Bukkit.getPlayer(args[0]);
            if(targetPlayer == null){
                sender.sendMessage("§cO jogador " + args[0] + " não existe!");
                return true;
            }
        }

        boolean vanished = vanishedPlayers.contains(targetPlayer.getUniqueId());

        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.equals(targetPlayer)) continue;
            if(vanished) p.showPlayer(targetPlayer);
            else p.hidePlayer(targetPlayer);
        }
        if(vanished) vanishedPlayers.remove(targetPlayer.getUniqueId());
        else vanishedPlayers.add(targetPlayer.getUniqueId());

        return true;
    }

}
