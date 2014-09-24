package eureka.api.events;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.eventhandler.Event;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockCheckEvent extends Event {
	public EntityPlayer player;
	public Block block;
	public int x, y, z;

	public BlockCheckEvent(EntityPlayer player, Block block, int x, int y, int z){
		this.player = player;
		this.block = block;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
