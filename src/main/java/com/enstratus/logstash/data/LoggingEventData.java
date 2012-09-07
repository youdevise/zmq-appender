//package com.enstratus.logstash.data;
//
//import ch.qos.logback.classic.Level;
//import ch.qos.logback.classic.spi.ILoggingEvent;
//import ch.qos.logback.classic.spi.LoggingEvent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
////import org.apache.log4j.Level;
////import org.apache.log4j.Logger;
////import org.apache.log4j.spi.LocationInfo;
////import org.apache.log4j.spi.LoggingEvent;
////import org.apache.log4j.spi.ThrowableInformation;
//
//public class LoggingEventData {
//    public String fqn;
//    public String log;
//    public long time;
//    public String level;
//    public String msg;
//    public String thread;
//    public List<String> thro;
//    public String ndc;
//    public LocationInfoData info;
//
//    @SuppressWarnings("rawtypes")
//    public Map mdc;
//
//    public LoggingEventData() {
//        super();
//    }
//
//    public LoggingEventData(final ILoggingEvent event) {
//        this();
//        this.fqn = "Not Implemented";//event.getFQNOfLoggerClass();
//        this.log = event.getLoggerName();
//        this.time = event.getTimeStamp();
//        this.level = event.getLevel().toString();
//        this.msg = event.getMessage();
//        this.thread = event.getThreadName();
////        final ThrowableInformation throwableInformation = event.getThrowableProxy();
////        if (null != throwableInformation) {
////            this.thro = Arrays.asList(throwableInformation.getThrowableStrRep());
////        }
//        this.ndc = event.getNDC();
//
//        this.info = new LocationInfoData(event.getLocationInformation());
//        this.mdc = event.getProperties();
//    }
//
//    public ILoggingEvent toLoggingEvent() {
//        Logger logger = LoggerFactory.getLogger(log);
//        ThrowableInformation throwableInformation = null;
//        LocationInfo locationInfo = null;
//
//        if (null != thro) {
//            throwableInformation = new ThrowableInformation(thro.toArray(new String[0]));
//        }
//
//        if (null != info) {
//            locationInfo = info.toLocationInfo();
//        }
//
//        //return new ILoggingEvent(fqn, logger, time, Level.toLevel(level), msg, thread, throwableInformation, ndc, locationInfo, mdc);
//        return new LoggingEvent(fqn, logger, time, Level.toLevel(level), msg, thread, throwableInformation, ndc, locationInfo, mdc);
//    }
//}
