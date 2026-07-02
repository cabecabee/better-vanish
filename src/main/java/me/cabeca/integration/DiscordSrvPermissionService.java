package me.cabeca.integration;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DiscordSrvPermissionService {
    private final JavaPlugin plugin;
    private final Map<UUID, PermissionAttachment> attachments = new HashMap<>();

    public DiscordSrvPermissionService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean isAvailable() {
        return Bukkit.getPluginManager().isPluginEnabled("DiscordSRV");
    }

    public void applySilent(Player player) {
        if (!isAvailable()) return;

        PermissionAttachment a = attachments.get(player.getUniqueId());
        if (a == null) {
            a = player.addAttachment(plugin);
            attachments.put(player.getUniqueId(), a);
        }
        a.setPermission("discordsrv.silentjoin", true);
        a.setPermission("discordsrv.silentquit", true);
    }

    public void removeSilent(Player player) {
        PermissionAttachment a = attachments.remove(player.getUniqueId());
        if (a == null) return;

        try {
            player.removeAttachment(a);
        } catch (IllegalArgumentException ignored) {
            // sem crash; já foi limpo por outro fluxo
        }
    }
}