package com.wozniczka.tomasz.paragony;

import com.wozniczka.tomasz.paragony.DatabaseResources.EmbeddedDatabaseConnection;
import com.wozniczka.tomasz.paragony.DatabaseResources.InvoicesDAO;
import com.wozniczka.tomasz.paragony.images.ImageHandler;

import java.sql.SQLException;
import java.util.List;

public class Tester {

	public static final String INVOICE_IMAGE_PATH = "/home/tomek/IdeaProjects/paragony/src/test/TestResources/427572.jpg";

	public static void main(String[] args) {
		InvoicesDAO dao = new InvoicesDAO(new EmbeddedDatabaseConnection());
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
			dao.insertInvoiceToDb(i);
			dao.insertInvoiceToDb(i2);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			List<Invoice> invoices = dao.selectAllInvoicesFormDB();
			for (Invoice inv : invoices) {
				System.out.println(inv.getProductName());
				System.out.println(inv.getProductPrice());
				System.out.println(inv.getGuaranteePeriod());
				System.out.println(inv.getPurchaseDateAsString());
				ImageHandler.writeInvoiceImageToDisk(inv, "/home/tomek/");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
}
