package eureka.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

import eureka.Eureka;
import eureka.networking.MessageResearch;
import eureka.networking.PacketHandler;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemEngineeringDiary extends ItemBook {

	public ItemEngineeringDiary() {
		setCreativeTab(Eureka.eureka);
		setTextureName("eureka:engineeringDiary");
		setUnlocalizedName("engineeringDiary");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			PacketHandler.instance.sendToAllAround(new MessageResearch(player), new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 1));
		if (!world.isRemote)
			player.openGui(Eureka.instance, 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		return stack;
	}
}
