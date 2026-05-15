package me.cabeca.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class VanishCommand implements CommandExecutor {

    // lista de players vanished
    private final Set<UUID> vanishedPlayers = new HashSet<>();

    private final JavaPlugin plugin;

    public VanishCommand(JavaPlugin plugin){
        this.plugin = plugin;
    }

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
            if(vanished) p.showPlayer(plugin, targetPlayer);
            else p.hidePlayer(plugin, targetPlayer);
        }
        if(vanished) vanishedPlayers.remove(targetPlayer.getUniqueId());
        else vanishedPlayers.add(targetPlayer.getUniqueId());
        saveVanishedPlayers();
        
        if(vanished){

            if(targetPlayer.equals(sender)){
                sender.sendMessage("§cVocê não está mais vanished!");
            }
            else{
                sender.sendMessage("§aO jogador voltou a aparecer!");
                targetPlayer.sendMessage("§cVocê não está mais vanished!");
            }

        }
        else{

            if(targetPlayer.equals(sender)){
                sender.sendMessage("§aVocê entrou em vanish!");
            }
            else{
                sender.sendMessage("§aO jogador entrou em vanish!");
                targetPlayer.sendMessage("§aVocê entrou em vanish!");
            }
        }

        return true;
    }

    private void saveVanishedPlayers(){
        List<String> uuids = vanishedPlayers.stream().map(UUID::toString).toList();
        plugin.getConfig().set("vanished-players", uuids);
        plugin.saveConfig();
    }

    public void loadVanishedPlayers(){
        List<String> uuids = plugin.getConfig().getStringList("vanished-players");
        for(String uuidString : uuids){
            vanishedPlayers.add(UUID.fromString(uuidString));
        }
    }

    public Set<UUID> getVanishedPlayers(){
        return vanishedPlayers;
    }
}
