package eureka.utils;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class Utils {

	public static String localize(String key) {
		return StatCollector.translateToLocal(key);
	}

	public static void dropItemstack(World world, int x, int y, int z, ItemStack stack) {
		float f1 = 0.7F;
		double d = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
		double d1 = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
		double d2 = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
		EntityItem itemToDrop = new EntityItem(world, x + d, y + d1, z + d2, stack);
		itemToDrop.delayBeforeCanPickup = 10;
		if (!world.isRemote)
			world.spawnEntityInWorld(itemToDrop);
	}


}
