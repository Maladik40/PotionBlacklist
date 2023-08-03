package me.maladik.potionblacklist;

import org.bukkit.entity.Player;

import java.util.List;

public class FileManager {

    public static void saveData() {
        for (Player p : PotionBlacklist.potionBlacklist) {
            PotionBlacklist.getInstance().getConfig().set("blacklisted.", p.getName());
        }
        PotionBlacklist.getInstance().saveConfig();
    }

    public static void restoreData() {
        PotionBlacklist.getInstance().getConfig().getConfigurationSection("blacklisted").getKeys(false).forEach(key -> {
            Player[] content = ((List<Player>) PotionBlacklist.getInstance().getConfig().get("blacklisted." + key)).toArray(new Player[0]);
                PotionBlacklist.potionBlacklist.addAll(List.of(content));
        });
    }
}
