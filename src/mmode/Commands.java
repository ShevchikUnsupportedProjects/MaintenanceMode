package mmode;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

public class Commands  implements CommandExecutor  {

	private Config config;
	
	public Commands(Config config)
	{
		this.config = config;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] args) {
		if (hasPerm(sender))
		{
			if (args.length == 1)
			{
				if (args[0].equalsIgnoreCase("on"))
				{
					config.mmodeEnabled = true;
					sender.sendMessage("Maintenance mode on");
					return true;
				}
				else if (args[0].equalsIgnoreCase("off"))
				{
					config.mmodeEnabled = false;
					sender.sendMessage("Maintenance mode off");
					return true;
				}
				else if (args[0].equalsIgnoreCase("reload"))
				{
					config.loadConfig();
					sender.sendMessage("Config reloaded");
					return true;
				}
			}
		}
		return false;
	}
	
	
	private boolean hasPerm(CommandSender sender)
	{
		boolean has = false;
		if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender)
		{
			has = true;
		}
		if (sender instanceof Player && sender.hasPermission("mmode.admin"))
		{
			has = true;
		}
		return has;
	}

}
