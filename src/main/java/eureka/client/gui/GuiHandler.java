package eureka.client.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import eureka.network.MessageEngineeringDiary;
import eureka.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
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
			PacketHandler.instance.sendToAllAround(new MessageEngineeringDiary(player), new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 1));
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
