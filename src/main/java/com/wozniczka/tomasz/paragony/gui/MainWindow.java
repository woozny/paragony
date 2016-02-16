package com.wozniczka.tomasz.paragony.gui;

import com.wozniczka.tomasz.paragony.DatabaseResources.InvoicesDAO;
import com.wozniczka.tomasz.paragony.GuaranteeHandler;
import com.wozniczka.tomasz.paragony.Invoice;
import com.wozniczka.tomasz.paragony.images.ImageHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class MainWindow {

	private static final String[] columnNames = {"Product name", "Price", "Purchase date", "Guarantee period"};
	private static final String CURRENCY = "PLN";
	private final InvoicesDAO dao;
	private List<Invoice> allInvoices;
	private JFrame frame;
	private JTabbedPane tabs;
	private JTable invoicesTable;
	private JTable alertsTable;
	private JPanel buttonsRow;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	private JButton downloadButton;
	private JButton printButton;

	private MainWindow mainWindow;
	private JScrollPane scrollPane;
	private List<Invoice> invoicesWithInvalidGuarantee;


	public MainWindow(InvoicesDAO dao) {
		this.dao = dao;
		try {
			getAllInvoicesFromDb(dao);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		prepareMainWindow();
		mainWindow = this;
	}

	public void refreshData() {
		try {
			getAllInvoicesFromDb(dao);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tabs.removeAll();
		prepareInvoiceTable(allInvoices);
		prepareAlertTable(allInvoices);
		frame.revalidate();
	}

	private void getAllInvoicesFromDb(InvoicesDAO dao) throws SQLException {
		allInvoices = dao.selectAllInvoicesFormDB();
	}

	private void prepareMainWindow() {
		frame = new JFrame();
		tabs = new JTabbedPane();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 300);
		frame.setTitle("Invoice Manager");
		buttonsRow = new JPanel();

		frame.getContentPane().add(tabs, BorderLayout.CENTER);
		prepareInvoiceTable(allInvoices);
		prepareAlertTable(allInvoices);
		prepareButtons();

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dao.dbConnection.closeConnection();
				System.exit(0);
			}
		});

		frame.setVisible(true);
	}

	private void prepareAlertTable(List<Invoice> invoices) {
		invoicesWithInvalidGuarantee = new ArrayList<>();
		for (Invoice i : invoices) {
			if (!GuaranteeHandler.isGuaranteeValid(i)) invoicesWithInvalidGuarantee.add(i);
		}

		Object[][] data = new Object[invoicesWithInvalidGuarantee.size()][columnNames.length];

		for (int i = 0; i < invoicesWithInvalidGuarantee.size(); i++) {
			data[i][0] = invoicesWithInvalidGuarantee.get(i).getProductName();
			data[i][1] = invoicesWithInvalidGuarantee.get(i).getProductPrice() + " " + CURRENCY;
			data[i][2] = invoicesWithInvalidGuarantee.get(i).getPurchaseDateAsString();
			data[i][3] = invoicesWithInvalidGuarantee.get(i).getGuaranteePeriod();
		}

		alertsTable = new JTable(data, columnNames);

		scrollPane = new JScrollPane(alertsTable);
		alertsTable.setFillsViewportHeight(true);
		alertsTable.setSelectionMode(SINGLE_SELECTION);

		tabs.add("Alerts", scrollPane);
		setAlertStatus();
	}

	private void prepareInvoiceTable(List<Invoice> invoices) {
		Object[][] data = new Object[invoices.size()][columnNames.length];

		for (int i = 0; i < invoices.size(); i++) {
			data[i][0] = invoices.get(i).getProductName();
			data[i][1] = invoices.get(i).getProductPrice() + " " + CURRENCY;
			data[i][2] = invoices.get(i).getPurchaseDateAsString();
			data[i][3] = invoices.get(i).getGuaranteePeriod();
		}

		invoicesTable = new JTable(data, columnNames);

		scrollPane = new JScrollPane(invoicesTable);
		invoicesTable.setFillsViewportHeight(true);
		invoicesTable.setSelectionMode(SINGLE_SELECTION);


		tabs.addTab("Invoices", scrollPane);

	}

	private void prepareButtons() {
		frame.getContentPane().add(buttonsRow, BorderLayout.SOUTH);

		addButton = new JButton("Add");
		addButton.addActionListener(new AddButton());

		editButton = new JButton("Edit");
		editButton.addActionListener(new EditButton());

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteButton());

		downloadButton = new JButton("Download");
		downloadButton.addActionListener(new DownloadButton());

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteButton());

		printButton = new JButton("Print");

		buttonsRow.add(addButton);
		buttonsRow.add(editButton);
		buttonsRow.add(deleteButton);
		buttonsRow.add(downloadButton);
		buttonsRow.add(printButton);
	}

	private Invoice selectInvoiceForEditing() {
		Invoice selectedInvoice;
		int activeTab = tabs.getSelectedIndex();

		if (activeTab == 0) {
			try {
				selectedInvoice = allInvoices.get(invoicesTable.getSelectedRow());
				return selectedInvoice;
			} catch (ArrayIndexOutOfBoundsException e) {
				showSelectionErrorPopup();
				return null;
			}
		} else {
			try {
				selectedInvoice = invoicesWithInvalidGuarantee.get(alertsTable.getSelectedRow());
				return selectedInvoice;
			} catch (ArrayIndexOutOfBoundsException e) {
				showSelectionErrorPopup();
				return null;
			}

		}

	}

	private void showSelectionErrorPopup() {
		JOptionPane.showMessageDialog(frame,
				"Invoice has been not selected",
				"Selection Error",
				JOptionPane.ERROR_MESSAGE);
	}

	private void setAlertStatus() {
		int amountOfInvalidInvoices = invoicesWithInvalidGuarantee.size();
		String invoiceText = (amountOfInvalidInvoices <= 1) ? "invoice" : "invoices";

		if (amountOfInvalidInvoices > 0) {
			tabs.setTitleAt(1, "You have " + amountOfInvalidInvoices + " outdated " + invoiceText);
		}
	}

	private class AddButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new AddEditWindow(mainWindow, dao);
		}
	}

	private class EditButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Invoice selectedInvoice = selectInvoiceForEditing();
			if (selectedInvoice != null) new AddEditWindow(mainWindow, dao, selectedInvoice);

		}
	}

	private class DeleteButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Invoice selectedInvoice = selectInvoiceForEditing();
			String productName = (selectedInvoice != null) ? selectedInvoice.getProductName() : "unknown";
			Object[] options = {"Yes", "No"};
			int answer;
			if (selectedInvoice != null) {
				answer = JOptionPane.showOptionDialog(
						frame,
						"Do you really want to delete " + productName + " invoice?",
						"Delete " + productName + " invoice",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						1
				);
			} else {
				answer = 1;
			}
			try {
				if (answer == 0) {
					dao.deleteInvoiceFromDb(selectedInvoice);
					refreshData();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
	}

	private class DownloadButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String writePath;
			JFileChooser fileChooser = new JFileChooser();
			Invoice selectedInvoice = selectInvoiceForEditing();

			if (selectedInvoice != null) {
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fileChooser.showSaveDialog(frame);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					writePath = fileChooser.getSelectedFile().toString();
					ImageHandler.writeInvoiceImageToDisk(selectedInvoice, writePath);
				}
			}

		}
	}
}
