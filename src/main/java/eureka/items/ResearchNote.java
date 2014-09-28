package eureka.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;


import eureka.Eureka;
import eureka.api.EurekaKnowledge;
import eureka.api.EurekaRegistry;
import eureka.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ResearchNote extends Item {
	public IIcon icon;
	public String key;

	public ResearchNote(String key) {
		this.key = key;
		setCreativeTab(Eureka.eureka);
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return icon;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (EurekaKnowledge.isFinished(player, key))
			return stack;
		EurekaKnowledge.makeProgress(player, key, EurekaRegistry.getMaxValue(key) - EurekaKnowledge.getProgress(player, key));
		if (stack.stackSize == 1)
			return new ItemStack(Items.paper);
		stack.stackSize--;
		return stack;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(Utils.localize("engineeringDiary." + key + ".title"));
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("eureka:ResearchNote");
	}
}
