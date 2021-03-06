package eureka.core;

import com.google.common.collect.ImmutableList;
import cpw.mods.fml.common.registry.GameRegistry;
import eureka.api.*;
import eureka.items.ItemResearchNote;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EurekaAPIImplementation implements IEurekAPI {
	private ArrayList<ICategory> categories = new ArrayList<ICategory>();
	private HashMap<String, ICategory> category = new HashMap<String, ICategory>();
	private HashMap<String, List<IEurekaInfo>> keys = new HashMap<String, List<IEurekaInfo>>();
	private ArrayList<IEurekaInfo> keylist = new ArrayList<IEurekaInfo>();
	private HashMap<String, IEurekaInfo> keylookup = new HashMap<String, IEurekaInfo>();
	private ArrayList<IDropHandler> dropHandlers = new ArrayList<IDropHandler>();
	private HashMap<Block, String> blockMap = new HashMap<Block, String>();
	private HashMap<Item, String> itemMap = new HashMap<Item, String>();
	private EnumMap<EnumProgressOptions, HashMap<Object, List<String>>> progress = new EnumMap<EnumProgressOptions, HashMap<Object, List<String>>>(EnumProgressOptions.class);

	@Override
	public void registerCategory(ICategory category) {
		categories.add(category);
		keys.put(category.getName(), new ArrayList<IEurekaInfo>());
		this.category.put(category.getName(), category);
	}

	@Override
	public void register(IEurekaInfo info) {
		if (keys.keySet().contains(info.getName())) {
			Logger.error("Duplicate registation: '" + info.getName() + "' is already used");
		} else {
			if (!categories.contains(category.get(info.getCategory()))) {
				Logger.error("Warning: Key with non-existing category added");
				return;
			}
			keylist.add(info);
			List<IEurekaInfo> list = keys.get(info.getCategory());
			list.add(info);
			keys.remove(info.getCategory());
			keys.put(info.getCategory(), list);
			keylookup.put(info.getName(), info);
		}
		ItemResearchNote note = new ItemResearchNote(info.getName());
		GameRegistry.registerItem(note, "researchNote" + info.getName());
	}

	@Override
	public void registerDropHandler(IDropHandler handler) {
		dropHandlers.add(handler);
	}

	@Override
	public List<IEurekaInfo> getAllKeys() {
		return ImmutableList.copyOf(keylist);
	}

	@Override
	public List<IEurekaInfo> getKeys(String category) {
		return ImmutableList.copyOf(keys.get(category));
	}

	@Override
	public List<ICategory> getCategories() {
		return ImmutableList.copyOf(categories);
	}

	@Override
	public int getMaxProgress(String key) {
		if (!keylist.contains(keylookup.get(key))) {
			return 0;
		} else {
			return keylookup.get(key).getMaxProgress();
		}
	}

	@Override
	public boolean hasPlayerFinishedResearch(String key, EntityPlayer player) {
		PlayerResearch research = PlayerResearch.get(player);
		return research != null && research.isFinished(key);
	}

	@Override
	public void makeProgress(String key, EntityPlayer player) {
		PlayerResearch research = PlayerResearch.get(player);
		if (research != null)
			research.makeProgress(key);
	}

	@Override
	public void revertProgress(String key, EntityPlayer player) {
		PlayerResearch research = PlayerResearch.get(player);
		if (research != null)
			research.revertProgress(key);
	}

	@Override
	public int getProgress(String key, EntityPlayer player) {
		PlayerResearch research = PlayerResearch.get(player);
		return research == null ? 0 : research.getProgress(key);
	}

	@Override
	public void bindToKey(Object o, String key) {
		if (o instanceof Item)
			itemMap.put((Item) o, key);
		else if (o instanceof Block)
			blockMap.put((Block) o, key);
	}

	@Override
	public String getKey(Object o) {
		if (o instanceof ItemStack)
			o = ((ItemStack) o).getItem();
		if (o instanceof Item)
			return itemMap.get(o);
		if (o instanceof Block)
			return blockMap.get(o);

		return "";
	}

	@Override
	public List<ItemStack> getDrops(ItemStack stack) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		boolean handled = false;
		for (IDropHandler handler : dropHandlers) {
			if (handler.handles(stack)) {
				handled = true;
				List<ItemStack> temp = handler.getDrops(stack);
				for (ItemStack stack2 : temp)
					list.addAll(getDrops(stack2));
			}
		}
		if (!handled)
			list.add(stack);
		return list;
	}

	@Override
	public boolean keyRegistered(String key) {
		return keylookup.keySet().contains(key);
	}

	@Override
	public void registerProgressOption(String key, EnumProgressOptions option, Object arg) {
		HashMap<Object, List<String>> map = progress.get(option);
		if (map == null) {
			map = new HashMap<Object, List<String>>();
		}
		if (!option.hasArg())
			arg = null;
		if (arg == null)
			arg = "ANY";
		List<String> list;
		if (map.containsKey(arg))
			list = map.get(arg);
		else
			list = new ArrayList<String>();
		if (!list.contains(key))
			list.add(key);
		map.remove(arg);
		map.put(arg, list);
		progress.remove(option);
		progress.put(option, map);
	}

	@Override
	public void completeResearch(EntityPlayer player, String key) {
		PlayerResearch.get(player).completeResearch(key);
	}

	@Override
	public IEurekaInfo getInfo(String key) {
		return keylookup.get(key);
	}

	public List<String> getKeysforProgress(EnumProgressOptions option, Object arg) {
		HashMap<Object, List<String>> map = progress.get(option);
		if (arg == null)
			arg = "ANY";
		if (map == null || !map.containsKey(arg))
			return Collections.EMPTY_LIST;
		return ImmutableList.copyOf(map.get(arg));
	}

	@Override
	public ICategory getCategory(String key) {
		return category.get(key);
	}
}
