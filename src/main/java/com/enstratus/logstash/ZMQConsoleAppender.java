package com.enstratus.logstash;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.apache.commons.lang.StringUtils;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZMQConsoleAppender extends AppenderBase<ILoggingEvent> {

    private Socket socket;

    private PatternLayoutEncoder encoder;

    // ZMQ specific "stuff"
    private int threads;
    private String listenAddress;

    public ZMQConsoleAppender() {
        super();
    }

    public ZMQConsoleAppender(final Socket socket) {
        this();
        this.socket = socket;
    }

    protected void append(ILoggingEvent event) {
        List<String> topic = new ArrayList<String>();

        topic.add(event.getLevel().toString());
        //TODO topic.add(event.getLocationInformation().getClassName().toString());
        String formattedTopic = StringUtils.join(topic, '.');
        if (formattedTopic != null) {
            socket.send(formattedTopic.getBytes(), ZMQ.SNDMORE);
        }

        String logLine = "";
        try {
            this.encoder.doEncode(event);
        } catch (IOException e) {
            // we can't do much with the exception except halting
            super.stop();
            addError("Failed to write to the console");
        }
        socket.send(logLine.getBytes(), ZMQ.NOBLOCK);
    }

    @Override
    public void start() {
        if (this.encoder == null) {
            addError("No layout set for the appender named [" + name + "].");
            return;
        }
        super.start();

        final Context context = ZMQ.context(threads);
        Socket sender;

        sender = context.socket(ZMQ.PUB);
        sender.setLinger(1);

        final Socket socket = sender;

        socket.bind(listenAddress);
        this.socket = socket;
    }

    public PatternLayoutEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(PatternLayoutEncoder encoder) {
        this.encoder = encoder;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public String getListenAddress() {
        return listenAddress;
    }

    public void setListenAddress(String listenAddress) {
        this.listenAddress = listenAddress;
    }
}
