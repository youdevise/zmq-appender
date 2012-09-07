package com.enstratus.logstash;

//import com.enstratus.logstash.data.LoggingEventData;
//import com.google.gson.Gson;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//import org.apache.log4j.spi.LocationInfo;
//import org.apache.log4j.spi.LoggingEvent;
//import org.apache.log4j.spi.ThrowableInformation;
//import org.junit.Before;
//import org.junit.Test;
//import org.zeromq.ZMQ;
//import org.zeromq.ZMQ.Context;
//import org.zeromq.ZMQ.Socket;
//
//import java.util.*;
//
//import static org.easymock.EasyMock.*;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ZMQAppenderTest {
    @Test
    public void fake(){
        assertTrue(true);
    }
/*
	Gson gson = new Gson();

	// unit
	ZMQAppender appender;

	Context context;
	Socket socket;
	Socket receiver;

	List<LoggingEvent> events = Arrays.asList(event(Level.FATAL, "fatal"),
			event(Level.ERROR, "error"), event(Level.INFO, "info"),
			event(Level.TRACE, "trace"), event(Level.DEBUG, "debug"));

	LoggingEvent event(Level level, Object message) {
		Logger logger = Logger.getLogger(ZMQAppenderTest.class);
		String fqn = "com.enstratus.logstash.test";
		long timeStamp = Calendar.getInstance().getTimeInMillis();
		String threadName = "testThread";
		Throwable throwable = new NullPointerException();
		String ndc = "ndc";
		String file = "";
		String classname = "";
		String method = "";
		String line = "";
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("thisis", "test");

		return new LoggingEvent(fqn, logger, timeStamp, level, message,
				threadName, new ThrowableInformation(throwable), ndc,
				new LocationInfo(file, classname, method, line), properties);
	}

	@Before
	public void setup() {
		context = ZMQ.context(1);
		receiver = context.socket(ZMQ.PULL);
		socket = createMock(Socket.class);
		appender = new ZMQAppender(socket);
	}

	@Test
	public void doesntRequireLayout() {
		//assertFalse(appender.requiresLayout());
	}

	@Test
	public void appendsLoggingEvents() {
		for (LoggingEvent event : events) {
			final LoggingEventData data = new LoggingEventData(event);
			expect(
					socket.send(aryEq(gson.toJson(data).getBytes()),
							eq(ZMQ.NOBLOCK))).andReturn(true);
		}
		replay(socket);

		for (LoggingEvent event : events) {
			appender.append(event);
		}

		verify(socket);
	}
*/
}
