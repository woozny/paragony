package com.wozniczka.tomasz.paragony;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Invoice {
	public static final String DATE_FORMAT = "yyyy-mm-dd";
	private final DateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
	private int id;
	private String productName;
	private BigDecimal productPrice;
	private int guaranteePeriod;
	private Date purchaseDate;
	private BufferedImage invoiceImage;
	private String imageFormat = "jpg";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (this.id == 0) this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String name) {
		productName = name;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
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
		setImageFormat(invoiceImagePath);
		invoiceImage = loadInvoiceImage(invoiceImagePath);
	}

	public void addInvoiceImage(BufferedImage image) {
		invoiceImage = image;
	}

	public String getImageFormat() {
		return imageFormat;
	}

	private void setImageFormat(String imagePath) {
		if (imagePath.contains(".")) {
			imageFormat = imagePath.substring(imagePath.lastIndexOf('.') + 1);
		} else {
			throw new IllegalArgumentException("Unable to find file extension in: " + imagePath);
		}
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
