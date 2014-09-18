package eureka.utils;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;


import eureka.core.Logger;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BCUtils {
	private static HashMap<String, String> BCKeys = new HashMap<String, String>(100);
	private static HashMap<String, ArrayList<ItemStack>> drops = new HashMap<String, ArrayList<ItemStack>>(100);

	public static void addBCKey(String unlocalizedname, String key, ItemStack...stacks){
		BCKeys.put(unlocalizedname, key);
		ArrayList<ItemStack> tempList = new ArrayList<ItemStack>(9);
		int teller = 0;
		for (ItemStack stack: stacks) {
			tempList.add(stack);
			if (stack == null)
				Logger.error("Itemstack parameter " + teller + "was null" +  key);
			teller++;
		}
		drops.put(key, tempList);
	}

	public static String getBCKey(String unlocalizedname){
		if (BCKeys.containsKey(unlocalizedname))
			return BCKeys.get(unlocalizedname);
		else
			return unlocalizedname;
	}

	public static ArrayList<ItemStack> getBCDrops(String key){
		if (drops.containsKey(key))
			return drops.get(key);
		return null;
	}
}
