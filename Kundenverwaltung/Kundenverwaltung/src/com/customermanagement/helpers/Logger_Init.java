package com.customermanagement.helpers;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


public class Logger_Init {

	// Init Log4j - Logger and Load configuration from xml file
	// Daily Rolling Appender - Each Day new LogFile
    private static Logger logger = null;
    
    private Logger_Init() {
        super();
    }
    
    public static Logger getInstance() {
        if(logger == null) { 
            initLogger();
        }
    return logger;
    }
    
    private static void initLogger() {
      // DOMConfigurator.configureAndWatch("log4j-4.xml", 60*1000);
       DOMConfigurator.configureAndWatch("log4j-4.xml", 60*1000); 
       
       logger = Logger.getLogger(Logger_Init.class);
    }	
}
