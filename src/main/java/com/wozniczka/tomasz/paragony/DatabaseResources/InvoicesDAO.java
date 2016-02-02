package com.wozniczka.tomasz.paragony.DatabaseResources;

import com.wozniczka.tomasz.paragony.Invoice;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoicesDAO {

	private static final String TABLE_NAME = "invoices";
	private static final String PRODUCT_NAME_COLUMN = "product_name";
	private static final String PRODUCT_PRICE_COLUMN = "product_price";
	private static final String PURCHASE_DATE_COLUMN = "purchase_date";
	private static final String GUARANTEE_COLUMN = "guarantee_period";
	private static final String INVOICE_IMAGE_COLUMN = "invoice_image";
	private static final String ID_COLUMN = "id";

	private static PreparedStatement psInsert;
	private static PreparedStatement psUpdate;
	private final EmbeddedDatabaseConnection dbConnection;
	private final Connection connection;
	private final Statement statement;
	private ResultSet resultSet;


	public InvoicesDAO(EmbeddedDatabaseConnection dbConnection) {
		this.dbConnection = dbConnection;
		statement = dbConnection.getStatement();
		connection = dbConnection.getConnection();
		try {
			createTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		configureInsertStatement();
	}

	public void insertInvoiceToDb(Invoice invoice) throws SQLException {
		psInsert.setInt(1, 1);
		psInsert.setString(2, invoice.getProductName());
		psInsert.setInt(3, invoice.getProductPrice());
		psInsert.setInt(4, invoice.getGuaranteePeriod());
		psInsert.setBlob(5, convertImageToBlob(invoice.getInvoiceImage(), invoice.getImageFormat()));
		psInsert.setDate(6, convertJavaDateToSqlDate(invoice.getPurchaseDate()));
		psInsert.executeUpdate();

		connection.commit();

		System.out.println("New invoice added to DB");
	}

	public List<Invoice> selectAllInvoicesFormDB() throws SQLException {
		List<Invoice> invoices = new ArrayList<>();

		resultSet = statement.executeQuery(
				"SELECT " + ID_COLUMN + ", " +
						PRODUCT_NAME_COLUMN + ", " +
						PRODUCT_PRICE_COLUMN + ", " +
						GUARANTEE_COLUMN + ", " +
						INVOICE_IMAGE_COLUMN + ", " +
						PURCHASE_DATE_COLUMN +
						" FROM " + TABLE_NAME + " ORDER BY id"
		);

		while (resultSet.next()) {
			Invoice invoice = new Invoice();

			invoice.setProductName(resultSet.getString(PRODUCT_NAME_COLUMN));
			invoice.setProductPrice(resultSet.getInt(PRODUCT_PRICE_COLUMN));
			invoice.setGuaranteePeriod(resultSet.getInt(GUARANTEE_COLUMN));
			invoice.addInvoiceImage(convertBlobToBufferedImage(resultSet.getBlob(INVOICE_IMAGE_COLUMN)));
			invoice.setPurchaseDate(resultSet.getDate(PURCHASE_DATE_COLUMN).toString());

			invoices.add(invoice);
		}

		return invoices;

	}

	private void createTable() throws SQLException {

		statement.execute(
				"create table " + TABLE_NAME +
						"(" +
						ID_COLUMN + " int, " +
						PRODUCT_NAME_COLUMN + " varchar(100), " +
						PRODUCT_PRICE_COLUMN + " int, " +
						GUARANTEE_COLUMN + " int, " +
						INVOICE_IMAGE_COLUMN + " BLOB, " +
						PURCHASE_DATE_COLUMN + " date" +
						")"
		);
		System.out.println("Created table " + TABLE_NAME);
		connection.commit();

	}


	private void configureInsertStatement() {
		try {
			psInsert = connection.prepareStatement("insert into " + TABLE_NAME + " values (?, ?, ?, ?, ?, ?)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Date convertJavaDateToSqlDate(java.util.Date purchaseDate) {
		return new Date(purchaseDate.getTime());
	}

	private InputStream convertImageToBlob(BufferedImage image, String imageFormat) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, imageFormat, baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(baos.toByteArray());
	}

	private BufferedImage convertBlobToBufferedImage(Blob imageInBlobFormat) throws SQLException {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(imageInBlobFormat.getBinaryStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bufferedImage;
	}


}
