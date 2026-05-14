package me.cabeca.commands;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        Player targetPlayer = null;
        if(args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cApenas jogadores podem executar esse comando em si mesmos! Tente especificar o jogador que quer.");
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

        if(targetPlayer.getAllowFlight()){
            targetPlayer.setFlying(false);
            targetPlayer.setAllowFlight(false);
            targetPlayer.sendMessage("§cVoo desativado!");
        }
        else{
        targetPlayer.setAllowFlight(true);
        targetPlayer.setFlying(true);
        targetPlayer.sendMessage("§aVoo ativado!");
        }
        return true;
    }
}
