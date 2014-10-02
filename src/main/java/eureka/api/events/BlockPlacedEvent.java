package eureka.api.events;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.Event;

/*Copyright (c) 2014, AEnterprise
* http://buildcraftadditions.wordpress.com/
* Eureka is distributed under the terms of LGPLv3
* Please check the contents of the license located in
* http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
*/

public class BlockPlacedEvent extends Event
{
	public EntityPlayer player;
	public Block block;
	public World world;
	public int metadata;
	public int xCoord;
	public int yCoord;
	public int zCoord;
	
	public BlockPlacedEvent(World world, int x, int y, int z, EntityPlayer placer, Block block, int meta)
	{
		super();
		this.world = world;
		xCoord = x;
		yCoord = y;
		zCoord = z;
		player = placer;
		this.block = block;
		metadata = meta;
	}

	public String print()
	{
		return world.provider.getDimensionName()+" x:"+xCoord+" y:"+yCoord+" z:"+zCoord+" Block:"+block;
	}
	
}
