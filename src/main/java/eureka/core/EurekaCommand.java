package eureka.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import eureka.api.EurekaAPI;
import eureka.api.IEurekaInfo;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EurekaCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "eureka";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "type '/eureka help' for possible commands and syntax";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args == null || args.length == 0) {
			sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
			return;
		}
		if (args[0].equalsIgnoreCase("help")) {
			sender.addChatMessage(new ChatComponentText("Eureka Command help:"));
			sender.addChatMessage(new ChatComponentText("/eureka lock <playername> <key>  : locks the research identified by the key for the specified player"));
			sender.addChatMessage(new ChatComponentText("/eureka unlock <playername> <key>  : unlocks the research identified by the key for the specified player"));
			sender.addChatMessage(new ChatComponentText("/eureka keylist  : lists all registered keys and there localized titles"));
			return;
		}

		if (args[0].equalsIgnoreCase("keylist")) {
			for (IEurekaInfo info : EurekaAPI.API.getAllKeys()) {
				sender.addChatMessage(new ChatComponentText(info.getName() + ": " + TextGetter.getTitle(info.getName())));
			}
			return;
		}
		EntityPlayer player = null;
		for (EntityPlayer p : (ArrayList<EntityPlayer>) MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			if (p.getDisplayName().equals(args[1])) {
				player = p;
				break;
			}
		}
		if (player == null)
			throw new WrongUsageException("Invalid playername");
		if (!EurekaAPI.API.keyRegistered(args[2])) {
			throw new WrongUsageException("Invalid key");
		}
		PlayerResearch research = PlayerResearch.get(player);
		if (args[0].equalsIgnoreCase("lock")) {
			research.overrideFinished(args[2], false);
			research.overrideProgress(args[2], 0);
			sender.addChatMessage(new ChatComponentText("Research locked"));
		} else if (args[0].equalsIgnoreCase("unlock")) {
			research.overrideFinished(args[2], true);
			sender.addChatMessage(new ChatComponentText("Research unlocked"));
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		return super.getRequiredPermissionLevel();
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 2) {
			List<String> names = new ArrayList<String>();
			for (EntityPlayer player : (ArrayList<EntityPlayer>) MinecraftServer.getServer().getConfigurationManager().playerEntityList)
				names.add(player.getDisplayName());
			return names;
		}
		return null;
	}
}
