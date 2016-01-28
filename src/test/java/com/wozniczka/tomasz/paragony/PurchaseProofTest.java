package com.wozniczka.tomasz.paragony;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PurchaseProofTest {

	public static final String HEADPHONES = "Headphones";
	public static final int PRICE = 400;
	PurchaseProof purchaseProof;

	@Before
	public void setUp() {
		purchaseProof = new PurchaseProof();
	}

	@Test
	public void shouldBeAbleToAddProductName() {
		purchaseProof.setProductName(HEADPHONES);

		assertEquals(HEADPHONES, purchaseProof.getProductName());
	}

	@Test
	public void shouldBeAbleToAddProductPrice() {
		purchaseProof.setProductPrice(PRICE);

		assertEquals(PRICE, purchaseProof.getProductPrice());
	}

}