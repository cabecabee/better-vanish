package me.cabeca;

import me.cabeca.commands.FlyCommand;
import me.cabeca.commands.SoftVanishCommand;
import me.cabeca.commands.VanishCommand;
import me.cabeca.listeners.JoinLeftListener;
import me.cabeca.listeners.SoftVanishListener;
import me.cabeca.listeners.TabCompleteListener;
import me.cabeca.listeners.TellListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class BetterVanish extends JavaPlugin {

    @Override
    public void onLoad() {
        getLogger().info("Better Vanish carregado!");
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("Better Vanish ativado!");
        SoftVanishCommand softVanishCommand = new SoftVanishCommand(this);
        softVanishCommand.loadSoftVanishedPlayers();
        Objects.requireNonNull(getCommand("softvanish")).setExecutor(softVanishCommand);
        VanishCommand vanishCommand = new VanishCommand(this);
        vanishCommand.loadVanishedPlayers();
        Objects.requireNonNull(getCommand("vanish")).setExecutor(vanishCommand);
        Objects.requireNonNull(getCommand("fly")).setExecutor(new FlyCommand());
        Bukkit.getPluginManager().registerEvents(new JoinLeftListener(this, vanishCommand), this);
        Bukkit.getPluginManager().registerEvents(new TellListener(softVanishCommand, vanishCommand), this);
        Bukkit.getPluginManager().registerEvents(new SoftVanishListener(softVanishCommand), this);
        Bukkit.getPluginManager().registerEvents(new TabCompleteListener(softVanishCommand), this);

        for(Player player : Bukkit.getOnlinePlayers()){

            if(softVanishCommand
                    .getSoftVanishedPlayers()
                    .contains(player.getUniqueId())){

                softVanishCommand.applySoftVanish(player);
            }
        }
        
    }


}
