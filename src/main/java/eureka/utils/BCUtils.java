package eureka.utils;

import java.util.HashMap;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BCUtils {
	private static HashMap<String, String> BCKeys = new HashMap(50);

	public static void addBCKey(String unlocalizedname, String key){
		BCKeys.put(unlocalizedname, key);
	}

	public static String getBCKey(String unlocalizedname){
		if (BCKeys.containsKey(unlocalizedname))
			return BCKeys.get(unlocalizedname);
		else
			return unlocalizedname;
	}
}
