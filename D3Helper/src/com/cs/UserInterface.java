/**
 * 
 */
package com.cs;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * @author Donald.Wang
 *
 */
public class UserInterface extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6584972333927594140L;

	private JCheckBox cbQ = new JCheckBox("Q");
	private JCheckBox cbW = new JCheckBox("W");
	private JCheckBox cbE = new JCheckBox("E");
	private JCheckBox cbR = new JCheckBox("R");

	private JTextField tfQ = new JTextField(2);
	private JTextField tfW = new JTextField(2);
	private JTextField tfE = new JTextField(2);
	private JTextField tfR = new JTextField(2);

	private static List<KeyboardRobot> selectKey = new ArrayList<>();

	private static List<JCheckBox> selectCkeckBox = new ArrayList<>();

	private JButton btnStart = new JButton("Start");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UserInterface u = new UserInterface();
		u.setUndecorated(true);
		u.setBackground(new Color(0, 0, 0, 0));
		u.setLayout(new FlowLayout());
		u.setSize(350, 35);
		u.setLocation(700, 0);
		u.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		u.addEventListener();
		u.addComponent();
		u.setAlwaysOnTop(true);
		u.setVisible(true);
	}

	private void addComponent() {
		super.add(tfQ);
		super.add(tfW);
		super.add(tfE);
		super.add(tfR);
		super.add(this.cbQ);
		super.add(this.cbW);
		super.add(this.cbE);
		super.add(this.cbR);
		super.add(this.btnStart);
	}

	private void addEventListener() {
		this.btnStart.addActionListener(new BtnListener(this.btnStart));
		this.btnStart.addActionListener(new CheckBoxListener(this.cbQ, this.tfQ, this.btnStart));
		this.btnStart.addActionListener(new CheckBoxListener(this.cbW, this.tfW, this.btnStart));
		this.btnStart.addActionListener(new CheckBoxListener(this.cbE, this.tfE, this.btnStart));
		this.btnStart.addActionListener(new CheckBoxListener(this.cbR, this.tfR, this.btnStart));
	}

	public static void addKey(KeyboardRobot key, JCheckBox cb) {
		selectCkeckBox.add(cb);
		selectKey.add(key);
	}

	public static void removeKey(KeyboardRobot key, JCheckBox cb) {
		selectCkeckBox.add(cb);
		selectKey.remove(key);
	}

	public static List<KeyboardRobot> getSelectKey() {
		return selectKey;
	}

	public static List<JCheckBox> getSelectCheckBox() {
		return selectCkeckBox;
	}

	public static void clear() {
		selectKey = new ArrayList<>();
		selectCkeckBox = new ArrayList<>();
	}

}

class BtnListener implements ActionListener {

	JButton btnStart;

	public BtnListener(JButton btnStart) {
		this.btnStart = btnStart;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<KeyboardRobot> keys = UserInterface.getSelectKey();
		List<JCheckBox> checkBoxs = UserInterface.getSelectCheckBox();
		if ("Start".equals(this.btnStart.getText())) {
			for (JCheckBox cb : checkBoxs) {
				cb.setEnabled(false);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			for (KeyboardRobot key : keys) {
				RobotManager.runTask(key);
			}
			this.btnStart.setText("Stop");
		} else {
			for (KeyboardRobot key : keys) {
				RobotManager.stop(key.getKey());
			}
			for (JCheckBox cb : checkBoxs) {
				cb.setEnabled(true);
			}
			UserInterface.clear();
			this.btnStart.setText("Start");
		}
	}

}

class CheckBoxListener implements ActionListener {
	JCheckBox cb = null;
	JTextField tf = null;
	Map<String, Integer> map = new HashMap<>();
	KeyboardRobot r = null;
	JButton btn;

	public CheckBoxListener(JCheckBox cb, JTextField tf, JButton btn) {
		map.put("Q", KeyboardRobot.KEY_Q);
		map.put("W", KeyboardRobot.KEY_W);
		map.put("E", KeyboardRobot.KEY_E);
		map.put("R", KeyboardRobot.KEY_R);
		this.cb = cb;
		this.tf = tf;
		this.btn = btn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.cb.isSelected() && "Start".equals(this.btn.getText())) {
			r = new KeyboardRobot(Integer.parseInt(tf.getText()), map.get(this.cb.getText()), this.tf);
			UserInterface.addKey(r, cb);
			this.tf.setEditable(false);
		} else {
			this.tf.setEditable(true);
		}
	}
}