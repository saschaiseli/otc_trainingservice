package ch.opentrainingcenter.otc.training.service.converter.util;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public final class TimeHelper {

	private static final String DATE_FILE_FORMAT_PATTERN = "yyyyMMddHHmmssSSS"; //$NON-NLS-1$
	private static final String DATE_TIME_FORMAT_PATTERN = "dd.MM.yyyy HH:mm:ss"; //$NON-NLS-1$
	private static final String TIME_FORMAT_PATTERN = "HH:mm:ss"; //$NON-NLS-1$
	private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy"; //$NON-NLS-1$
	private static final String UNKNOWN_DATE = "--:--:--"; //$NON-NLS-1$

	private TimeHelper() {

	}

	/**
	 * Konvertiert Sekunden in HH:MM:ss
	 *
	 * @param sec sekunden
	 * @return Zeit im Format HH:MM:ss. Wenn Zeit negativ ist, wird --:--:--
	 *         zurückgegeben.
	 */
	public static String convertSecondsToHumanReadableZeit(final double sec) {
		if (sec < 0) {
			return UNKNOWN_DATE;
		}
		return convertTimeToString((long) (sec * 1000));
	}

	/**
	 * konvertiere ein {@link Date} in ein lesbareres format.
	 *
	 * wenn der parameter withDay true ist wird der wochentag noch mit ausgegeben
	 *
	 * @param datum   {@link Date}
	 * @param withDay flag ob der wochenag auch mitgegeben werden soll.
	 * @return das datum '13.01.2013 00:00:00' und wenn das flag with Day ist wird
	 *         noch der ausgeschreibene wochentag vorangestellt.
	 */
	public static String convertDateToString(final Date datum, final boolean withDay) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(datum);
		final SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN);
		if (withDay) {
			return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()) + " " //$NON-NLS-1$
					+ format.format(datum);
		} else {
			return format.format(datum);
		}
	}

	/**
	 * konvertiere ein {@link Date} in ein lesbareres format.
	 *
	 * wenn der parameter withDay true ist wird der wochentag noch mit ausgegeben
	 *
	 * @param datum   {@link Date}
	 * @param withDay flag ob der wochenag auch mitgegeben werden soll.
	 * @return das datum '13.01.2013 00:00:00' und wenn das flag with Day ist wird
	 *         noch der ausgeschreibene wochentag vorangestellt.
	 */
	public static String convertDateToString(final Long datum, final boolean withDay) {
		return convertDateToString(new Date(datum), withDay);
	}

	/**
	 * konvertiere ein {@link Date} in ein lesbareres format.
	 *
	 * wenn der parameter withDay true ist wird der wochentag noch mit ausgegeben
	 *
	 * @param datum {@link Date}
	 * @return das datum '201011231423'.
	 */
	public static String convertDateToFileName(final Date datum) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(datum);
		final SimpleDateFormat format = new SimpleDateFormat(DATE_FILE_FORMAT_PATTERN);
		return format.format(datum);
	}

	/**
	 * @return datum als String in der Form dd.mm.yyyy
	 */
	public static String convertDateToString(final Date datum) {
		final Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.setTime(datum);
		final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_PATTERN);
		return format.format(datum);
	}

	/**
	 * @return datum als String in der Form dd.mm.yyyy
	 */
	public static String convertDateToString(final Long millis) {
		return convertDateToString(new Date(millis));
	}

	/**
	 * @return zeit als String in der Form HH:mm:ss
	 */
	public static String convertTimeToString(final long timeInMillis) {
		final Calendar calendar = Calendar.getInstance(Locale.GERMAN);
		calendar.setTimeInMillis(timeInMillis);
		final SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT_PATTERN);
		format.setTimeZone(TimeZone.getTimeZone("GMT+0")); //$NON-NLS-1$
		return format.format(calendar.getTime());
	}

	/**
	 * @return zeit als String in der Form HH:mm:ss bei 23:59:59 wird aber
	 *         weitergezaehlt > 28:22:11,...
	 */
	public static String convertTimeToStringHourUnlimited(final long timeInMillis) {
		final long hours = TimeUnit.HOURS.convert(timeInMillis, TimeUnit.MILLISECONDS);
		final long remaing = timeInMillis - (hours * 60 * 60 * 1000);
		final int minutes = (int) TimeUnit.MINUTES.convert(remaing, TimeUnit.MILLISECONDS);
		final int seconds = (int) TimeUnit.SECONDS.convert(remaing - (minutes * 60 * 1000), TimeUnit.MILLISECONDS);
		return String.format("%s:%s:%s", hours, addLeadingZero(minutes), addLeadingZero(seconds));
	}

	private static String addLeadingZero(final int value) {
		if (value < 10) {
			return "0" + value; //$NON-NLS-1$
		} else {
			return String.valueOf(value);
		}
	}

	public static int getKalenderWoche(final Date date, final Locale locale) {
		final Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Gibt das Jahr zurück. Allerdings gibt zum Beispiel der 1. Januar 2012 nicht
	 * 2012 zurück sondern 2011. Das Jahr bezieht sich immer auf die Woche in der
	 * der Tag ist.
	 *
	 */
	public static int getJahr(final Date date, final Locale locale) {
		final Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH);
		final int week = calendar.get(Calendar.WEEK_OF_YEAR);
		if (week == 52 && month == 0) {
			// so zum beispiel am 1. januar 2012 --> KW52
			year--;
		}
		return year;
	}

	/**
	 * Berechnet den Montag, sowie den Sonntag der angegebenen Kalenderwoche in dem
	 * entsprechenden Jahr. </br>
	 * Für KW 44 im 2012 wäre dies 29. Oktober <--> 4. November
	 *
	 * @param jahr          Das Jahr
	 * @param kalenderwoche Die Kalenderwoche
	 * @return Start und Enddatum --> Montag und Sonntag der Kalenderwoche
	 */
	public static Interval getInterval(final int year, final int calendarWeek) {

		final LocalDate start = LocalDate.of(year, 5, 5).with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, calendarWeek)
				.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		final LocalDate end = LocalDate.of(year, 5, 5).with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, calendarWeek)
				.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
		return Interval.of(start, end);
	}

	/**
	 * Gibt den Montag 00:00:00 Uhr zurück von der Woche mit dem angegebenen Datum.
	 *
	 * Zum Beispiel: Sonntag 15. Dezember 2013 09:11:00 --> gibt Montag 9. Dezember
	 * 2013 00:00:00 zurück.
	 */
	public static LocalDateTime getFirstDayOfWeek(final LocalDateTime now) {
		LocalDateTime result = now.minusDays(now.getDayOfWeek().getValue() - 1);
		result = result.minusHours(now.getHour());
		result = result.minusMinutes(now.getMinute());
		result = result.minusSeconds(now.getSecond());
		return result;
	}

	/**
	 * Gibt den ersten Tag mit 00:00:00 Uhr vom Monat zurück.
	 *
	 * Zum Beispiel: Sonntag 15. Dezember 2013 09:11:00 --> gibt Sonntag 1. Dezember
	 * 2013 00:00:00 zurück.
	 */
	public static LocalDateTime getFirstDayOfMonth(final LocalDateTime ldt) {
		return LocalDateTime.of(ldt.getYear(), ldt.getMonth(), 1, 0, 0);
	}

}