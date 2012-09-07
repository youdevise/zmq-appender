package com.enstratus.logstash.layouts;

import java.net.UnknownHostException;

public class HostData {
    private HostData(){
        //Prevent Concstruction
    }

    public static String getHostName() {
        try {
            return java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            //Nothing
        }
        return "unknown-host";
    }
}
