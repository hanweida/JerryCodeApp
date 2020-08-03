package com.jerry.util.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogWriter {

	private static final Logger sysLogger = LoggerFactory.getLogger("sysLogger");

    public static Logger getSysLogger() {
        return sysLogger;
    }

}
