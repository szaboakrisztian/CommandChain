package CommandChain;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Chain extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("CommandChain enabled");

        // Register command
        this.getCommand("chain").setExecutor(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("CommandChain disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("Please provide some commands to execute.");
            return false;
        }

        // Combine all arguments into a single string (commands separated by semicolons)
        String allCommands = String.join(" ", args);

        // Split the commands by semicolon
        String[] commands = allCommands.split(";");

        // Execute each command
        for (String cmd : commands) {
            String trimmedCmd = cmd.trim(); // Trim whitespace around commands
            if(trimmedCmd.charAt(0) == '/'){
                trimmedCmd = trimmedCmd.substring(1);
            }
            if (trimmedCmd.isEmpty()) {
                continue; // Skip empty commands
            }

            try {
                // Debug: Log the command we're executing
                getLogger().info("Executing command: " + trimmedCmd);

                // Perform the command directly
                if (!(sender instanceof Player)) {
                    // Execute for console
                    getServer().dispatchCommand(sender, trimmedCmd);
                } else {
                    // Execute for players
                    ((Player) sender).performCommand(trimmedCmd);
                }
            } catch (Exception e) {
                
                sender.sendMessage("Error executing command: " + cmd.trim());
                e.printStackTrace();
            }
        }

        return true;
    }
}
