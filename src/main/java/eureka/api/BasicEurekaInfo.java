package eureka.api;

import java.util.ArrayList;
import java.util.List;

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
public class BasicEurekaInfo implements IEurekaInfo {
	private final String category, name;
	private final int maxProgress;
	private final ItemStack displayStack;
	private final List<String> requiredResearch;

	public BasicEurekaInfo(String name, String category, int maxProgress, ItemStack displayStack, String... requiredResearch) {
		this.name = name;
		this.category = category;
		this.maxProgress = maxProgress;
		this.displayStack = displayStack;
		List<String> research = new ArrayList<String>();
		for (String r : requiredResearch)
			research.add(r);
		this.requiredResearch = research;
	}

	public BasicEurekaInfo(String name, String category, int maxProgress, Item item, String... requiredResearch) {
		this(name, category, maxProgress, new ItemStack(item), requiredResearch);
	}

	public BasicEurekaInfo(String name, String category, int maxProgress, Block block, String... requiredResearch) {
		this(name, category, maxProgress, new ItemStack(block), requiredResearch);
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public int getMaxProgress() {
		return maxProgress;
	}

	@Override
	public ItemStack getDisplayStack() {
		return displayStack;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<String> getRequiredResearch() {
		return requiredResearch;
	}
}
