package me.maladik.potionblacklist;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddToPotionBlacklist implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("Questo comando puo' essere soltanto eseguito da un giocatore!");
            return false;
        }

        Player p = (Player) sender;
        if (p.hasPermission("potion.blacklist")) {

            if (args.length > 0) {
                String argCommand = args[0];
                String argName = args[1];
                if (argCommand.isEmpty() || argName.isEmpty()) {
                    return false;
                } else {
                    try {
                        Player target = Bukkit.getPlayer(argName);
                        if (target == null) throw new Exception();
                        if (!target.isOnline()) throw new Exception();


                        if (argCommand.equalsIgnoreCase("add")) {
                            PotionBlacklist.getInstance().removePlayerInBlacklist(target);
                            p.sendMessage("§a - Il giocatore " + target.getName() + " è stato inserito nella Blacklist delle pozioni");
                        } else if (argCommand.equalsIgnoreCase("remove")) {
                            PotionBlacklist.getInstance().addNewPlayerInBlacklist(target);
                            p.sendMessage("§a - Il giocatore " + target.getName() + " è stato rimosso dalla Blacklist delle pozioni");
                        } else if (argCommand.equalsIgnoreCase("check")) {
                            if (PotionBlacklist.potionBlacklist.contains(target)) {
                                p.sendMessage("§7 - Utente: §f" + target.getName() + "§7: §c§lBLACKLISTATO" );
                            } else {
                                p.sendMessage("§7 - Utente: §f" + target.getName() + "§7: §a§lLIBERO" );
                            }
                        } else {
                            p.sendMessage("§c&lERRORE &c- Comandi disponibili:\n §7/potionblacklist add <Nome>\n §7/potionblacklist remove <Nome>\n §7/potionblacklist check <Nome>");
                        }

                    } catch (Exception ex) {
                        p.sendMessage("&c - Il giocatore da te indicato non è online o non esiste!");
                    }
                }
            }


        } else {
            p.sendMessage("§cNon hai il permesso per effettuare questo comando!");
        }


        return false;
    }
}
