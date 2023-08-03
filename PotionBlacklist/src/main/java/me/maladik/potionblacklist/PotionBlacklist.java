package me.maladik.potionblacklist;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;

public final class PotionBlacklist extends JavaPlugin {

    private static PotionBlacklist instance;

    public static HashSet<Player> potionBlacklist;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        this.saveDefaultConfig(); // <-- Crea il config.yml
        if (this.getConfig().contains("blacklisted")) {
            FileManager.restoreData();
        }


        potionBlacklist = new HashSet<>();
        getCommand("potionblacklist").setExecutor(new AddToPotionBlacklist());

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(this, () -> {
            Player[] players = this.getServer().getOnlinePlayers().toArray(new Player[this.getServer().getOnlinePlayers().size()]);

            for (int i = 0; i < players.length; i++) {
                Player currentPlayer = players[i];
                for (int j = 0; j < PotionEffectType.values().length; j++) {
                    PotionEffectType currentPotionEffect = PotionEffectType.values()[j];
                    if (currentPlayer.hasPotionEffect(currentPotionEffect) && checkInList(currentPlayer)) {
                        currentPlayer.removePotionEffect(currentPotionEffect);
                    }
                }
            }

        }, 10L * 20L, 10L * 20L);


    }

    @Override
    public void onDisable() {

        if (!potionBlacklist.isEmpty()) FileManager.saveData();

        instance = null;

    }

    public static PotionBlacklist getInstance() {
        return instance;
    }

    public void addNewPlayerInBlacklist(Player p) {
        if (!checkInList(p)) {
            potionBlacklist.add(p);
        }
    }

    public void removePlayerInBlacklist(Player p) {
        if (checkInList(p)) {
            potionBlacklist.remove(p);
        }
    }

    public boolean checkInList(Player p) {
        if (potionBlacklist.contains(p)) return true;
        else return false;
    }


}
