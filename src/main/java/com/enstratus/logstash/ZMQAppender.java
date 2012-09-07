package com.enstratus.logstash;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.enstratus.logstash.data.LoggingEventData;
import com.enstratus.logstash.layouts.JSONMessage;
import com.enstratus.logstash.layouts.LogstashMessage;
import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

import java.io.IOException;

public class ZMQAppender extends AppenderBase<ILoggingEvent> {
    private static final String PUBSUB = "pub";
    private static final String PUSHPULL = "push";
    private static final String CONNECTMODE = "connect";
    private static final String BINDMODE = "bind";
    private static final String JSONFORMAT = "json";
    private static final String JSONEVENTFORMAT = "json_event";

    private Socket socket;
    private final Gson gson = new Gson();

    private PatternLayoutEncoder encoder;


    // ZMQ specific "stuff"
    private int threads;
    private int hwm;
    private String endpoint;
    private String mode;
    private String socketType;
    private String topic;
    private String identity;
    private boolean blocking;

    // Ancillary settings
    private String tags;
    private String eventFormat = "json_event";


    public ZMQAppender() {
        super();
    }

    public ZMQAppender(final Socket socket) {
        this();
        this.socket = socket;
    }

    @Override
    public void start() {
        if (this.encoder == null) {
            addError("No layout set for the appender named [" + name + "].");
            return;
        }
        super.start();


        final ZMQ.Context context = ZMQ.context(threads);
        Socket sender;

        if (PUBSUB.equals(socketType)) {
            LogLog.debug("Setting socket type to PUB");
            sender = context.socket(ZMQ.PUB);
        } else if (PUSHPULL.equals(socketType)) {
            LogLog.debug("Setting socket type to PUSH");
            sender = context.socket(ZMQ.PUSH);
        } else {
            LogLog.debug("Setting socket type to default PUB");
            sender = context.socket(ZMQ.PUB);
        }
        sender.setLinger(1);

        final Socket socket = sender;

        final String[] endpoints = endpoint.split(",");

        for (String ep : endpoints) {

            if (BINDMODE.equals(mode)) {
                LogLog.debug("Binding socket to " + ep);
                socket.bind(ep);
            } else if (CONNECTMODE.equals(mode)) {
                LogLog.debug("Connecting socket to " + ep);
                socket.connect(ep);
            } else {
                LogLog.debug("Default connecting socket to " + ep);
                socket.connect(ep);
            }
        }

        if (identity != null) {
            socket.setIdentity(identity.getBytes());
        }

        this.socket = socket;

    }

    @Override
    protected void append(ILoggingEvent loggingevent) {
        // note that AppenderBase.doAppend will invoke this method only if
        // this appender was successfully started.

        String logLine = "";

        try {
            this.encoder.doEncode(loggingevent);
        } catch (IOException e) {
            // we can't do much with the exception except halting
            super.stop();
            addError("Failed to write to the console");
        }

        System.out.println(logLine);

        //code from old log4j implmentation
        final LoggingEventData data = new LoggingEventData(loggingevent);
        String messageFormat = getEventFormat();

        String identifier = getIdentity();
        String[] tagz;
        if (!(null == tags)) {
            tagz = getTags().split(",");
        } else {
            tagz = null;
        }

        if (JSONFORMAT.equals(messageFormat)) {
            JSONMessage message = new JSONMessage(data, identifier, tagz);
            logLine = message.toJson();
        } else if (JSONEVENTFORMAT.equals(messageFormat)) {
            LogstashMessage message = new LogstashMessage(data, identifier, tagz);
            logLine = message.toJson();
        }
        if ((topic != null) && (PUBSUB.equals(socketType))) {
            socket.send(topic.getBytes(), ZMQ.SNDMORE);
        }

        socket.send(logLine.getBytes(), blocking ? 0 : ZMQ.NOBLOCK);

    }

    public PatternLayoutEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(PatternLayoutEncoder encoder) {
        this.encoder = encoder;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(final int threads) {
        this.threads = threads;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(final String endpoint) {
        this.endpoint = endpoint;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(final String topic) {
        this.topic = topic;
    }

    public String getSocketType() {
        return socketType;
    }

    public void setSocketType(final String socketType) {
        this.socketType = socketType;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(final String mode) {
        this.mode = mode;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(final String identity) {
        this.identity = identity;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(final String tags) {
        this.tags = tags;
    }

    public String getEventFormat() {
        return eventFormat;
    }

    public void setEventFormat(final String eventFormat) {
        this.eventFormat = eventFormat;
    }
}