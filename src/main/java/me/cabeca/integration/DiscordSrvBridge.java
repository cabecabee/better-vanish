package me.cabeca.integration;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class DiscordSrvBridge {

    public static void sendFakeLeave(Player player) {
        if (!Bukkit.getPluginManager().isPluginEnabled("DiscordSRV")) return;

        TextChannel channel = DiscordSRV.getPlugin().getMainTextChannel();
        if (channel == null) return;

        DiscordUtil.sendMessage(channel, "⬅️ **" + player.getName() + "** saiu do servidor.");
    }

    public static void sendFakeJoin(Player player) {
        if (!Bukkit.getPluginManager().isPluginEnabled("DiscordSRV")) return;

        TextChannel channel = DiscordSRV.getPlugin().getMainTextChannel();
        if (channel == null) return;

        DiscordUtil.sendMessage(channel, "➡️ **" + player.getName() + "** entrou no servidor.");
    }
}
