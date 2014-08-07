package eureka.core;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftAdditions.wordpress.com/
 * Eureka is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftAdditions.wordpress.com/wiki/licensing-stuff/
 */
public class EurekaRegistry {
    private static HashMap<String, EurekaEntry> entries = new HashMap<String, EurekaEntry>(50);
	private static ArrayList<String> keys = new ArrayList<String>(50);

    /**
     * Register your keys here for the EUREKA system
     */
    public static void registerKey(Class<? extends EurekaInformation> information){
	    try {
		    String category = information.newInstance().getCategory();
		    String key = information.newInstance().getKey();
		    int increment = information.newInstance().getIncrement();
		    int maxValue = information.newInstance().getMaxValue();
		    ItemStack stack = information.newInstance().getDisplayStack();
		    entries.put(key, new EurekaEntry(category, increment, maxValue, stack));
		    keys.add(key);
	    } catch (Throwable e){
		    Logger.error("Error trying to register" + information.toString());
		    e.printStackTrace();
	    }

    }

    /**
     * @return a clone of the list containing all EUREKA keys
     */
    public static ArrayList<String> getKeys(){
        return (ArrayList) keys.clone();
    }

    public static int getMaxValue(String key){
        if (!keys.contains(key))
            return 0;
        return entries.get(key).maxValue;
    }

    public static int getIncrement(String key){
        if (!keys.contains(key))
            return 0;
        return entries.get(key).increment;
    }

	public static ItemStack getDisplayStack(String key){
		if (!keys.contains(key))
			return null;
		return entries.get(key).stack;
	}

	public static class EurekaEntry{
		public String category;
		public int increment, maxValue;
		public ItemStack stack;

		public EurekaEntry(String category, int increment, int maxValue, ItemStack stack){
			this.category = category;
			this.increment = increment;
			this.maxValue = maxValue;
			this.stack = stack;
		}
	}
}
