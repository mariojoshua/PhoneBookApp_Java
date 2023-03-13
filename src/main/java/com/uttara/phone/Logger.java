package com.uttara.phone;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * This class is a threadsafe Singelton logger implementation with the ability to 
 * print the statements on console if required by changing debugMode value to true
 * 
 * @author mariojoshuaaugustine
 */

public class Logger {

	private static final Logger logger = null;
	private boolean debugMode = true;	
	private Path myPath = null;
	
	private Logger() {
		myPath = Paths.get("/Users/mariojoshuaaugustine/Desktop/tasklog.txt");
	}

	public static Logger getInstance() {
		return logger == null ? new Logger() : logger; 
	}

	public synchronized void log(String data) {
		new Thread (new Runnable() {
			@Override
			public void run() {
				
				try (BufferedWriter bufferedWriter = Files.newBufferedWriter(myPath)) {
					String message = new Date() + " : " + data;
					bufferedWriter.write(message);
					bufferedWriter.newLine();
					if (debugMode == true) {
						System.out.println(message);
					}		
				} catch (IOException e) {
					e.printStackTrace();
				}			
			}
		}).start();
	}

	










}
