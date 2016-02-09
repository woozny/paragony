package com.wozniczka.tomasz.paragony;

import com.wozniczka.tomasz.paragony.DatabaseResources.EmbeddedDatabaseConnection;
import com.wozniczka.tomasz.paragony.DatabaseResources.InvoicesDAO;
import com.wozniczka.tomasz.paragony.gui.MainWindow;

public class FrontendBackendIntegrator {
	private static InvoicesDAO DAO;

	public void runApp() {
		DAO = new InvoicesDAO(new EmbeddedDatabaseConnection());
		new MainWindow(DAO);
	}
}
