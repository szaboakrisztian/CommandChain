package WEChain;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.command.CommandEvent;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.extension.platform.PlatformCommandManager;
import com.sk89q.worldedit.extension.platform.PlatformManager;

public class WEPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("WorldEdit Test Plugin Enabled");

        // Register command
        this.getCommand("testwe").setExecutor(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("WorldEdit Test Plugin Disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        Player player = (Player) sender;
        String allCommands = String.join(";", args); // Example: /testwe cmd1;cmd2

        String[] commands = allCommands.split(";");

        // Get the WorldEdit platform manager
        PlatformManager platformManager = WorldEdit.getInstance().getPlatformManager();
        PlatformCommandManager commandManager = platformManager.getPlatformCommandManager();
        Actor actor = BukkitAdapter.adapt(player);

        // Execute each command
        for (String cmdStr : commands) {
            try {
                CommandEvent commandEvent = new CommandEvent(actor, cmdStr.trim());
                commandManager.handleCommand(commandEvent);
            } catch (Exception e) {
                player.sendMessage("Error executing command: " + cmdStr.trim());
                e.printStackTrace();
            }
        }

        return true;
    }
}