package com.wozniczka.tomasz.paragony.images;

import com.wozniczka.tomasz.paragony.Invoice;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class InvoicePrinter implements Printable {

	private BufferedImage invoiceImage;

	public InvoicePrinter(Invoice invoice) {
		invoiceImage = invoice.getInvoiceImage();
	}


	public int print(Graphics g, PageFormat pf, int page) throws
			PrinterException {

		if (page > 0) { /* We have only one page, and 'page' is zero-based */
			return NO_SUCH_PAGE;
		}

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Now we perform our rendering */
		((Graphics2D) g).drawImage(invoiceImage, new AffineTransform(1f, 0f, 0f, 1f, 10, 10), null);

        /* tell the caller that this page is part of the printed document */
		return PAGE_EXISTS;
	}


}
