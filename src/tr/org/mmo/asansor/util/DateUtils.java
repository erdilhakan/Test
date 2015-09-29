package tr.org.mmo.asansor.util;

import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtils {
	public static long differenceBetweenTwoDates(Date arg0, Date arg1) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal0 = Calendar.getInstance();
		cal1.setTime(arg1);
		cal0.setTime(arg0);
		long fark = (cal1.getTimeInMillis() - cal0.getTimeInMillis())
				/ (1000 * 60 * 60 * 24);
		return fark < 0 ? (-1) * fark : fark;

	}

	public static Date addDate(java.util.Date date, int daysToAdd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, daysToAdd);
		return cal.getTime();
	}

	public static XMLGregorianCalendar CalendarToXMLGregorianCalendar(
			Calendar cal) throws DatatypeConfigurationException {
		DatatypeFactory dtf = DatatypeFactory.newInstance();
		XMLGregorianCalendar xgc = dtf.newXMLGregorianCalendar();
		xgc.setYear(cal.get(Calendar.YEAR));
		xgc.setDay(cal.get(Calendar.DAY_OF_MONTH));
		xgc.setMonth(cal.get(Calendar.MONTH) + 1);
		xgc.setHour(cal.get(Calendar.HOUR_OF_DAY));
		xgc.setMinute(cal.get(Calendar.MINUTE));
		xgc.setSecond(cal.get(Calendar.SECOND));
		xgc.setMillisecond(cal.get(Calendar.MILLISECOND));

		int offsetInMinutes = (cal.get(Calendar.ZONE_OFFSET) + cal
				.get(Calendar.DST_OFFSET)) / (60 * 1000);
		xgc.setTimezone(offsetInMinutes);
		return xgc;
	}

	public static String getMonth(int month) {
		String mnth = "";
		switch (month + 1) {
		case 1:
			mnth = "Ocak";
			break;
		case 2:
			mnth = "Şubat";
			break;
		case 3:
			mnth = "Mart";
			break;
		case 4:
			mnth = "Nisan";
			break;
		case 5:
			mnth = "Mayıs";
			break;
		case 6:
			mnth = "Haziran";
			break;
		case 7:
			mnth = "Temmuz";
			break;
		case 8:
			mnth = "Ağustos";
			break;
		case 9:
			mnth = "Eylül";
			break;
		case 10:
			mnth = "Ekim";
			break;
		case 11:
			mnth = "Kasım";
			break;
		case 12:
			mnth = "Aralık";
			break;
		default:
			break;
		}
		return mnth;
	}

}
