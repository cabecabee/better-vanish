package me.cabeca.commands;

import me.cabeca.integration.DiscordSrvBridge;
import me.cabeca.integration.DiscordSrvPermissionService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SoftVanishCommand implements CommandExecutor {

    private final Set<UUID> softVanishedPlayers = new HashSet<>();

    private final JavaPlugin plugin;

    private final Team team;

    private final DiscordSrvPermissionService discordPermService;

    private boolean hasDiscordSRV() {
        return Bukkit.getPluginManager().isPluginEnabled("DiscordSRV");
    }

    public SoftVanishCommand(JavaPlugin plugin, DiscordSrvPermissionService discordPermService){
        this.plugin = plugin;
        this.discordPermService = discordPermService;
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Team existing = board.getTeam("hidden_names");
        if(existing != null){
            this.team = existing;
        }
        else{
            this.team = board.registerNewTeam("hidden_names");
        }
        this.team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        Player targetPlayer;

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

        boolean softVanished = softVanishedPlayers.contains(targetPlayer.getUniqueId());

        if(softVanished){

            if(targetPlayer.equals(sender)){
                sender.sendMessage("§cVocê não está mais soft vanished!");
            }
            else{
                sender.sendMessage("§aO jogador voltou a aparecer!");
                targetPlayer.sendMessage("§cVocê não está mais soft vanished!");
            }

        }
        else{

            if(targetPlayer.equals(sender)){
                sender.sendMessage("§aVocê entrou em soft vanish!");
            }
            else{
                sender.sendMessage("§aO jogador entrou em soft vanish!");
                targetPlayer.sendMessage("§aVocê entrou em soft vanish!");
            }
        }

        if(softVanished){

            removeSoftVanish(targetPlayer);

            softVanishedPlayers.remove(
                    targetPlayer.getUniqueId()
            );
            if(hasDiscordSRV()) {
                discordPermService.removeSilent(targetPlayer);
                DiscordSrvBridge.sendFakeJoin(targetPlayer);
            }
            saveSoftVanishedPlayers();
        }
        else{

            applySoftVanish(targetPlayer);

            softVanishedPlayers.add(targetPlayer.getUniqueId());
            if(hasDiscordSRV()){
                discordPermService.applySilent(targetPlayer);
                DiscordSrvBridge.sendFakeLeave(targetPlayer);
            }
            saveSoftVanishedPlayers();
        }

        if(softVanished){

            for(Player p : Bukkit.getOnlinePlayers()) {
                if(p.hasPermission("better-vanish.admin")) continue;
                p.sendMessage(
                        Component.translatable(
                                "multiplayer.player.joined",
                                Component.text(targetPlayer.getName())
                        ).color(NamedTextColor.YELLOW)
                );

            }

        }
        else{

            for(Player p : Bukkit.getOnlinePlayers()) {
                if(p.hasPermission("better-vanish.admin")) continue;
                p.sendMessage(
                        Component.translatable(
                                "multiplayer.player.left",
                                Component.text(targetPlayer.getName())
                        ).color(NamedTextColor.YELLOW)
                );

            }
        }

        if(softVanished){

            for(Player p : Bukkit.getOnlinePlayers()){

                if(!p.hasPermission("better-vanish.admin")) continue;
                if(p.equals(targetPlayer)) continue;

                p.sendMessage("§c" + targetPlayer.getName() + " saiu do soft vanish.");
            }

        }
        else{

            for(Player p : Bukkit.getOnlinePlayers()){

                if(!p.hasPermission("better-vanish.admin")) continue;
                if(p.equals(targetPlayer)) continue;

                p.sendMessage("§a" + targetPlayer.getName() + " entrou em soft vanish.");
            }
        }

        return true;
    }

    public Set<UUID> getSoftVanishedPlayers(){
        return softVanishedPlayers;
    }

    private void saveSoftVanishedPlayers(){

        List<String> uuids = softVanishedPlayers.stream().map(UUID::toString).toList();

        plugin.getConfig().set("soft-vanished-players", uuids);

        plugin.saveConfig();
    }

    public void loadSoftVanishedPlayers(){

        List<String> uuids = plugin.getConfig().getStringList("soft-vanished-players");

        for(String uuidString : uuids){
            softVanishedPlayers.add(UUID.fromString(uuidString));
        }
    }

    public void applySoftVanish(Player player){

        team.addEntry(player.getName());

        player.playerListName(Component.empty());
    }

    public void removeSoftVanish(Player player){

        team.removeEntry(player.getName());

        player.playerListName(Component.text(player.getName()));
    }
}
