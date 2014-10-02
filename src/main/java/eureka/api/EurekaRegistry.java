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
 * Eureka is distributed under the terms of LGPLv3
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
	private static HashMap<Item, String> items = new HashMap<Item, String>(50);
	private static HashMap<Item, String> craftingProgressItems = new HashMap<Item, String>(50);
	private static ArrayList<String> craftKeys = new ArrayList<String>(50);
	private static HashMap<Block, String> breakBlockProgression = new HashMap<Block, String>(50);
	private static ArrayList<String> breakAnyProgression = new ArrayList<String>(50);
	private static HashMap<Block, String> placeBlockProgression = new HashMap<Block, String>(50);
	private static ArrayList<String> enderTeleportKeys = new ArrayList<String>(50);
	private static ArrayList<String> killKeys = new ArrayList<String>(50);
	private static ArrayList<String> deathKeys = new ArrayList<String>(50);
	private static ArrayList<String> pipeProgressKeys = new ArrayList<String>(50);
	private static HashMap<String, ArrayList<String>> pipePlacementKeys = new HashMap<String, ArrayList<String>>(50);

	/**
	 * Register your keys here for the EUREKA system
	 */
	public static void register(EurekaInformation information) {
		chapters.put(information.getKey(), information);
		keys.add(information.getKey());
		GameRegistry.registerItem(new ResearchNote(information.getKey()).setUnlocalizedName("researchNote"), "researchNote" + information.getKey());
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

	public static boolean bindToKey(Item item, String key){
		if (items.containsKey(item)) {
			Logger.error("Error while binding a item (" + item.getUnlocalizedName() + ") to a key (" + key + "): the item has already be bound to another key (" + items.get(item) + ")");
			return false;
		}
		items.put(item, key);
		return true;
	}

	public static boolean addCrafingProgress(Item item, String key) {
		if (craftingProgressItems.containsKey(item)){
			Logger.error("Error while registering crafting progress for item " + item + ": specified item is already used for another key (" + craftingProgressItems.get(item)  +")");
			return false;
		}
		craftingProgressItems.put(item, key);
		return true;
	}

	public static void addAnyCraftingProgress(String key){
		craftKeys.add(key);
	}

	public static boolean addBlockBreakProgress(Block block, String key){
		if (breakBlockProgression.containsKey(block)){
			Logger.error("Error while registering block break progression for block " + block + ": specified block is already used for anohter key (" + breakBlockProgression.get(block) + ")");
			return false;
		}
		breakBlockProgression.put(block, key);
		return true;
	}

	public static void addBreakAnyProgress(String key){
		breakAnyProgression.add(key);
	}

	public static boolean addPlaceBlockProgress(Block block, String key){
		if (placeBlockProgression.containsKey(block)){
			Logger.error("Error while registering place block progression for block " + block + ": specified block is already used for another key (" + placeBlockProgression.get(block));
			return false;
		}
		placeBlockProgression.put(block, key);
		return true;
	}

	public static void addEnderTeleportKey(String key){
		enderTeleportKeys.add(key);
	}

	public static void addKillKey(String key){
		killKeys.add(key);
	}

	public static void addDeathKey(String key){
		deathKeys.add(key);
	}

	public static void addPipeprogressKey(String key){
		pipeProgressKeys.add(key);
	}

	public static void addPipePlacementKey(String pipeType, String key){
		if(!pipePlacementKeys.containsKey(pipeType)){
			ArrayList<String> list = new ArrayList<String>(20);
			list.add(key);
			pipePlacementKeys.put(pipeType, list);
		} else {
			ArrayList<String> list = (ArrayList) pipePlacementKeys.get(pipeType).clone();
			pipePlacementKeys.remove(pipeType);
			list.add(key);
			pipePlacementKeys.put(pipeType, list);
		}
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

	public static String getCraftingProgressKey(Item item){
		if (!craftingProgressItems.containsKey(item))
			return "";
		return craftingProgressItems.get(item);
	}

	public static ArrayList<String> getCraftKeys() {
		return (ArrayList) craftKeys.clone();
	}

	public static String getBlockBreakKey(Block block){
		if (!breakBlockProgression.containsKey(block))
			return "";
		return breakBlockProgression.get(block);
	}

	public static ArrayList<String> getBreakAnyKeys(){
		return (ArrayList) breakAnyProgression.clone();
	}

	public static String getBlockPlacementKey(Block block){
		if (!placeBlockProgression.containsKey(block))
			return "";
		return placeBlockProgression.get(block);
	}

	public static ArrayList<String> getEnderTeleportKeys(){
		return (ArrayList) enderTeleportKeys.clone();
	}

	public static ArrayList<String> getKillKeys(){
		return (ArrayList) killKeys.clone();
	}

	public static ArrayList<String> getDeathKeys(){
		return (ArrayList) deathKeys.clone();
	}

	public static ArrayList<String> getPipeProgressKeys() {
		return (ArrayList) pipeProgressKeys.clone();
	}

	public static ArrayList<String> getPipePlacementKeys(String pipetype){
		if (!pipePlacementKeys.containsKey(pipetype))
			return new ArrayList<String>();
		return (ArrayList) pipePlacementKeys.get(pipetype).clone();
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
