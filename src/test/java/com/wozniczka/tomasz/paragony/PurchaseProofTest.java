package com.wozniczka.tomasz.paragony;

import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseProofTest {

	private static final String HEADPHONES = "Headphones";
	private static final String INVOICE_IMAGE_PATH = "/home/tomek/IdeaProjects/paragony/src/test/TestResources/427572.jpg";
	//private static final String INCORRECT_INVOICE_IMAGE_PATH = "test427572.jpg";
	private static final int PRICE = 400;
	private final BufferedImage sampleInvoiceImage = loadInvoiceImage(INVOICE_IMAGE_PATH);
	private PurchaseProof purchaseProof;

	@Before
	public void setUp() {
		purchaseProof = new PurchaseProof();
	}

	@Test
	public void shouldBeAbleToAddProductName() {
		purchaseProof.setProductName(HEADPHONES);

		assertThat(purchaseProof.getProductName()).isEqualTo(HEADPHONES);
	}

	@Test
	public void shouldBeAbleToAddProductPrice() {
		purchaseProof.setProductPrice(PRICE);

		assertThat(purchaseProof.getProductPrice()).isEqualTo(PRICE);
	}

	@Test
	public void shouldBeAbleToAddGuaranteePeriod() {
		purchaseProof.setGuaranteePeriod(2);

		assertThat(purchaseProof.getGuaranteePeriod()).isEqualTo(2);
	}

	@Test
	public void shouldBeAbleToAddScannedInvoice() {
		purchaseProof.addInvoiceImage(INVOICE_IMAGE_PATH);

		assertThat(purchaseProof.getInvoiceImage().getHeight()).isEqualTo(sampleInvoiceImage.getHeight());
		assertThat(purchaseProof.getInvoiceImage().getWidth()).isEqualTo(sampleInvoiceImage.getWidth());
		assertThat(purchaseProof.getInvoiceImage().getType()).isEqualTo(sampleInvoiceImage.getType());
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