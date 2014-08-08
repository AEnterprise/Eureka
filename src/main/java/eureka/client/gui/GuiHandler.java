package eureka.client.gui;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftAdditions.wordpress.com/
 * Eureka is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftAdditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == 1){
			if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG))
				player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
			FMLClientHandler.instance().getClientPlayerEntity().getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG));
			return new ContainerEngineeringDiary(player);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == 1){
			return new GuiEngineeringDiary(player);
		}
		return null;
	}
}
