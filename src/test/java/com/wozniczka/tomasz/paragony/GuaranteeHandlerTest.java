package com.wozniczka.tomasz.paragony;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GuaranteeHandlerTest {

	Invoice invoiceWithValidGuarantee;
	Invoice invoiceWithInvalidGuarantee;

	@Before
	public void setUp() {

		invoiceWithValidGuarantee = new Invoice();
		invoiceWithValidGuarantee.setProductName("Valid");
		invoiceWithValidGuarantee.setPurchaseDate("2016-01-02");
		invoiceWithValidGuarantee.setGuaranteePeriod(2);

		invoiceWithInvalidGuarantee = new Invoice();
		invoiceWithInvalidGuarantee.setProductName("Invalid");
		invoiceWithInvalidGuarantee.setPurchaseDate("2010-01-02");
		invoiceWithInvalidGuarantee.setGuaranteePeriod(1);
	}

	@Test
	public void shouldGuaranteeBeValid() {
		assertThat(GuaranteeHandler.isGuaranteeValid(invoiceWithValidGuarantee)).isTrue();
	}

	@Test
	public void shouldGuaranteeBeInvalid() {
		assertThat(GuaranteeHandler.isGuaranteeValid(invoiceWithInvalidGuarantee)).isFalse();

	}

}