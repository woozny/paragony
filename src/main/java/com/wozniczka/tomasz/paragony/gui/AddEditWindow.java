package com.wozniczka.tomasz.paragony.gui;

import com.wozniczka.tomasz.paragony.Invoice;

import javax.swing.*;
import java.awt.*;

public class AddEditWindow {
	public static final int TEXT_FIELD_SIZE = 20;
	private Invoice invoice;
	private JFrame frame;
	private JPanel nameRow;
	private JPanel priceRow;
	private JPanel purchaseRow;
	private JPanel guaranteeRow;
	private JLabel nameLabel;
	private JLabel priceLabel;
	private JLabel purchaseLabel;
	private JLabel guaranteeLabel;
	private JTextField nameTextField;
	private JTextField priceTextField;
	private JTextField purchaseTextField;
	private JTextField guaranteeTextField;

	public AddEditWindow(Invoice invoice) {
		this.invoice = invoice;
		prepareAddEditWindow();
		prepareFields();
		frame.setVisible(true);
	}

	private void prepareAddEditWindow() {
		frame = new JFrame();
		nameRow = new JPanel();
		priceRow = new JPanel();
		purchaseRow = new JPanel();
		guaranteeRow = new JPanel();

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 200);
		frame.setTitle("Add new invoice");
		frame.setResizable(false);

		//set layouts
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		nameRow.setLayout(new BorderLayout());
		priceRow.setLayout(new BorderLayout());
		purchaseRow.setLayout(new BorderLayout());
		guaranteeRow.setLayout(new BorderLayout());

		//add rows to frame
		frame.getContentPane().add(nameRow);
		frame.getContentPane().add(priceRow);
		frame.getContentPane().add(purchaseRow);
		frame.getContentPane().add(guaranteeRow);

	}

	private void prepareFields() {
		//set fields properties
		nameLabel = new JLabel("Product name: ");
		priceLabel = new JLabel("Product price: ");
		purchaseLabel = new JLabel("Purchase date: ");
		guaranteeLabel = new JLabel("Guarantee period: ");
		nameTextField = new JTextField(TEXT_FIELD_SIZE);
		priceTextField = new JTextField(TEXT_FIELD_SIZE);
		purchaseTextField = new JTextField(TEXT_FIELD_SIZE);
		guaranteeTextField = new JTextField(TEXT_FIELD_SIZE);

		//add fields do window
		nameRow.add(nameLabel, BorderLayout.WEST);
		nameRow.add(nameTextField, BorderLayout.EAST);

		priceRow.add(priceLabel, BorderLayout.WEST);
		priceRow.add(priceTextField, BorderLayout.EAST);

		purchaseRow.add(purchaseLabel, BorderLayout.WEST);
		purchaseRow.add(purchaseTextField, BorderLayout.EAST);

		guaranteeRow.add(guaranteeLabel, BorderLayout.WEST);
		guaranteeRow.add(guaranteeTextField, BorderLayout.EAST);
	}
}
