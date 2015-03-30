package eureka.api;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public enum EnumProgressOptions {
	CRAFTING(true),
	CRAFT_ANYTHING(false),
	BREAK_BLOCK(true),
	BREAK_ANY_BLOCK(false),
	PLACE_BLOCK(true),
	PLACE_ANY_BLOCK(false),
	ENDER_TELEPORT(false),
	KILL_ANYTHING(false),
	DIE(false),
	FILL_BUCKET(false);

	private final boolean hasArg;

	EnumProgressOptions(boolean hasArg) {
		this.hasArg = hasArg;
	}

	public boolean hasArg() {
		return hasArg;
	}
}
