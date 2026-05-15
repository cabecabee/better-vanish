package me.cabeca;

import me.cabeca.commands.FlyCommand;
import me.cabeca.commands.VanishCommand;
import me.cabeca.listeners.JoinListener;
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
        Bukkit.getPluginManager().registerEvents(new JoinListener(this, vanishCommand), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
