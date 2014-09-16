package buildcraft.api.events;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class PipePlacedEvent extends Event {
	public EntityPlayer player;
	public String pipeType;

	public PipePlacedEvent(EntityPlayer player, String pipeType){
		this.player = player;
		this.pipeType = pipeType;
	}

}
