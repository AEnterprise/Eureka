package eureka.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BasicDropHandler implements IDropHandler {
	private Item item;
	private int meta;
	private NBTTagCompound nbt;
	private List<ItemStack> drops;

	public BasicDropHandler(ItemStack stack, ItemStack... stacks) {
		item = stack.getItem();
		meta = stack.getItemDamage();
		nbt = stack.stackTagCompound;
		drops = new ArrayList<ItemStack>();
		drops.addAll(Arrays.asList(stacks));
	}

	@Override
	public boolean handles(ItemStack stack) {
		return stack != null && stack.getItem() == item && stack.getItemDamage() == meta && stack.stackTagCompound == nbt;
	}

	@Override
	public List<ItemStack> getDrops(ItemStack stack) {
		return drops;
	}
}
