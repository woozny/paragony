package com.wozniczka.tomasz.paragony;

import com.wozniczka.tomasz.paragony.DatabaseResources.EmbeddedDatabaseConnection;
import com.wozniczka.tomasz.paragony.DatabaseResources.InvoicesDAO;

import java.sql.SQLException;
import java.util.List;

public class Tester {
	public static void main(String[] args) {
		InvoicesDAO dao = new InvoicesDAO(new EmbeddedDatabaseConnection());
		Invoice i = new Invoice();

		i.setProductName("Sluchawki");
		i.setGuaranteePeriod(2);
		i.setProductPrice(400);
		i.addInvoiceImage("/home/tomek/IdeaProjects/paragony/src/test/TestResources/427572.jpg");

		try {
			dao.insertInvoiceToDb(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			List<Invoice> invoices = dao.selectAllInvoicesFormDB();
			for (Invoice inv : invoices) {
				System.out.println(inv.getProductName());
				System.out.println(inv.getProductPrice());
				System.out.println(inv.getGuaranteePeriod());
				inv.writeInvoiceImageToDisk("/home/tomek/plik");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
}
