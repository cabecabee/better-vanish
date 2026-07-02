package me.cabeca.integration;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class DiscordSrvBridge {

    private static String avatarUrl(Player player) {
        String uuidNoDash = player.getUniqueId().toString().replace("-", "");
        return "https://minotar.net/helm/" + uuidNoDash + "/128";
    }

    public static void sendFakeLeave(Player player) {
        if (!Bukkit.getPluginManager().isPluginEnabled("DiscordSRV")) return;

        TextChannel channel = DiscordSRV.getPlugin().getMainTextChannel();
        if (channel == null) return;

        EmbedBuilder eb = new EmbedBuilder()
                .setColor(0xFF0000)
                .setAuthor(player.getName() + " left the server", null, avatarUrl(player));

        channel.sendMessageEmbeds(eb.build()).queue();
    }

    public static void sendFakeJoin(Player player) {
        if (!Bukkit.getPluginManager().isPluginEnabled("DiscordSRV")) return;

        TextChannel channel = DiscordSRV.getPlugin().getMainTextChannel();
        if (channel == null) return;

        EmbedBuilder eb = new EmbedBuilder()
                .setColor(0x00FF00)
                .setAuthor(player.getName() + " joined the server", null, avatarUrl(player));
        channel.sendMessageEmbeds(eb.build()).queue();
    }
}