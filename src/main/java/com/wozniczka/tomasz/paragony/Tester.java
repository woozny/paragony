package com.wozniczka.tomasz.paragony;

import com.wozniczka.tomasz.paragony.DatabaseResources.EmbeddedDatabaseConnection;
import com.wozniczka.tomasz.paragony.DatabaseResources.InvoicesDAO;
import com.wozniczka.tomasz.paragony.gui.MainWindow;

import java.sql.SQLException;
import java.util.List;

public class Tester {

	public static final String INVOICE_IMAGE_PATH = "/home/tomek/IdeaProjects/paragony/src/test/TestResources/427572.jpg";
	public static final EmbeddedDatabaseConnection DB_CONNECTION = new EmbeddedDatabaseConnection();
	private List<Invoice> invoices;
	private InvoicesDAO dao;
	private MainWindow mainWindow;


	Tester() {
		dao = new InvoicesDAO(DB_CONNECTION);
		try {
			invoices = dao.selectAllInvoicesFormDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mainWindow = new MainWindow(invoices);

	}

	public static void main(String[] args) {
		Tester tester = new Tester();
		Invoice i = new Invoice();
		Invoice i2 = new Invoice();

		i.setProductName("Sluchawki");
		i.setGuaranteePeriod(2);
		i.setProductPrice(400);
		i.setPurchaseDate("2015-01-10");
		i.addInvoiceImage(INVOICE_IMAGE_PATH);

		i2.setProductName("Kebab");
		i2.setGuaranteePeriod(1);
		i2.setProductPrice(100);
		i2.setPurchaseDate("2016-03-10");
		i2.addInvoiceImage(INVOICE_IMAGE_PATH);


		try {
			tester.dao.insertInvoiceToDb(i);
			tester.dao.insertInvoiceToDb(i2);
			DB_CONNECTION.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
}
