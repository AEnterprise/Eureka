package eureka.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import eureka.api.EurekaAPI;
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
		return "/eureka lock|unlock <player> <key>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length != 3)
			throw new WrongUsageException("Usage: " + getCommandUsage(sender));
		EntityPlayer player = null;
		for (EntityPlayer p : (ArrayList<EntityPlayer>) MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			if (p.getDisplayName().equals(args[1])) {
				player = p;
				break;
			}
		}
		if (player == null)
			throw new WrongUsageException("Invalid playername");
		if (EurekaAPI.API.keyRegistered(args[2])) {
			throw new WrongUsageException("Invalid key");
		}
		PlayerResearch research = PlayerResearch.get(player);
		if (args[0].equalsIgnoreCase("lock")) {
			research.overrideFinished(args[2], false);
			research.overrideProgress(args[2], 0);
		} else if (args[0].equalsIgnoreCase("unlock")) {
			research.overrideFinished(args[2], true);
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
