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
			System.out.println("sleep 30 seconds");
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("step 3");
		// Notify the finalization of the System
		system.shutdown();

	}

}
