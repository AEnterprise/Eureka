package eureka.api.events;

import cpw.mods.fml.common.eventhandler.Event;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class UnauthorizedBlockEvent extends Event {
	public int x, y, z;
	public String key;

	public UnauthorizedBlockEvent(String key, int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
		this.key = key;
	}
}
