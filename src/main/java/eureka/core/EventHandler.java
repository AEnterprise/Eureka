package eureka.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import buildcraft.api.events.BlockInteractionEvent;
import buildcraft.api.events.BlockPlacedDownEvent;
import buildcraft.api.events.PipePlacedEvent;

import eureka.Eureka;
import eureka.api.EurekaKnowledge;
import eureka.api.interfaces.IEurekaBlock;
import eureka.api.interfaces.IEurekaItem;

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
			if (event.player.getEntityWorld().isRemote)
				return;
			event.player.addChatComponentMessage(new ChatComponentText("Buildcraft block interaction detected: " + event.block.getUnlocalizedName()));
		}

		@SubscribeEvent
		public void BuildcraftPipePlaced(PipePlacedEvent event){
			event.player.addChatComponentMessage(new ChatComponentText("Buildcraft pipe placed: " + event.pipeType));
		}

		@SubscribeEvent
		public void BuildcraftBlockPlaced(BlockPlacedDownEvent event){
			if (event.player.getEntityWorld().isRemote)
				return;
			event.player.addChatComponentMessage(new ChatComponentText("Buildcraft block placed: " + event.block.getUnlocalizedName()));
		}

	}


	public static class Forge {
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
	}
}
