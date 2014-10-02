package eureka.api.client.gui;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of LGPLv3
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class EurekaChapter {

	public abstract String getText(int page);

	public abstract void drawCustomStuff(int page);

	public abstract boolean hasNextPage(int page);

	public abstract String getRequiredResearch();

	public abstract String howToMakeProgress();

	public abstract boolean isInfoPage();
}
