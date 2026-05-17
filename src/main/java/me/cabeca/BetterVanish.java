package me.cabeca;

import me.cabeca.commands.FlyCommand;
import me.cabeca.commands.VanishCommand;
import me.cabeca.listeners.JoinLeftListener;
import me.cabeca.listeners.TellListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterVanish extends JavaPlugin {

    @Override
    public void onLoad() {
        getLogger().info("Better Vanish carregado!");
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("Better Vanish ativado!");
        VanishCommand vanishCommand = new VanishCommand(this);
        vanishCommand.loadVanishedPlayers();
        getCommand("vanish").setExecutor(vanishCommand);
        getCommand("fly").setExecutor(new FlyCommand());
        Bukkit.getPluginManager().registerEvents(new JoinLeftListener(this, vanishCommand), this);
        Bukkit.getPluginManager().registerEvents(new TellListener(vanishCommand), this);
    }


}
