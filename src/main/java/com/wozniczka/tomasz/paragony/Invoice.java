package com.wozniczka.tomasz.paragony;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Invoice {
	private String productName;
	private int productPrice;
	private int guaranteePeriod;
	private BufferedImage invoiceImage;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String name) {
		productName = name;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getGuaranteePeriod() {
		return guaranteePeriod;
	}

	public void setGuaranteePeriod(int guaranteePeriod) {
		this.guaranteePeriod = guaranteePeriod;
	}

	public BufferedImage getInvoiceImage() {
		return invoiceImage;
	}


	public void addInvoiceImage(String invoiceImagePath) {
		invoiceImage = loadInvoiceImage(invoiceImagePath);
	}

	private BufferedImage loadInvoiceImage(String imagePath) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}
