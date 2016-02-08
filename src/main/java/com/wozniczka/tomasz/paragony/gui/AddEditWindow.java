package com.wozniczka.tomasz.paragony.gui;

import com.wozniczka.tomasz.paragony.DatabaseResources.InvoicesDAO;
import com.wozniczka.tomasz.paragony.Invoice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddEditWindow {
	public static final int TEXT_FIELD_SIZE = 20;
	private final InvoicesDAO dao;
	private Invoice invoice;
	private JFrame frame;
	private JPanel nameRow;
	private JPanel priceRow;
	private JPanel purchaseRow;
	private JPanel guaranteeRow;
	private JPanel fileChooserRow;
	private JPanel buttonsRow;
	private JTextField nameTextField;
	private JTextField priceTextField;
	private JTextField purchaseTextField;
	private JTextField guaranteeTextField;
	private JButton fileChooserButton;
	private JFileChooser fileChooser;
	private String invoiceImagePath;

	public AddEditWindow(InvoicesDAO dao, Invoice invoice) {
		this.invoice = invoice;
		this.dao = dao;
		prepareAddEditWindow();
		prepareFields();
		prepareButtons();
		frame.setVisible(true);
	}

	private void prepareAddEditWindow() {
		frame = new JFrame();
		nameRow = new JPanel();
		priceRow = new JPanel();
		purchaseRow = new JPanel();
		guaranteeRow = new JPanel();
		buttonsRow = new JPanel();
		fileChooserRow = new JPanel();

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
		buttonsRow.setLayout(new BorderLayout());

		//add rows to frame
		frame.getContentPane().add(nameRow);
		frame.getContentPane().add(priceRow);
		frame.getContentPane().add(purchaseRow);
		frame.getContentPane().add(guaranteeRow);
		frame.getContentPane().add(fileChooserRow);
		frame.getContentPane().add(buttonsRow);

	}

	private void prepareFields() {
		//set fields properties
		JLabel nameLabel = new JLabel("Product name: ");
		JLabel priceLabel = new JLabel("Product price: ");
		JLabel purchaseLabel = new JLabel("Purchase date: ");
		JLabel guaranteeLabel = new JLabel("Guarantee period: ");
		JLabel fileChooserLabel = new JLabel("Choose invoice image: ");
		nameTextField = new JTextField(TEXT_FIELD_SIZE);
		priceTextField = new JTextField(TEXT_FIELD_SIZE);
		purchaseTextField = new JTextField(TEXT_FIELD_SIZE);
		guaranteeTextField = new JTextField(TEXT_FIELD_SIZE);
		fileChooser = new JFileChooser();
		fileChooserButton = new JButton("...");

		//add fields to window
		nameRow.add(nameLabel, BorderLayout.WEST);
		nameRow.add(nameTextField, BorderLayout.EAST);

		priceRow.add(priceLabel, BorderLayout.WEST);
		priceRow.add(priceTextField, BorderLayout.EAST);

		purchaseRow.add(purchaseLabel, BorderLayout.WEST);
		purchaseRow.add(purchaseTextField, BorderLayout.EAST);

		guaranteeRow.add(guaranteeLabel, BorderLayout.WEST);
		guaranteeRow.add(guaranteeTextField, BorderLayout.EAST);

		fileChooserRow.add(fileChooserLabel, BorderLayout.WEST);
		fileChooserRow.add(fileChooserButton, BorderLayout.EAST);

		fileChooserButton.addActionListener(new InvoiceImageSelector());
	}

	private void prepareButtons() {
		JButton addButton = new JButton("Add");
		JButton cancelButton = new JButton("Cancel");

		buttonsRow.add(addButton, BorderLayout.WEST);
		buttonsRow.add(cancelButton, BorderLayout.EAST);

		addButton.addActionListener(new InvoiceCreator());
		cancelButton.addActionListener(new FrameCloser());
	}

	private String simplifyPathIfToLong(String path) {
		if (path.length() > 15) {
			return "..." + path.substring(path.length() - 15);
		}
		return path;
	}

	private class InvoiceImageSelector implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int returnVal = fileChooser.showOpenDialog(frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				invoiceImagePath = fileChooser.getSelectedFile().toString();
				fileChooserButton.setText(simplifyPathIfToLong(invoiceImagePath));
			}
		}
	}

	private class InvoiceCreator implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Invoice newInvoice = new Invoice();
			newInvoice.setProductName(nameTextField.getText());
			newInvoice.setPurchaseDate(purchaseTextField.getText());
			newInvoice.setProductPrice(Integer.parseInt(priceTextField.getText()));
			newInvoice.setGuaranteePeriod(Integer.parseInt(guaranteeTextField.getText()));
			newInvoice.addInvoiceImage(invoiceImagePath);
			try {
				dao.insertInvoiceToDb(newInvoice);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			frame.dispose();
		}
	}

	private class FrameCloser implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
		}
	}


}
