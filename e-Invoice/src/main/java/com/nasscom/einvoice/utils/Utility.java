package com.nasscom.einvoice.utils;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class Utility {
	private static Calendar c = Calendar.getInstance();

	public static int differenceDays(DateTime start, DateTime end) {
		return Days.daysBetween(start, end).getDays();
	}

	public static int timeRemainAllowDays(Date fromDate, Date toDate) {
		c.setTime(fromDate);
		c.add(Calendar.DATE, 7); //time remain on allow days
		if (c.getTime().compareTo(toDate) < 0) {
			// It's more than 7 days.
		}
		return c.getTime().compareTo(toDate);
	}
	
	public static String getCurrentFinancialYear() {
		String currentFinancialYear="";
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int advance = (month <= 3) ? -1:0;//-1, if current month is March or before(then currentFY is previous to currentYear)
        int year = Calendar.getInstance().get(Calendar.YEAR) + advance;
        currentFinancialYear= year+"-"+(year+1);//eg format 2017-2018
		return currentFinancialYear;
	}
}
