package eureka.core;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

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
		}

		@SubscribeEvent
		public void BuildcraftBlockPlaced(BlockPlacedDownEvent event){
			if (event.player.getEntityWorld().isRemote || event.block.getUnlocalizedName().equals("tile.pipeBlock"))
				return;
			String key = BCUtils.getBCKey(event.block.getUnlocalizedName());
			if (!EurekaKnowledge.isFinished(event.player, key)){
				event.player.worldObj.setBlockToAir(event.x, event.y, event.z);
				for (ItemStack stack: BCUtils.getBCDrops(key))
					Utils.dropItemstack(event.player.worldObj, event.x, event.y, event.z, stack);
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
			EurekaKnowledge.makeProgress(event.getPlayer(), "quarry");
		}
	}
}
