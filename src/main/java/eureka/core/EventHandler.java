package eureka.core;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import eureka.Eureka;
import eureka.api.EnumProgressOptions;
import eureka.api.EurekaAPI;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EventHandler {

	@SubscribeEvent
	public void onEntityConstruction(EntityEvent.EntityConstructing event) {
		if (event.entity instanceof EntityPlayer && PlayerResearch.get((EntityPlayer) event.entity) == null) {
			PlayerResearch.register((EntityPlayer) event.entity);
		}
	}

	@SubscribeEvent
	public void onClone(PlayerEvent.Clone event) {
		PlayerResearch.get(event.entityPlayer).copy(PlayerResearch.get(event.original));
	}

	@SubscribeEvent
	public void playerLogin(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
		//give engineering diary
		if (!event.player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).hasKey("bookRecieved") && Eureka.giveBook) {
			for (int slot = 0; slot < event.player.inventory.getSizeInventory(); slot++) {
				if (event.player.inventory.getStackInSlot(slot) == null) {
					event.player.inventory.setInventorySlotContents(slot, new ItemStack(Eureka.engineeringDiary));
					NBTTagCompound tag = event.player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
					tag.setBoolean("bookRecieved", true);
					event.player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, tag);
					return;
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	//make sure it gets trigered first to prevent handeling from other mods
	public void onPlayerUsesBlock(PlayerInteractEvent event) {
		String key = "";
		if (event.world.isRemote)
			return;
		EntityPlayer player = event.entityPlayer;
		PlayerResearch research = PlayerResearch.get(player);
		if (player.getCurrentEquippedItem() != null)
			key = EurekaAPI.API.getKey(player.getCurrentEquippedItem());
		if (!research.isFinished(key) && !key.equals("")) {
			ItemStack stack = player.getCurrentEquippedItem().copy();
			stack.stackSize = 1;
			dropItems(event.world, event.x, event.y, event.z, EurekaAPI.API.getDrops(stack));
			if (event.entityPlayer.getCurrentEquippedItem().stackSize > 1)
				event.entityPlayer.getCurrentEquippedItem().stackSize--;
			else
				event.entityPlayer.destroyCurrentEquippedItem();
			event.setCanceled(true);
		}

		key = EurekaAPI.API.getKey(event.world.getBlock(event.x, event.y, event.z));
		if (key != null && !key.equals("") && !research.isFinished(key))
			event.setCanceled(true);
	}

	@SubscribeEvent
	public void bucketFill(FillBucketEvent event) {
		makeProgress(EnumProgressOptions.FILL_BUCKET, PlayerResearch.get(event.entityPlayer), null);
	}

	@SubscribeEvent
	public void onTeleport(EnderTeleportEvent event) {
		if (event.entityLiving != null && event.entityLiving instanceof EntityPlayer)
			makeProgress(EnumProgressOptions.ENDER_TELEPORT, PlayerResearch.get((EntityPlayer) event.entityLiving), null);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void blockPlacement(BlockEvent.PlaceEvent event) {
		String key = EurekaAPI.API.getKey(event.block);
		PlayerResearch research = PlayerResearch.get(event.player);
		if (!research.isFinished(key) && !key.equals("")) {
			dropItems(event.world, event.x, event.y, event.z, EurekaAPI.API.getDrops(new ItemStack(event.block)));
			event.world.setBlock(event.x, event.y, event.z, Blocks.air);
		}
		makeProgress(EnumProgressOptions.PLACE_BLOCK, research, event.block);
		makeProgress(EnumProgressOptions.PLACE_ANY_BLOCK, research, null);
	}

	@SubscribeEvent
	public void onCrafted(cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent event) {
		//there are some stupid mods out thare that don't properly fill in the event
		if (event == null || event.player == null || event.crafting == null)
			return;
		PlayerResearch research = PlayerResearch.get(event.player);
		Object obj = event.crafting.getItem();
		if (event.crafting.getItem() instanceof ItemBlock)
			obj = ((ItemBlock) event.crafting.getItem()).field_150939_a;
		makeProgress(EnumProgressOptions.CRAFTING, research, obj);
		makeProgress(EnumProgressOptions.CRAFT_ANYTHING, research, null);
	}

	@SubscribeEvent
	public void blockBreak(BlockEvent.BreakEvent event) {
		PlayerResearch research = PlayerResearch.get(event.getPlayer());
		revertProgress(EnumProgressOptions.PLACE_BLOCK, research, event.block);
		makeProgress(EnumProgressOptions.BREAK_BLOCK, research, event.block);
		makeProgress(EnumProgressOptions.BREAK_ANY_BLOCK, research, null);
	}

	private void dropItems(World world, int x, int y, int z, List<ItemStack> stacks) {
		for (ItemStack stack : stacks) {
			if (!world.isRemote && stack != null && stack.stackSize > 0 && stack.getItem() != null) {
				float rx = world.rand.nextFloat() * 0.8F + 0.1F;
				float ry = world.rand.nextFloat() * 0.8F + 0.1F;
				float rz = world.rand.nextFloat() * 0.8F + 0.1F;
				EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, stack.copy());
				float factor = 0.05F;
				entityItem.motionX = (world.rand.nextGaussian() * factor);
				entityItem.motionY = (world.rand.nextGaussian() * factor + 0.2000000029802322D);
				entityItem.motionZ = (world.rand.nextGaussian() * factor);
				world.spawnEntityInWorld(entityItem);
			}
		}
	}

	private void makeProgress(EnumProgressOptions option, PlayerResearch research, Object arg) {
		if (research == null)
			return;
		EurekaAPIImplementation imp = (EurekaAPIImplementation) EurekaAPI.API;
		for (String key : imp.getKeysforProgress(option, arg))
			research.makeProgress(key);
	}

	private void revertProgress(EnumProgressOptions option, PlayerResearch research, Object arg) {
		if (research == null)
			return;
		EurekaAPIImplementation imp = (EurekaAPIImplementation) EurekaAPI.API;
		for (String key : imp.getKeysforProgress(option, arg))
			research.revertProgress(key);
	}
}
