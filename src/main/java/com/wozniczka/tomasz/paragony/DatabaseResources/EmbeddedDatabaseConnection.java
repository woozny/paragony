package com.wozniczka.tomasz.paragony.DatabaseResources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EmbeddedDatabaseConnection {
	private static final String DB_NAME = "derbyDB"; // the name of the database
	private static final String PROTOCOL = "jdbc:derby:";
	private Connection connection;
	private Statement statement;
	public EmbeddedDatabaseConnection() {
		connection = connectToDatabase();
	}

	public static void printSQLException(SQLException e) {
		// Unwraps the entire exception chain to unveil the real cause of the
		// Exception.
		while (e != null) {
			System.err.println("\n----- SQLException -----");
			System.err.println("  SQL State:  " + e.getSQLState());
			System.err.println("  Error Code: " + e.getErrorCode());
			System.err.println("  Message:    " + e.getMessage());
			// for stack traces, refer to derby.log or uncomment this:
			//e.printStackTrace(System.err);
			e = e.getNextException();
		}
	}

	public Connection connectToDatabase() {
		try {
			connection = DriverManager.getConnection(PROTOCOL + DB_NAME + ";create=true");
			// We want to control transactions manually. Autocommit is on by
			// default in JDBC.

			System.out.println("Connected to " + DB_NAME);

			connection.setAutoCommit(false);

			statement = connection.createStatement();


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void closeConnection() {

		try {
			// the shutdown=true attribute shuts down Derby
			DriverManager.getConnection("jdbc:derby:;shutdown=true");

			// To shut down a specific database only, but keep the
			// engine running (for example for connecting to other
			// databases), specify a database in the connection URL:
			//DriverManager.getConnection("jdbc:derby:" + dbName + ";shutdown=true");
		} catch (SQLException se) {
			if (((se.getErrorCode() == 50000)
					&& ("XJ015".equals(se.getSQLState())))) {
				// we got the expected exception
				System.out.println("Derby shut down normally");
				// Note that for single database shutdown, the expected
				// SQL state is "08006", and the error code is 45000.
			} else {
				// if the error code or SQLState is different, we have
				// an unexpected exception (shutdown failed)
				System.err.println("Derby did not shut down normally");
				printSQLException(se);
			}
		}
	}

	public Statement getStatement() {
		return statement;
	}

	public Connection getConnection() {
		return connection;
	}
}
