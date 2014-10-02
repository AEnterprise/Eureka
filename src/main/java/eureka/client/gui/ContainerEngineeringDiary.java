package eureka.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of LGPLv3
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerEngineeringDiary extends Container {

	public ContainerEngineeringDiary(EntityPlayer player) {
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
