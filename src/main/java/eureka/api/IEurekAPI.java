package eureka.api;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public interface IEurekAPI {

	void registerCategory(ICategory category);

	void register(IEurekaInfo info);

	void registerDropHandler(IDropHandler handler);

	List<IEurekaInfo> getAllKeys();

	List<IEurekaInfo> getKeys(String category);

	List<ICategory> getCategories();

	int getMaxProgress(String key);

	boolean hasPlayerFinishedResearch(String key, EntityPlayer player);

	void makeProgress(String key, EntityPlayer player);

	void revertProgress(String key, EntityPlayer player);

	int getProgress(String key, EntityPlayer player);

	void bindToKey(Object o, String key);

	String getKey(Object o);

	List<ItemStack> getDrops(ItemStack stack);

	boolean keyRegistered(String key);

	void registerProgressOption(String key, EnumProgressOptions option, Object arg);

	void completeResearch(EntityPlayer player, String key);

	IEurekaInfo getInfo(String key);
}
