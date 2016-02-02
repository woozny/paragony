package com.wozniczka.tomasz.paragony.gui;

import com.wozniczka.tomasz.paragony.Invoice;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow {

	JFrame frame;
	JList list;
	List<Invoice> allInvoices;

	public MainWindow(List<Invoice> invoices) {
		allInvoices = invoices;
		prepareMainWindow();
	}

	private void prepareMainWindow() {
		frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);

		prepareTable(allInvoices);

		frame.setVisible(true);
	}

	private void prepareTable(List<Invoice> invoices) {
		DefaultListModel invoicesList = new DefaultListModel();

		for (Invoice i : invoices) {
			invoicesList.addElement(i);
		}

		list = new JList(invoicesList);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);

		JScrollPane listScrollPane = new JScrollPane(list);

		frame.getContentPane().add(listScrollPane, BorderLayout.CENTER);


	}

}
