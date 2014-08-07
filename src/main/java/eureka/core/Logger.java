package eureka.core;


import org.apache.logging.log4j.LogManager;

/**
 * Copyright (c) 2014, AEnterprise
 * Eureka is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftAdditions.wordpress.com/wiki/licensing-stuff/
 */
public class Logger {

    public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger("Eureka");

    public static void info(String message) { logger.info(message); }

    public static void error(String message) { logger.error(message); }

}
