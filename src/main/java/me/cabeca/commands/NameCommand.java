package me.cabeca.commands;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NameCommand implements CommandExecutor {

    public void changeName(Player player, String name){
        PlayerProfile profile = Bukkit.createProfileExact(player.getUniqueId(), name);
        profile.setTextures(player.getPlayerProfile().getTextures());
        player.setPlayerProfile(profile);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        Player targetPlayer;
        String name;

        if(args.length < 1){
            sender.sendMessage("§cDê o nome que será usado e, caso o comando seja usado em um outro jogador, também especifique o nome deste.");
            return true;
        }

        if(args.length == 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cApenas jogadores podem executar esse comando!");
                return true;
            }

            targetPlayer = (Player) sender;
        }

        else {
            targetPlayer = Bukkit.getPlayer(args[1]);
            if (targetPlayer == null) {
                sender.sendMessage("§cO jogador " + args[1] + " não existe!");
                return true;
            }
        }
        name = args[0];
        if (name.length() > 16 || name.length() < 3) {
            sender.sendMessage("§cO nome deve ter no mínimo 3 caracteres e no máximo 16!");
            return true;
        }
        changeName(targetPlayer, name);
        return true;
    }
}