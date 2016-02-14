package com.wozniczka.tomasz.paragony.gui;

import com.wozniczka.tomasz.paragony.DatabaseResources.InvoicesDAO;
import com.wozniczka.tomasz.paragony.Invoice;
import com.wozniczka.tomasz.paragony.images.ImageHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class MainWindow {

	private static final String[] columnNames = {"Product name", "Price", "Purchase date", "Guarantee period"};
	private static final String CURRENCY = "PLN";
	private final InvoicesDAO dao;
	private List<Invoice> allInvoices;
	private JFrame frame;
	private JTabbedPane tabs;
	private JTable table;
	private JPanel buttonsRow;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	private JButton downloadButton;
	private JButton printButton;

	private MainWindow mainWindow;
	private JScrollPane scrollPane;


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
		prepareTable(allInvoices);
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
		prepareTable(allInvoices);
		prepareButtons();

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dao.dbConnection.closeConnection();
				System.exit(0);
			}
		});

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

		//if (scrollPane != null) frame.getContentPane().remove(scrollPane);

		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(SINGLE_SELECTION);

		tabs.removeAll();

		tabs.addTab("Invoices", scrollPane);
		tabs.add("Alerts", null);

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
		try {
			selectedInvoice = allInvoices.get(table.getSelectedRow());
			return selectedInvoice;
		} catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(frame,
					"Invoice has been not selected",
					"Selection Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
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
