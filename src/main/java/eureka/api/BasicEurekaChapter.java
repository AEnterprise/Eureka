package eureka.api;

import eureka.api.client.gui.EurekaChapter;
import eureka.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BasicEurekaChapter extends EurekaChapter{
	public String key, requiredResearch;
	public boolean isInfoPage;

	public BasicEurekaChapter(String key){
		this.key = key;
		requiredResearch = Utils.localize("engineeringDiary.noRequiredResearch");
	}

	public BasicEurekaChapter(String key, String requiredResearch, boolean isInfoPage){
		this.key = key;
		if (requiredResearch == null || requiredResearch == "")
			requiredResearch = Utils.localize("engineeringDiary.noRequiredResearch");
		this.requiredResearch = requiredResearch;
		this.isInfoPage = isInfoPage;
	}

	@Override
	public String getText(int page) {
		return Utils.localize("engineeringDiary." + key + ".page" + page);
	}

	@Override
	public void drawCustomStuff(int page) {

	}

	@Override
	public boolean hasNextPage(int page) {
		return !(Utils.localize("engineeringDiary." + key + ".page" + (page + 1)).equals("engineeringDiary." + key + ".page" + Integer.toString(page + 1)));
	}

	@Override
	public String getRequiredResearch() {
		return requiredResearch;
	}

	@Override
	public String howToMakeProgress() {
		return Utils.localize("engineeringDiary." + key + ".howToMakeProgress");
	}

	@Override
	public boolean isInfoPage() {
		return isInfoPage;
	}
}
