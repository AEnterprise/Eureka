package eureka.core;

import net.minecraft.item.ItemBlock;
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


import eureka.Eureka;
import eureka.api.EurekaKnowledge;
import eureka.api.EurekaRegistry;
import eureka.api.events.BlockPlacedEvent;
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

		}

		@SubscribeEvent
		public void BuildcraftBlockPlaced(BlockPlacedDownEvent event) {
			if (event.player.getEntityWorld().isRemote || event.block.getUnlocalizedName().equals("tile.air"))
				return;
			String key = EurekaRegistry.getKey(event.block);

		}

		/*@SubscribeEvent
		public void BuildcraftRobotPlaced(RobotPlacementEvent event) {
			event.player.addChatComponentMessage(new ChatComponentText("Buildcraft robot placed: " + getBCKey(event.robotProgram)));

		}*/

		@SubscribeEvent
		public void onCrafted(PlayerEvent.ItemCraftedEvent event) {
			for (String key : EurekaRegistry.getCraftKeys())
				EurekaKnowledge.makeProgress(event.player, key, 1);
			String key = EurekaRegistry.getCraftingProgressKey(event.crafting.getItem());
			if (!key.isEmpty())
				EurekaKnowledge.makeProgress(event.player, key, 1);
		}


		@SubscribeEvent
		public void blockPlacement(BlockPlacedEvent event){
			if (event.world.isRemote)
				return;
			String key = EurekaRegistry.getKey(event.block);
			if (!key.equals(""))
				if (!EurekaKnowledge.isFinished(event.player, key)){
					event.player.addChatComponentMessage(new ChatComponentText(Utils.localize("eureka.missingKnowledge")));
					event.world.setBlockToAir(event.xCoord, event.yCoord, event.zCoord);
					dropItemsFromList(event.world, event.xCoord, event.yCoord, event.zCoord, EurekaRegistry.getDrops(key));
				}
			key = EurekaRegistry.getBlockPlacementKey(event.block);
			if (!key.equals(""))
				EurekaKnowledge.makeProgress(event.player, key, 1);
		}
	}


	public static class Forge {

		@SubscribeEvent
		public void BucketFill(FillBucketEvent event) {
			EurekaKnowledge.makeProgress(event.entityPlayer, "pump", 1);
		}


		@SubscribeEvent
		public void onPlayerUsesBlock(PlayerInteractEvent event) {
			String key;
			if (event.world.isRemote)
				return;
			if (event.entityPlayer.getCurrentEquippedItem() != null) {
				key = EurekaRegistry.getKey(event.entityPlayer.getCurrentEquippedItem().getItem());
				if (!key.equals("") && !EurekaKnowledge.isFinished(event.entityPlayer, key)) {
					if (event.entityPlayer.getCurrentEquippedItem().stackSize > 1)
						event.entityPlayer.getCurrentEquippedItem().stackSize--;
					else
						event.entityPlayer.destroyCurrentEquippedItem();
					dropItemsFromList(event.world, (int)event.entityPlayer.posX,(int) event.entityPlayer.posY, (int)event.entityPlayer.posZ, EurekaRegistry.getDrops(key));
					event.setCanceled(true);

				}
			}
			key = EurekaRegistry.getKey(event.world.getBlock(event.x, event.y, event.z));
			if (key.equals(""))
				return;
			if (!EurekaKnowledge.isFinished(event.entityPlayer, key)) {
				event.setCanceled(true);
			}
		}

		@SubscribeEvent
		public void blockBreakEvent(BlockEvent.BreakEvent event) {
			for (String key: EurekaRegistry.getBreakAnyKeys())
				EurekaKnowledge.makeProgress(event.getPlayer(), key, 1);
			String key = EurekaRegistry.getBlockBreakKey(event.block);
			if (!key.equals(""))
				EurekaKnowledge.makeProgress(event.getPlayer(), key, 1);
			key = EurekaRegistry.getBlockPlacementKey(event.block);
			if (!key.equals(""))
				EurekaKnowledge.makeProgress(event.getPlayer(), key, -1);
		}
	}
	public static void dropItemsFromList(World world, int x, int y, int z, ItemStack[] stacks) {
		for (ItemStack stack : stacks) {
			String key = EurekaRegistry.getKey(stack.getItem());
			if (key.equals("")) {
				if (stack.getItem() instanceof ItemBlock)
					key = EurekaRegistry.getKey(((ItemBlock) stack.getItem()).field_150939_a);
			}
			if (!key.equals(""))
				for (ItemStack component: EurekaRegistry.getDrops(key))
					Utils.dropItemstack(world, x, y, z, component.copy());
			Utils.dropItemstack(world, x, y, z, stack.copy());
		}
	}
}
