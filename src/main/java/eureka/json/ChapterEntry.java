package eureka.json;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of LGPLv3
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ChapterEntry {
	public String name;
	public String category;
	public String displaystackModID;
	public String displaystackName;
	public String displaystackType;
	public int maxProgress;
	public String[] dropsModIDs;
	public String[] dropsStackName;
	public String[] dropsStackType;
	public int[] dropsAmount;
	public String linkedObjectModID;
	public String linkedObjectStackName;
	public String linkedObjectStackType;
	public String progressType;
	public String progressObjectModID;
	public String progressObjectStackName;
	public String progressObjectType;
	public String BCPipeType;
	public String[] requiredResearch;

	public ChapterEntry(){

	}

	public ChapterEntry(String name, String category, String displaystackModID, String displaystackName, String displaystackType, int maxProgress, String[] dropsModIDs, String[] dropsStackName, String[] dropsStackType, int[] dropsAmount, String linkedObjectModID, String linkedObjectStackName, String linkedObjectStackType, String progressType, String progressObjectModID, String progressObjectStackName, String progressObjectType, String BCPipeType, String[] requiredResearch) {
		this.name = name;
		this.category = category;
		this.displaystackModID = displaystackModID;
		this.displaystackName = displaystackName;
		this.displaystackType = displaystackType;
		this.maxProgress = maxProgress;
		this.dropsModIDs = dropsModIDs;
		this.dropsStackName = dropsStackName;
		this.dropsStackType = dropsStackType;
		this.dropsAmount = dropsAmount;
		this.linkedObjectModID = linkedObjectModID;
		this.linkedObjectStackName = linkedObjectStackName;
		this.linkedObjectStackType = linkedObjectStackType;
		this.progressType = progressType;
		this.progressObjectModID = progressObjectModID;
		this.progressObjectStackName = progressObjectStackName;
		this.progressObjectType = progressObjectType;
		this.BCPipeType = BCPipeType;
		this.requiredResearch = requiredResearch;
	}
}
