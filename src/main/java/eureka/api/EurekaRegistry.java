package eureka.api;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;


import eureka.api.client.gui.EurekaChapter;
import eureka.core.Logger;
import eureka.items.ResearchNote;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EurekaRegistry {
	private static HashMap<String, EurekaInformation> chapters = new HashMap<String, EurekaInformation>(50);
	private static ArrayList<String> keys = new ArrayList<String>(50);
	private static ArrayList<String> categoriesList = new ArrayList<String>(50);
	private static HashMap<String, ItemStack> categories = new HashMap<String, ItemStack>(50);
	private static HashMap<String, ItemStack[]> drops = new HashMap<String, ItemStack[]>(50);
	private static HashMap<Block, String> blocks = new HashMap<Block, String>(50);
	private static HashMap<String, String> blockMeta = new HashMap<String, String>(50);
	private static HashMap<Item, String> items = new HashMap<Item, String>(50);

	/**
	 * Register your keys here for the EUREKA system
	 */
	public static void register(EurekaInformation information) {
		chapters.put(information.getKey(), information);
		keys.add(information.getKey());
		GameRegistry.registerItem(new ResearchNote(information.getKey()).setUnlocalizedName("researchNote" + information.getKey()), "researchNote" + information.getKey());
		Logger.info("Eureka key registered: " + information.getKey());
	}

	public static boolean registerCategory(String category, ItemStack stack) {
		if (categoriesList.contains(category)) {
			Logger.error("Error while registering a category (" + category + "): The a category with that name is already registered");
			return false;
		}
		categoriesList.add(category);
		categories.put(category, stack);
		return true;
	}

	public static boolean registerDrops(String key, ItemStack...stacks){
		if (drops.containsKey(key)) {
			Logger.error("Error while registering drops for a key (" + key + "): There are already drops registered for that key");
			return false;
		}
		drops.put(key, stacks);
		return true;
	}

	public static boolean bindToKey(Block block, String key){
		if (blocks.containsKey(block)) {
			Logger.error("Error while binding a block (" + block.getUnlocalizedName() + ") to a key (" + key + "): the block has already be bound to another key (" + blocks.get(block) + ")");
			return false;
		}
		blocks.put(block, key);
		return true;
	}

	public static boolean bindToKey(Block block, int meta, String key){
		if (blocks.containsKey(block)) {
			Logger.error("Error while binding a block (" + block.getUnlocalizedName() + ") to a key (" + key + "): the block has already be bound to another key (" + blocks.get(block) + ")");
			return false;
		}
		blocks.put(block, key);
		blockMeta.put(key, Integer.toString(meta));
		return true;
	}

	public static boolean bindToKey(Item item, String key){
		if (items.containsKey(item)) {
			Logger.error("Error while binding a item (" + item.getUnlocalizedName() + ") to a key (" + key + "): the item has already be bound to another key (" + items.get(item) + ")");
			return false;
		}
		items.put(item, key);
		return true;
	}

	public static ItemStack[] getDrops(String key){
		if (!drops.containsKey(key))
			return new ItemStack[0];
		return drops.get(key).clone();
	}

	public static String getKey(Block block){
		if (!blocks.containsKey(block))
			return "";
		return blocks.get(block);
	}

	public static String getKey(Item item){
		if (!items.containsKey(item))
			return "";
		return items.get(item);
	}

	/**
	 * @return a clone of the list containing all EUREKA keys
	 */
	public static ArrayList<String> getKeys() {
		return (ArrayList) keys.clone();
	}

	public static int getMaxValue(String key) {
		if (!keys.contains(key))
			return 0;
		return chapters.get(key).getMaxValue();
	}

	public static ItemStack getDisplayStack(String key) {
		if (!keys.contains(key))
			return null;
		return chapters.get(key).getDisplayStack();
	}

	public static EurekaChapter getChapterGui(String key) {
		return chapters.get(key).getGui();
	}

	public static String getCategory(String key) {
		return chapters.get(key).getCategory();
	}

	public static ArrayList<String> getCategoriesList() {
		return (ArrayList) categoriesList.clone();
	}

	public static ItemStack getCategoryDisplayStack(String category) {
		return categories.get(category);
	}

	public static ArrayList<String> getRequiredReserch(String key) {
		return chapters.get(key).getRequiredResearch();
	}

	public static boolean isInfoChapter(String key) {
		return chapters.get(key).getGui().isInfoPage();
	}

}
