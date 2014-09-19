package eureka.core;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import buildcraft.api.events.BlockInteractionEvent;
import buildcraft.api.events.BlockPlacedDownEvent;
import buildcraft.api.events.PipePlacedEvent;
import buildcraft.api.events.RobotPlacementEvent;


import eureka.Eureka;
import eureka.api.EurekaKnowledge;
import eureka.api.interfaces.IEurekaBlock;
import eureka.api.interfaces.IEurekaItem;
import eureka.utils.BCUtils;
import eureka.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftAdditions.wordpress.com/
 * Eureka is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftAdditions.wordpress.com/wiki/licensing-stuff/
 */
public class EventHandler {

	public static class FML{

		@SubscribeEvent
		public void playerLogin (PlayerEvent.PlayerLoggedInEvent event) {
			//initialize player knowledge if needed
			EurekaKnowledge.init(event.player);

			//give engineering diary
			if (!event.player.getEntityData().hasKey("bookRecieved")) {
				for (int slot = 0; slot < event.player.inventory.getSizeInventory(); slot++) {
					if (event.player.inventory.getStackInSlot(slot) == null) {
						event.player.inventory.setInventorySlotContents(slot, new ItemStack(Eureka.engineeringDiary));
						event.player.getEntityData().setBoolean("bookRecieved", true);
						return;
					}

				}
			}
		}

		@SubscribeEvent
		public void BuildcraftBlockInteraction(BlockInteractionEvent event){
			String key = BCUtils.getBCKey(event.block.getUnlocalizedName());
			event.setCanceled(!EurekaKnowledge.isFinished(event.player, key));
			event.player.addChatComponentMessage(new ChatComponentText("Buildcraft block interaction detected: " + key));
		}

		@SubscribeEvent
		public void BuildcraftPipePlaced(PipePlacedEvent event){
			String key = BCUtils.getBCKey(event.pipeType);
			event.player.addChatComponentMessage(new ChatComponentText("Buildcraft pipe placed: " + key));
			if (!EurekaKnowledge.isFinished(event.player, key)){
				event.player.worldObj.setBlockToAir( event.x, event.y, event.z);
				for (ItemStack stack: BCUtils.getBCDrops(key)) {
					if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_WOOD && !EurekaKnowledge.isFinished(event.player, "woodItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("woodItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_COBBLESTONE && !EurekaKnowledge.isFinished(event.player, "cobblestoneItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("cobblestoneItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_STONE && !EurekaKnowledge.isFinished(event.player, "stoneItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("stoneItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_QUARTZ && !EurekaKnowledge.isFinished(event.player, "quartzItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("quartzItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_SANDSTONE && !EurekaKnowledge.isFinished(event.player, "sandstoneItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("sandstoneItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_GOLD && !EurekaKnowledge.isFinished(event.player, "goldItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("goldItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_IRON && !EurekaKnowledge.isFinished(event.player, "ironItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("ironItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_VOID && !EurekaKnowledge.isFinished(event.player, "voidItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("voidItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_OBSIDIAN && !EurekaKnowledge.isFinished(event.player, "obsidianItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("obsidianItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_EMERALD && !EurekaKnowledge.isFinished(event.player, "emeraldItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("emeraldItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_DIAMOND && !EurekaKnowledge.isFinished(event.player, "diamondItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("diamondItems"));
					else
						Utils.dropItemstack(event.player.worldObj, event.x, event.y, event.z, stack.copy());
				}
			}
		}

		@SubscribeEvent
		public void BuildcraftBlockPlaced(BlockPlacedDownEvent event){
			if (event.player.getEntityWorld().isRemote || event.block.getUnlocalizedName().equals("tile.air"))
				return;
			String key = BCUtils.getBCKey(event.block.getUnlocalizedName());
			if (event.block.getUnlocalizedName().equals("tile.null")){
				int meta = event.player.worldObj.getBlockMetadata(event.x, event.y, event.z);
					if (meta == 0)
						key = "assemblyTable";
				else if (meta == 1)
						key = "advancedCraftingTable";
				else
						key = "integrationTable";
			}
			if (event.block.getUnlocalizedName().equals("tile.engineBlock")){
				int meta = event.player.worldObj.getBlockMetadata(event.x, event.y, event.z);
				if (meta == 0)
					key = "redstoneEngine";
				else if (meta == 1)
					key = "stilringEngine";
				else
					key = "combustionEngine";
			}
			if (!EurekaKnowledge.isFinished(event.player, key)){
				event.player.worldObj.setBlockToAir(event.x, event.y, event.z);
				for (ItemStack stack: BCUtils.getBCDrops(key)) {
					if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_WOOD && !EurekaKnowledge.isFinished(event.player, "woodItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("woodItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_COBBLESTONE && !EurekaKnowledge.isFinished(event.player, "cobblestoneItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("cobblestoneItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_STONE && !EurekaKnowledge.isFinished(event.player, "stoneItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("stoneItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_QUARTZ && !EurekaKnowledge.isFinished(event.player, "quartzItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("quartzItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_SANDSTONE && !EurekaKnowledge.isFinished(event.player, "sandstoneItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("sandstoneItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_GOLD && !EurekaKnowledge.isFinished(event.player, "goldItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("goldItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_IRON && !EurekaKnowledge.isFinished(event.player, "ironItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("ironItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_VOID && !EurekaKnowledge.isFinished(event.player, "voidItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("voidItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_OBSIDIAN && !EurekaKnowledge.isFinished(event.player, "obsidianItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("obsidianItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_EMERALD && !EurekaKnowledge.isFinished(event.player, "emeraldItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("emeraldItems"));
					else if (stack.getItem() == BCUtils.BCItems.PIPE_ITEMS_DIAMOND && !EurekaKnowledge.isFinished(event.player, "diamondItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, BCUtils.getBCDrops("diamondItems"));
					else
						Utils.dropItemstack(event.player.worldObj, event.x, event.y, event.z, stack.copy());
				}
			}
			event.player.addChatComponentMessage(new ChatComponentText("Buildcraft block placed: " +  key));
		}

		@SubscribeEvent
		public void BuildcraftRobotPlaced(RobotPlacementEvent event){
			event.player.addChatComponentMessage(new ChatComponentText("Buildcraft robot placed: " + BCUtils.getBCKey(event.robotProgram)));

		}

		@SubscribeEvent
		public void onCrafted(PlayerEvent.ItemCraftedEvent event){
			EurekaKnowledge.makeProgress(event.player, "autoWorkbench");
			if (event.crafting.getItem() == new ItemStack(Blocks.chest).getItem())
				EurekaKnowledge.makeProgress(event.player, "tank");
		}


		private void dropItemsFromList(World world, int x, int y, int z, ArrayList<ItemStack> stacks){
			for (ItemStack stack: stacks)
				Utils.dropItemstack(world, x, y, z, stack.copy());
		}
	}


	public static class Forge {

		@SubscribeEvent
		public void BucketFill(FillBucketEvent event){
			EurekaKnowledge.makeProgress(event.entityPlayer, "pump");
		}



		@SubscribeEvent
		public void onPlayerUsesBlock(PlayerInteractEvent event) {
			if (event != null) {
				//eureka block check
				Block block = event.world.getBlock(event.x, event.y, event.z);
				if (block instanceof IEurekaBlock)
					EurekaKnowledge.eurekaBlockEvent(event.world, (IEurekaBlock) block, event.x, event.y, event.z, event.entityPlayer, true);

				//eureka item check
				if (event.entityPlayer.getCurrentEquippedItem() == null)
					return;
				Item item = event.entityPlayer.getCurrentEquippedItem().getItem();
				if (item instanceof IEurekaItem) {
					IEurekaItem eurekaItem = (IEurekaItem) item;
					if (!eurekaItem.isAllowed(event.entityPlayer)) {
						if (event.world.isRemote)
							event.entityPlayer.addChatComponentMessage(new ChatComponentText(eurekaItem.getMessage()));
						for (ItemStack stack : ((IEurekaItem) item).getComponents()) {
							Utils.dropItemstack(event.entity.worldObj, (int) event.entity.posX, (int) event.entity.posY, (int) event.entity.posZ, stack);
						}
						event.entityPlayer.destroyCurrentEquippedItem();
					}
				}
			}
		}

		@SubscribeEvent
		public void blockBreakEvent(BlockEvent.BreakEvent event){
			EurekaKnowledge.makeProgress(event.getPlayer(), "miningWell");
			EurekaKnowledge.makeProgress(event.getPlayer(), "QUARRY");
		}
	}
}
