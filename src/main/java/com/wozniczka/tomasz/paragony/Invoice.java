package com.wozniczka.tomasz.paragony;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Invoice {
	public static final String DATE_FORMAT = "dd.mm.yyyy";
	private final DateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
	private String productName;
	private int productPrice;
	private int guaranteePeriod;
	private Date purchaseDate;
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

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String date) {
		try {
			purchaseDate = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public String getPurchaseDateAsString() {
		return format.format(purchaseDate);
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