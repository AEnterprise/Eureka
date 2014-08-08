package eureka.core;

import eureka.client.gui.EurekaChapter;
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
    private static HashMap<String, EurekaEntry> chapters = new HashMap<String, EurekaEntry>(50);
	private static ArrayList<String> keys = new ArrayList<String>(50);
	private static ArrayList<String> categoriesList = new ArrayList<String>(20);
	private static HashMap<String, ItemStack> categories = new HashMap<String, ItemStack>(20);

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
		    Class<? extends EurekaChapter> chapterClass = information.newInstance().getGuiClass();
		    chapters.put(key, new EurekaEntry(category, increment, maxValue, stack, chapterClass));
		    keys.add(key);
	    } catch (Throwable e){
		    Logger.error("Error trying to register" + information.toString());
		    e.printStackTrace();
	    }

    }

	public static void registerCategory(String category, ItemStack stack){
		categoriesList.add(category);
		categories.put(category, stack);
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
        return chapters.get(key).maxValue;
    }

    public static int getIncrement(String key){
        if (!keys.contains(key))
            return 0;
        return chapters.get(key).increment;
    }

	public static ItemStack getDisplayStack(String key){
		if (!keys.contains(key))
			return null;
		return chapters.get(key).stack;
	}

	public static Class<? extends EurekaChapter> getChapterClass(String key){
		return chapters.get(key).chapterClass;
	}

	public static String getCategory(String key){
		return chapters.get(key).category;
	}

	public static ArrayList<String> getCategoriesList(){
		return (ArrayList) categoriesList.clone();
	}

	public static ItemStack getCategoryDisplayStack(String category){
		return categories.get(category);
	}

	public static class EurekaEntry{
		public String category;
		public int increment, maxValue;
		public ItemStack stack;
		public Class<? extends EurekaChapter> chapterClass;

		public EurekaEntry(String category, int increment, int maxValue, ItemStack stack, Class <? extends EurekaChapter> chapterClass){
			this.category = category;
			this.increment = increment;
			this.maxValue = maxValue;
			this.stack = stack;
			this.chapterClass = chapterClass;
		}
	}
}
