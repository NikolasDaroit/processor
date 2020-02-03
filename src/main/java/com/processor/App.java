package com.processor;

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
            monitor.MonitorDirectoryListener();
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e);
        }
        System.out.println( "Hello World!" );
    }
}
