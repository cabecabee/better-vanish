package me.cabeca;

import me.cabeca.commands.FlyCommand;
import me.cabeca.commands.VanishCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterVanish extends JavaPlugin {

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        getLogger().info("Better Vanish ativado!");
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("fly").setExecutor(new FlyCommand());
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
