package eureka.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import eureka.Eureka;
import eureka.api.EurekaAPI;
import eureka.core.TextGetter;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemResearchNote extends Item {
	public String key;

	public ItemResearchNote(String key) {
		setCreativeTab(Eureka.eureka);
		setTextureName("eureka:ResearchNote");
		setUnlocalizedName("researchNote");
		this.key = key;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		EurekaAPI.API.completeResearch(player, key);
		if (EurekaAPI.API.hasPlayerFinishedResearch(key, player))
			player.destroyCurrentEquippedItem();
		return stack;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
		list.add(TextGetter.getTitle(key));
	}
}
