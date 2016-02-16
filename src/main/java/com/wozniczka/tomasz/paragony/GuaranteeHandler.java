package com.wozniczka.tomasz.paragony;

import java.util.Calendar;
import java.util.Date;

public class GuaranteeHandler {
	public static boolean isGuaranteeValid(Invoice invoice) {
		Calendar currentTime = Calendar.getInstance();
		Calendar purchaseTime = Calendar.getInstance();
		Date purchaseDate = invoice.getPurchaseDate();
		int guarantee = invoice.getGuaranteePeriod();

		purchaseTime.setTime(purchaseDate);
		purchaseTime.add(Calendar.YEAR, guarantee);

		return purchaseTime.after(currentTime);
	}

}
