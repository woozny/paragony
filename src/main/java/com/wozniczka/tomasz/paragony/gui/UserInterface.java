package com.wozniczka.tomasz.paragony.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface implements ActionListener {

	JFrame frame;
	JButton button;

	public static void main(String[] args) {
		UserInterface userInterface = new UserInterface();
		userInterface.prepareGui();
	}

	private void prepareGui() {
		frame = new JFrame();
		button = new JButton("click me");

		button.addActionListener(this);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().add(button);

		frame.setSize(300, 300);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		button.setText("ELO!");
	}
}
