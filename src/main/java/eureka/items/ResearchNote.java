package eureka.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;


import eureka.Eureka;
import eureka.api.EurekaKnowledge;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ResearchNote extends Item {
	public IIcon icon;
	public String key;

	public ResearchNote(String key){
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
		while (!EurekaKnowledge.isFinished(player, key)){
			EurekaKnowledge.makeProgress(player, key);
		}
		if (stack.stackSize == 1)
			return new ItemStack(Items.paper);
		stack.stackSize--;
		return stack;
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		iconRegister.registerIcon("eureka:ResearchNotes");
	}
}
