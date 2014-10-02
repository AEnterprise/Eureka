package eureka.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;


import eureka.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of LGPLv3
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EurekaKnowledge {

	public static void init(EntityPlayer player) {
		if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
			player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
		}
		NBTTagCompound tag = getTag(player);
		for (String key : EurekaRegistry.getKeys())
			initKey(tag, key);

	}

	public static NBTTagCompound getTag(EntityPlayer player) {
		if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
			player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
		}
		return (NBTTagCompound) player.getEntityData().getTag(EntityPlayer.PERSISTED_NBT_TAG);
	}

	public static int getProgress(EntityPlayer player, String key) {
		initKey(getTag(player), key);
		return getTag(player).getInteger(key + "Progress");
	}


	public static boolean isFinished(EntityPlayer player, String key) {
		initKey(getTag(player), key);
		if (player == null)
			return false;
		return player.capabilities.isCreativeMode || getTag(player).getBoolean(key + "Finished");
	}

	public static void makeProgress(EntityPlayer player, String key, int amount) {
		if (player.worldObj.isRemote)
			return;
		try {
			if (!(EurekaRegistry.getRequiredReserch(key) == null || EurekaRegistry.getRequiredReserch(key).isEmpty()))
				for (String requiredResearchKey : EurekaRegistry.getRequiredReserch(key)) {
					if (!isFinished(player, requiredResearchKey) && !EurekaRegistry.isInfoChapter(key))
						return;
				}
			int progress = getProgress(player, key);
			NBTTagCompound tag = getTag(player);
			if (progress < EurekaRegistry.getMaxValue(key)) {
				progress += amount;
				if (progress < 0)
					progress = 0;
				setKey(tag, key + "Progress", progress);
			}
			if (progress >= EurekaRegistry.getMaxValue(key)) {
				if (!isFinished(player, key)) {
					setKey(tag, key + "Finished", true);
					String message = Utils.localize("eureka." + key + "Finished");
					player.addChatMessage(new ChatComponentText(Utils.localize("Eureka")));
					player.addChatComponentMessage(new ChatComponentText(message));
				}
			}
		} catch (Throwable e) {
			//fake player issue, ignoring
		}
	}

	private static void initKey(NBTTagCompound tag, String key) {
		if (!tag.hasKey(key + "Progress")) {
			setKey(tag, key + "Progress", 0);
			setKey(tag, key + "Finished", false);
		}
	}

	public static void setKey(NBTTagCompound tag, String key, int integer) {
		tag.setInteger(key, integer);
	}

	public static void setKey(NBTTagCompound tag, String key, boolean bool) {
		tag.setBoolean(key, bool);
	}
}
