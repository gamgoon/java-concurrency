package com.github.gamgoon.concurrency.ch04.second;

import java.util.concurrent.TimeUnit;

/**
 * Main class of the advanced example
 * @author author
 *
 */
public class AdvancedMain {

	public static void main(String args[]) {
		
		// Creates the System and execute it as a Thread
		NewsAdvancedSystem system = new NewsAdvancedSystem("data/sources.txt");

		Thread t = new Thread(system);

		t.start();

		// Wait 30 minutes
		try {
			TimeUnit.MINUTES.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Notify the finalization of the System
		system.shutdown();

	}

}