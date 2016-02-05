package com.wozniczka.tomasz.paragony.gui;

import com.wozniczka.tomasz.paragony.Invoice;

import javax.swing.*;

public class AddEditWindow {
	private Invoice invoice;
	private JFrame frame;

	public AddEditWindow(Invoice invoice) {
		this.invoice = invoice;
		prepareAddEditWindow();
	}

	private void prepareAddEditWindow() {
		frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(200, 200);
		frame.setTitle("Add new invoice");

		frame.setVisible(true);
	}
}
