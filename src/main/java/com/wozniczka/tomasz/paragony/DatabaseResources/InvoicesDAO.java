package com.wozniczka.tomasz.paragony.DatabaseResources;

import com.wozniczka.tomasz.paragony.Invoice;

import java.sql.*;

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

	public static void insertInvoiceToDb(Invoice invoice) throws SQLException {
		psInsert.setInt(1, 1);
		psInsert.setString(2, invoice.getProductName());
		psInsert.setInt(3, invoice.getProductPrice());
		psInsert.setInt(4, invoice.getGuaranteePeriod());
		psInsert.executeUpdate();

		System.out.println("New invoice added to DB");
	}

	public void selectAllInvoicesFormDB() throws SQLException {
		resultSet = statement.executeQuery(
				"SELECT num, " +
						EmbeddedDatabaseConnection.getProductNameColumn() + ", " +
						EmbeddedDatabaseConnection.getProductPriceColumn() + ", " +
						EmbeddedDatabaseConnection.getGuaranteeColumn() +
						" FROM location ORDER BY num"
		);
	}

	private void configureInsertStatement() {
		try {
			psInsert = connection.prepareStatement("insert into " + EmbeddedDatabaseConnection.getTableName() + " values (?, ?, ?, ?)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
