package eureka.api;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;


import eureka.api.client.gui.EurekaChapter;
import eureka.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EurekaInfo extends EurekaInformation {
	public String key, category;
	public int increment, maxValue;
	public ItemStack stack;
	public EurekaChapter gui;
	public ArrayList<String> requiredResearch;

	public EurekaInfo(String key, String category, int increment, int maxValue, ItemStack stack, EurekaChapter gui, String... researches) {
		this.key = key;
		this.category = category;
		this.increment = increment;
		this.maxValue = maxValue;
		this.stack = stack;
		this.gui = gui;
		requiredResearch = new ArrayList<String>(researches.length);
		for (String research : researches)
			requiredResearch.add(research);
	}

	public EurekaInfo(String key, String category, int increment, int maxValue, ItemStack stack, String... researches) {
		this.key = key;
		this.category = category;
		this.increment = increment;
		this.maxValue = maxValue;
		this.stack = stack;
		if (researches.length > 0) {
			boolean first = true;
			String requiredResearch = "";
			for (String research : researches) {
				if (first) {
					requiredResearch = Utils.localize("engineeringDiary." + research + ".title");
					first = false;
				} else {
					requiredResearch = requiredResearch + ", " + Utils.localize("engineeringDiary." + research + ".title");
				}
			}
			gui = new BasicEurekaChapter(key, requiredResearch, false);
		} else {
			gui = new BasicEurekaChapter(key);
		}
		requiredResearch = new ArrayList<String>(researches.length);
		for (String research : researches)
			requiredResearch.add(research);
	}

	public EurekaInfo(String key, String category, int increment, int maxValue, ItemStack stack, EurekaChapter gui) {
		this.key = key;
		this.category = category;
		this.increment = increment;
		this.maxValue = maxValue;
		this.stack = stack;
		this.gui = gui;
		this.requiredResearch = new ArrayList<String>();
	}

	public EurekaInfo(String key, String category, int increment, int maxValue, ItemStack stack) {
		this.key = key;
		this.category = category;
		this.increment = increment;
		this.maxValue = maxValue;
		this.stack = stack;
		gui = new BasicEurekaChapter(key);
		this.requiredResearch = new ArrayList<String>();
	}


	public EurekaInfo(String key, String category, int increment, int maxValue, ItemStack stack, boolean isInfoPage, String... researches) {
		this.key = key;
		this.category = category;
		this.increment = increment;
		this.maxValue = maxValue;
		this.stack = stack;
		if (researches.length > 0) {
			boolean first = true;
			String requiredResearch = "";
			for (String research : researches) {
				if (first) {
					requiredResearch = Utils.localize("engineeringDiary." + research + ".title");
					first = false;
				} else {
					requiredResearch.concat(", " + Utils.localize("engineeringDiary." + research + ".title"));
				}
			}
			gui = new BasicEurekaChapter(key, requiredResearch, isInfoPage);
		} else {
			gui = new BasicEurekaChapter(key);
		}
		requiredResearch = new ArrayList<String>(researches.length);
		for (String research : researches)
			requiredResearch.add(research);
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public int getIncrement() {
		return increment;
	}

	@Override
	public int getMaxValue() {
		return maxValue;
	}

	@Override
	public ItemStack getDisplayStack() {
		return stack;
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public EurekaChapter getGui() {
		return gui;
	}

	@Override
	public ArrayList<String> getRequiredResearch() {
		return requiredResearch;
	}
}
