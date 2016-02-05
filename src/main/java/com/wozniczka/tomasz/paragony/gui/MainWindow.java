package com.wozniczka.tomasz.paragony.gui;

import com.wozniczka.tomasz.paragony.Invoice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class MainWindow {

	private static final String[] columnNames = {"Product name", "Price", "Purchase date", "Guarantee period"};
	private static final String CURRENCY = "PLN";
	private List<Invoice> allInvoices;
	private JFrame frame;
	private JTable table;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;


	public MainWindow(List<Invoice> invoices) {
		allInvoices = invoices;
		prepareMainWindow();
	}

	private void prepareMainWindow() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 300);
		frame.setTitle("Invoice Manager");

		prepareTable(allInvoices);
		prepareButtons();

		frame.setVisible(true);
	}

	private void prepareTable(List<Invoice> invoices) {
		Object[][] data = new Object[invoices.size()][columnNames.length];

		for (int i = 0; i < invoices.size(); i++) {
			data[i][0] = invoices.get(i).getProductName();
			data[i][1] = invoices.get(i).getProductPrice() + " " + CURRENCY;
			data[i][2] = invoices.get(i).getPurchaseDateAsString();
			data[i][3] = invoices.get(i).getGuaranteePeriod();
		}

		table = new JTable(data, columnNames);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(SINGLE_SELECTION);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
	}

	private void prepareButtons() {
		addButton = new JButton("Add");
		addButton.addActionListener(new AddButton());

		editButton = new JButton("Edit");
		editButton.addActionListener(new EditButton());

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteButton());

		frame.getContentPane().add(addButton, BorderLayout.SOUTH);
		//frame.getContentPane().add(editButton, BorderLayout.SOUTH);
		//frame.getContentPane().add(deleteButton, BorderLayout.SOUTH);
	}

	private class AddButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new AddEditWindow(new Invoice());
		}
	}

	private class EditButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	private class DeleteButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}


}
