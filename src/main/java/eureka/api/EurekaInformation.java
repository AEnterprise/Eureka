package eureka.api;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;


import eureka.api.client.gui.EurekaChapter;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of LGPLv3
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class EurekaInformation {

	public abstract String getKey();

	public abstract int getMaxValue();

	public abstract ItemStack getDisplayStack();

	public abstract String getCategory();

	public abstract EurekaChapter getGui();

	public abstract ArrayList<String> getRequiredResearch();
}
