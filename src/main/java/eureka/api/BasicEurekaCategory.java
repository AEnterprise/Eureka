package eureka.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BasicEurekaCategory implements ICategory {
	private final String name;
	private final ItemStack displayStack;

	public BasicEurekaCategory(String name, ItemStack displayStack) {
		this.name = name;
		this.displayStack = displayStack;
	}

	public BasicEurekaCategory(String name, Block block) {
		this(name, new ItemStack(block));
	}

	public BasicEurekaCategory(String name, Item item) {
		this(name, new ItemStack(item));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ItemStack getDisplayStack() {
		return displayStack;
	}
}
