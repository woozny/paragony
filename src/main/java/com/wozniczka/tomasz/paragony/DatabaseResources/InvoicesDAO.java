package com.wozniczka.tomasz.paragony.DatabaseResources;

import com.wozniczka.tomasz.paragony.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoicesDAO {
	private static PreparedStatement psInsert;
	private static PreparedStatement psUpdate;
	private static PreparedStatement psSelectAll;
	private final EmbeddedDatabaseConnection dbConnection;
	private final Connection connection;
	private final Statement statement;
	private ResultSet resultSet;


	public InvoicesDAO(EmbeddedDatabaseConnection dbConnection) {
		this.dbConnection = dbConnection;
		statement = dbConnection.getStatement();
		connection = dbConnection.getConnection();
		configureInsertStatement();
	}

	public void insertInvoiceToDb(Invoice invoice) throws SQLException {
		psInsert.setInt(1, 1);
		psInsert.setString(2, invoice.getProductName());
		psInsert.setInt(3, invoice.getProductPrice());
		psInsert.setInt(4, invoice.getGuaranteePeriod());
		psInsert.setDate(5, convertJavaDateToSqlDate(invoice.getPurchaseDate()));
		psInsert.executeUpdate();

		connection.commit();

		System.out.println("New invoice added to DB");
	}

	public void selectAllInvoicesFormDB() throws SQLException {
		List<Invoice> invoices = new ArrayList<>();

		resultSet = statement.executeQuery(
				"SELECT num, " +
						EmbeddedDatabaseConnection.getProductNameColumn() + ", " +
						EmbeddedDatabaseConnection.getProductPriceColumn() + ", " +
						EmbeddedDatabaseConnection.getGuaranteeColumn() +
						EmbeddedDatabaseConnection.getPurchaseDateColumnName() +
						" FROM location ORDER BY num"
		);

		while (resultSet.next()) {
			Invoice invoice = new Invoice();

			invoice.setProductName(EmbeddedDatabaseConnection.getProductNameColumn());
			invoice.setProductName(EmbeddedDatabaseConnection.getProductPriceColumn());
			invoice.setProductName(EmbeddedDatabaseConnection.getGuaranteeColumn());
			//invoice.setProductName(EmbeddedDatabaseConnection.getPurchaseDateColumnName());

			invoices.add(invoice);
		}

	}

	private void configureInsertStatement() {
		try {
			psInsert = connection.prepareStatement("insert into " + EmbeddedDatabaseConnection.getTableName() + " values (?, ?, ?, ?)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Date convertJavaDateToSqlDate(java.util.Date purchaseDate) {
		return new Date(purchaseDate.getTime());
	}


}
