package eureka.core;


import org.apache.logging.log4j.LogManager;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class Logger {

    public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger("Eureka");

    public static void info(String message) { logger.info(message); }

    public static void error(String message) { logger.error(message); }

}
