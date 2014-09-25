package eureka.core;

import net.minecraft.init.Blocks;
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
import eureka.api.EurekaRegistry;
import eureka.api.events.BlockPlacedEvent;
import eureka.utils.BCItems;
import eureka.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EventHandler {

	public static class FML {

		@SubscribeEvent
		public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
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
		public void BuildcraftBlockInteraction(BlockInteractionEvent event) {
			String key = EurekaRegistry.getKey(event.block);
			event.setCanceled(!EurekaKnowledge.isFinished(event.player, key));
			event.player.addChatComponentMessage(new ChatComponentText("Buildcraft block interaction detected: " + key));
		}

		@SubscribeEvent
		public void BuildcraftPipePlaced(PipePlacedEvent event) {
			String key = getBCKey(event.pipeType);
			event.player.addChatComponentMessage(new ChatComponentText("Buildcraft pipe placed: " + key));
			if (!EurekaKnowledge.isFinished(event.player, key)) {
				event.player.worldObj.setBlockToAir(event.x, event.y, event.z);
				for (ItemStack stack : EurekaRegistry.getDrops(key)) {
					if (stack.getItem() == BCItems.PIPE_ITEMS_WOOD && !EurekaKnowledge.isFinished(event.player, "woodItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("woodItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_COBBLESTONE && !EurekaKnowledge.isFinished(event.player, "cobblestoneItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("cobblestoneItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_STONE && !EurekaKnowledge.isFinished(event.player, "stoneItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("stoneItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_QUARTZ && !EurekaKnowledge.isFinished(event.player, "quartzItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("quartzItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_SANDSTONE && !EurekaKnowledge.isFinished(event.player, "sandstoneItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("sandstoneItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_GOLD && !EurekaKnowledge.isFinished(event.player, "goldItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("goldItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_IRON && !EurekaKnowledge.isFinished(event.player, "ironItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("ironItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_VOID && !EurekaKnowledge.isFinished(event.player, "voidItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("voidItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_OBSIDIAN && !EurekaKnowledge.isFinished(event.player, "obsidianItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("obsidianItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_EMERALD && !EurekaKnowledge.isFinished(event.player, "emeraldItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("emeraldItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_DIAMOND && !EurekaKnowledge.isFinished(event.player, "diamondItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("diamondItems"));
					else
						Utils.dropItemstack(event.player.worldObj, event.x, event.y, event.z, stack.copy());
				}
			}
		}

		@SubscribeEvent
		public void BuildcraftBlockPlaced(BlockPlacedDownEvent event) {
			if (event.player.getEntityWorld().isRemote || event.block.getUnlocalizedName().equals("tile.air"))
				return;
			String key = EurekaRegistry.getKey(event.block);
			if (event.block.getUnlocalizedName().equals("tile.null")) {
				int meta = event.player.worldObj.getBlockMetadata(event.x, event.y, event.z);
				if (meta == 0)
					key = "assemblyTable";
				else if (meta == 1)
					key = "advancedCraftingTable";
				else
					key = "integrationTable";
			}
			if (event.block.getUnlocalizedName().equals("tile.engineBlock")) {
				int meta = event.player.worldObj.getBlockMetadata(event.x, event.y, event.z);
				if (meta == 0)
					key = "redstoneEngine";
				else if (meta == 1)
					key = "stilringEngine";
				else
					key = "combustionEngine";
			}
			if (!EurekaKnowledge.isFinished(event.player, key)) {
				event.player.worldObj.setBlockToAir(event.x, event.y, event.z);
				for (ItemStack stack : EurekaRegistry.getDrops(key)) {
					if (stack.getItem() == BCItems.PIPE_ITEMS_WOOD && !EurekaKnowledge.isFinished(event.player, "woodItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("woodItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_COBBLESTONE && !EurekaKnowledge.isFinished(event.player, "cobblestoneItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("cobblestoneItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_STONE && !EurekaKnowledge.isFinished(event.player, "stoneItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("stoneItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_QUARTZ && !EurekaKnowledge.isFinished(event.player, "quartzItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("quartzItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_SANDSTONE && !EurekaKnowledge.isFinished(event.player, "sandstoneItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("sandstoneItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_GOLD && !EurekaKnowledge.isFinished(event.player, "goldItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("goldItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_IRON && !EurekaKnowledge.isFinished(event.player, "ironItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("ironItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_VOID && !EurekaKnowledge.isFinished(event.player, "voidItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("voidItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_OBSIDIAN && !EurekaKnowledge.isFinished(event.player, "obsidianItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("obsidianItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_EMERALD && !EurekaKnowledge.isFinished(event.player, "emeraldItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("emeraldItems"));
					else if (stack.getItem() == BCItems.PIPE_ITEMS_DIAMOND && !EurekaKnowledge.isFinished(event.player, "diamondItems"))
						dropItemsFromList(event.player.worldObj, event.x, event.y, event.z, EurekaRegistry.getDrops("diamondItems"));
					else
						Utils.dropItemstack(event.player.worldObj, event.x, event.y, event.z, stack.copy());
				}
			}
			event.player.addChatComponentMessage(new ChatComponentText("Buildcraft block placed: " + key));
		}

		@SubscribeEvent
		public void BuildcraftRobotPlaced(RobotPlacementEvent event) {
			event.player.addChatComponentMessage(new ChatComponentText("Buildcraft robot placed: " + getBCKey(event.robotProgram)));

		}

		@SubscribeEvent
		public void onCrafted(PlayerEvent.ItemCraftedEvent event) {
			EurekaKnowledge.makeProgress(event.player, "autoWorkbench", 1);
			if (event.crafting.getItem() == new ItemStack(Blocks.chest).getItem())
				EurekaKnowledge.makeProgress(event.player, "tank", 1);
		}


		@SubscribeEvent
		public void blockPlacement(BlockPlacedEvent event){
			if (event.world.isRemote)
				return;
			String key = EurekaRegistry.getKey(event.block);
			if (key == "")
				return;
			if (!EurekaKnowledge.isFinished(event.player, key)){
				Logger.info("UNAUTHORIZED PLACEMENT");
			}
		}


		private void dropItemsFromList(World world, int x, int y, int z, ItemStack[] stacks) {
			for (ItemStack stack : stacks)
				Utils.dropItemstack(world, x, y, z, stack.copy());
		}

		private String getBCKey(String name){
			return "";
		}
	}


	public static class Forge {

		@SubscribeEvent
		public void BucketFill(FillBucketEvent event) {
			EurekaKnowledge.makeProgress(event.entityPlayer, "pump", 1);
		}


		@SubscribeEvent
		public void onPlayerUsesBlock(PlayerInteractEvent event) {
			if (event.entityPlayer.getCurrentEquippedItem() == null)
				return;
			String key = EurekaRegistry.getKey(event.entityPlayer.getCurrentEquippedItem().getItem());
			if (key == ""){
				key = EurekaRegistry.getKey(event.world.getBlock(event.x, event.y, event.z));
			}
			if (key == "")
				return;
			if (!EurekaKnowledge.isFinished(event.entityPlayer, key))
				event.setCanceled(true);
		}

		@SubscribeEvent
		public void blockBreakEvent(BlockEvent.BreakEvent event) {
			EurekaKnowledge.makeProgress(event.getPlayer(), "miningWell", 1);
			EurekaKnowledge.makeProgress(event.getPlayer(), "quarry", 1);
		}
	}
}
