package eureka.api.event;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.Event;

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
