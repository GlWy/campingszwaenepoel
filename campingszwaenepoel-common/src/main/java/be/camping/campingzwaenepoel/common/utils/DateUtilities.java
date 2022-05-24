package be.camping.campingzwaenepoel.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtilities {

	public static boolean equals(Date date1, Date date2) {
		boolean ret = false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		if (cal1.equals(cal2)) {
			ret = true;
		}
		return ret;
	}

	public static boolean equals(String sDate1, String sDate2, SimpleDateFormat sdf) {
		boolean ret = false;
		Date date1;
		Date date2;
		try {
			date1 = sdf.parse(sDate1);
			date2 = sdf.parse(sDate2);
			if (date1.equals(date2)) {
				ret = true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public static long getDifference(Calendar a, Calendar b, TimeUnit units) {
		int day1 = a.get(Calendar.DAY_OF_YEAR);
		int day2 = b.get(Calendar.DAY_OF_YEAR);
		return day2 - day1;
	}
}
