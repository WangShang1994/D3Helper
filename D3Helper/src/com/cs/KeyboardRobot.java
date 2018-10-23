/**
 * 
 */
package com.cs;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

/**
 * @author Donald.Wang
 *
 */
public class KeyboardRobot extends Thread {

	public static final int KEY_Q = KeyEvent.VK_Q;

	public static final int KEY_W = KeyEvent.VK_W;

	public static final int KEY_E = KeyEvent.VK_E;

	public static final int KEY_R = KeyEvent.VK_R;

	private boolean running = true;

	private int cd = 0;

	private int key = 0;

	private JTextField tf;

	public KeyboardRobot(int seconds, int key, JTextField tf) {
		this.cd = seconds;
		this.key = key;
		this.tf = tf;
	}

	private void pressAndReleaseKey(int key) throws AWTException {
		Robot r = new Robot();
		r.setAutoDelay(200);
		r.keyPress(key);
		r.keyRelease(key);
	}

	public int getKey() {
		return this.key;
	}

	public void stopRunning() {
		this.tf.setBackground(Color.WHITE);
		this.running = false;
	}

	@Override
	public void run() {
		while (this.running) {
			try {
				this.tf.setBackground(Color.WHITE);
				for (int i = 0; i < this.cd; i++) {
					this.tf.setText(Integer.toString(this.cd - i));
					Thread.sleep(1000);
				}
				if (!this.running) {
					this.tf.setBackground(Color.WHITE);
					this.tf.setText(Integer.toString(this.cd));
					return;
				}
				this.tf.setText(Integer.toString(this.cd));
				this.tf.setBackground(Color.RED);
				pressAndReleaseKey(this.key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}

class Task implements Runnable {

	@Override
	public void run() {

	}

}
