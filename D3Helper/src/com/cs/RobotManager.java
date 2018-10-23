/**
 * 
 */
package com.cs;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Donald.Wang
 *
 */
public class RobotManager {

	static Map<Integer, KeyboardRobot> threadPool = new HashMap<>();

	private RobotManager() {

	}

	public static void runTask(KeyboardRobot task) {
		task.start();
		threadPool.put(task.getKey(), task);
	}

	public static void stop(int key) {
		threadPool.get(key).stopRunning();
		threadPool.remove(key);
	}

}
