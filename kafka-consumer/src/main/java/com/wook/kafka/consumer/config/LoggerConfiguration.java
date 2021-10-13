package com.wook.kafka.consumer.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;

public class LoggerConfiguration {

    private static Map<String, String> transactionId = new LinkedHashMap<String, String>();

    private static Map<String, LoggerConfiguration> logger = new LinkedHashMap<String, LoggerConfiguration>();

    private String name;

    public LoggerConfiguration() {
        super();
    }

    public LoggerConfiguration(String name) {
        super();
        this.name = name;
    }

    public static LoggerConfiguration getLogger(String name) {
        if (logger.containsKey(name)){
            return logger.get(name);
        }
        LoggerConfiguration newlog = new LoggerConfiguration(name);
        logger.put(name, newlog);
        return newlog;
    }

    public static LoggerConfiguration getLogger(Class<?> classname) {
        return getLogger(classname.toString());
    }

    public void setTransId(String transId) {
        if (transId == null) {
            transId = UUID.randomUUID().toString();
        }
        transactionId.put(Thread.currentThread().getName(), transId.trim());
    }

    public String getTransactionId() {
        return transactionId.get(Thread.currentThread().getName());
    }

    private String getTransid(Object msg) {
        String transid = getTransactionId();
        return (transid==null?" ":"[" + transid + "] ") + msg;
    }

    public void trace(Object msg) {
        LogManager.getLogger(getName()).trace(getTransid(msg));
    }

    public void debug(Object msg) {
        LogManager.getLogger(getName()).debug(getTransid(msg));
    }

    public void info(Object msg) {
        LogManager.getLogger(getName()).info(getTransid(msg));
    }

    public void warn(Object msg) {
        LogManager.getLogger(getName()).warn(getTransid(msg));
    }

    public void error(Object msg) {
        LogManager.getLogger(getName()).error(getTransid(msg));
    }

    public void error(Object msg, Throwable t) {
        LogManager.getLogger(getName()).error(getTransid(msg+ " , " + t));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

