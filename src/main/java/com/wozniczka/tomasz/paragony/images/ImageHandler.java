package com.wozniczka.tomasz.paragony.images;

import com.wozniczka.tomasz.paragony.Invoice;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHandler {
	//TODO: add support for other formats than JPG
	//TODO: printing
	public static void writeInvoiceImageToDisk(Invoice invoice, String writePath) {
		BufferedImage image = invoice.getInvoiceImage();
		String imageFormat = invoice.getImageFormat();

		File file = new File(writePath + File.separator + createImageFileName(invoice));
		try {
			ImageIO.write(image, imageFormat, file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String createImageFileName(Invoice i) {
		return i.getProductName() + "_" + i.getPurchaseDateAsString() + "." + i.getImageFormat();
	}
}
