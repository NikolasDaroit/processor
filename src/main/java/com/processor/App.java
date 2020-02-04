package com.processor;

import java.io.IOException;

import com.processor.reader.MonitorDirectory;

/**
 * Hello world!
 *
 */
public class App 
{
    
    public static void main( String[] args )
    {
        MonitorDirectory   monitor = new MonitorDirectory();
        try {
            System.out.println("Sarting monitoring, waiting for file input");
            monitor.monitorDirectoryListener();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}
