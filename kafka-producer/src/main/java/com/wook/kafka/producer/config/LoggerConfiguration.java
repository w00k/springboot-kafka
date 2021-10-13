package com.wook.kafka.producer.config;

import org.apache.logging.log4j.LogManager;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

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

    private String getTransid() {
        String transid = transactionId.get(Thread.currentThread().getName());
        if(transid == null){
            transid = " ";
        }else{
            transid = "[" + transid + "] ";
        }
        return transid;
    }

    public void trace(Object msg) {
        String trazaLog = getTransid() + msg;
        LogManager.getLogger(getName()).trace(trazaLog);
    }

    public void debug(Object msg) {
        String trazaLog = getTransid() + msg;
        LogManager.getLogger(getName()).debug(trazaLog);
    }

    public void info(Object msg) {
        String trazaLog = getTransid() + msg;
        LogManager.getLogger(getName()).info(trazaLog);
    }

    public void warn(Object msg) {
        String trazaLog = getTransid() + msg;
        LogManager.getLogger(getName()).warn(trazaLog);
    }

    public void error(Object msg) {
        String trazaLog = getTransid() + msg;
        LogManager.getLogger(getName()).error(trazaLog);
    }

    public void error(Object msg, Throwable t) {
        String trazaLog = getTransid() + msg + " , " + t;
        LogManager.getLogger(getName()).error(trazaLog);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

