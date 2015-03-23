package eureka.json;

import java.util.List;

import eureka.api.EnumProgressOptions;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class JSONChapter {
	public String name, displayStack, category, progressObject;
	public List<String> requiredResearch, blockedObjects;
	public int maxProgress;
	public EnumProgressOptions progressOption;
}
