package com.wozniczka.tomasz.paragony;

import com.wozniczka.tomasz.paragony.DatabaseResources.EmbeddedDatabaseConnection;
import com.wozniczka.tomasz.paragony.DatabaseResources.InvoicesDAO;
import com.wozniczka.tomasz.paragony.images.ImageHandler;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class IntegrationTests {

	private static final String INVOICE_IMAGE_PATH = "src/test/TestResources/427572.jpg";
	private List<Invoice> invoices;

	//TODO: clean this mess
	//TODO: split this huge test
	//TODO: add assertions
	@Test
	public void shouldIfDbIsWorkingAsExpected() {
		IntegrationTests t = new IntegrationTests();
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
			t.invoices = dao.selectAllInvoicesFormDB();
			for (Invoice inv : t.invoices) {
				System.out.println(inv.getProductName());
				System.out.println(inv.getProductPrice());
				System.out.println(inv.getGuaranteePeriod());
				System.out.println(inv.getPurchaseDateAsString());
				//TODO this should be cleaned automatically
				ImageHandler.writeInvoiceImageToDisk(inv, "src/test/");

			}
			System.out.println("------------------------");
			System.out.println("Updating");
			System.out.println("------------------------");
			//change something
			t.invoices.get(1).setProductName("Taczki");

			//update in db
			dao.updateInvoiceInDb(t.invoices.get(1));
			// reload objects
			t.invoices = dao.selectAllInvoicesFormDB();

			for (Invoice inv1 : t.invoices) {
				System.out.println(inv1.getProductName());
				System.out.println(inv1.getProductPrice());
				System.out.println(inv1.getGuaranteePeriod());
				System.out.println(inv1.getPurchaseDateAsString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
