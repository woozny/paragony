package com.wozniczka.tomasz.paragony;

import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class InvoiceTest {

	public static final String PURCHASE_DATE = "1016-10-01";
	private static final String HEADPHONES = "Headphones";
	private static final String INVOICE_IMAGE_PATH = "src/test/TestResources/427572.jpg";
	//private static final String INCORRECT_INVOICE_IMAGE_PATH = "test427572.jpg";
	private static final int PRICE = 400;
	private final BufferedImage sampleInvoiceImage = loadInvoiceImage(INVOICE_IMAGE_PATH);
	private Invoice invoice;

	@Before
	public void setUp() {
		invoice = new Invoice();
	}

	@Test
	public void shouldBeAbleToAddProductName() {
		invoice.setProductName(HEADPHONES);

		assertThat(invoice.getProductName()).isEqualTo(HEADPHONES);
	}

	@Test
	public void shouldBeAbleToAddProductPrice() {
		invoice.setProductPrice(PRICE);

		assertThat(invoice.getProductPrice()).isEqualTo(PRICE);
	}

	@Test
	public void shouldBeAbleToAddGuaranteePeriod() {
		invoice.setGuaranteePeriod(2);

		assertThat(invoice.getGuaranteePeriod()).isEqualTo(2);
	}

	@Test
	public void shouldBeAbleToAddScannedInvoice() {
		invoice.addInvoiceImage(INVOICE_IMAGE_PATH);

		assertThat(invoice.getInvoiceImage().getHeight()).isEqualTo(sampleInvoiceImage.getHeight());
		assertThat(invoice.getInvoiceImage().getWidth()).isEqualTo(sampleInvoiceImage.getWidth());
		assertThat(invoice.getInvoiceImage().getType()).isEqualTo(sampleInvoiceImage.getType());
	}

	@Test
	public void shouldBeAbleToAddPurchaseDate() {
		invoice.setPurchaseDate(PURCHASE_DATE);

		assertThat(invoice.getPurchaseDateAsString()).isEqualTo(PURCHASE_DATE);
	}

	@Test
	public void shouldNotUpdateIdWhenIsDifferentThanZero() {
		invoice.setId(10);
		invoice.setId(15);

		assertThat(invoice.getId()).isEqualTo(10);
	}

//TODO: test for exception when image does not exists

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
