package eureka.core;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;


import eureka.api.events.BlockPlacedEvent;

public class BlockDetector
{
	public static BlockDetector instance;
	public ArrayList<PlacePosition> placed = new ArrayList<PlacePosition>();


	public BlockDetector()
	{
		instance = this;

	}
	
	@SubscribeEvent
	public void onGameTick(ServerTickEvent evt)
	{
		if(evt.phase != Phase.END || placed.size() <= 0)
		{
			return;
		}
		ArrayList<PlacePosition> remove = new ArrayList<PlacePosition>();
		for(PlacePosition cu : placed)
		{
			if(cu.check())
			{
				remove.add(cu);
			}
		}
		if(remove.size() > 0)
		{
			placed.removeAll(remove);
		}
		
	}
	
	//Has to be lowest because we want to know if another mod says Nope you are not allowed to place Blocks.
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void onItemUse(PlayerInteractEvent evt)
	{
		if(evt.action == Action.LEFT_CLICK_BLOCK)
		{
			//Ignoring Hitting Blocks Simply
			return;
		}
		if(evt.isCanceled())
		{
			return;
		}
		if(evt.useItem == evt.useItem.DENY)
		{
			return;
		}
		
		
		//That is just another possebility of Placing Blocks. Just a backup.
		int[] pos = getAdjustedCoords(evt.world, evt.x, evt.y, evt.z, evt.face);
		
		placed.add(new PlacePosition(evt.world, evt.x, evt.y, evt.z, evt.entityPlayer));
		if(pos[0] != evt.x || pos[1] != evt.y || pos[2] != evt.z)
		{
			placed.add(new PlacePosition(evt.world, pos[0], pos[1], pos[2], evt.entityPlayer));
		}
		
	}
	
	private int[] getAdjustedCoords(World world, int x, int y, int z, int facing)
	{
		if(facing == -1)
		{
			return new int[]{x,y,z};
		}
		
		Block block = world.getBlock(x, y, z);
		if(block != null)
		{
			int xCoord = x;
			int yCoord = y;
			int zCoord = z;
			
			if (block == Blocks.snow_layer && (world.getBlockMetadata(xCoord, yCoord, zCoord) & 7) < 1)
			{
				
			}
			else if(block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(world, xCoord, yCoord, zCoord))
			{
	            if (facing == 0)
	            {
	                --yCoord;
	            }

	            if (facing == 1)
	            {
	                ++yCoord;
	            }

	            if (facing == 2)
	            {
	                --zCoord;
	            }

	            if (facing == 3)
	            {
	                ++zCoord;
	            }

	            if (facing == 4)
	            {
	                --xCoord;
	            }

	            if (facing == 5)
	            {
	                ++xCoord;
	            }
	            
	            return new int[]{xCoord, yCoord, zCoord};
			}
		}
		
		return new int[]{x,y,z};
	}
	
	
	public static class PlacePosition
	{
		//Data of May Placing.
		World world;
		int x;
		int y;
		int z;
		EntityPlayer placer;
		
		//Infos about the block before placed.
		Block block;
		int metadata;
	
		int tests = 10;
		
		public PlacePosition()
		{
			
		}
		
		public PlacePosition(World world, int x, int y, int z, EntityPlayer placer)
		{
			this.world = world;
			this.x = x;
			this.y = y;
			this.z = z;
			this.placer = placer;
			
			this.block = world.getBlock(x, y, z);
			this.metadata = world.getBlockMetadata(x, y, z);
		}
		
		public boolean check()
		{
			if(world.isRemote)
			{
				return false;
			}
			
			if(tests <= 0)
			{
				return true;
			}
			
			tests--;
			
			if(world.getBlock(x, y, z) != block)// || world.getBlockMetadata(x, y, z) != metadata)
			{
				FMLCommonHandler.instance().bus().post(new BlockPlacedEvent(world, x, y, z, placer, world.getBlock(x, y, z), world.getBlockMetadata(x, y, z)));
				return true;
			}
			return false;
		}
	}
}
