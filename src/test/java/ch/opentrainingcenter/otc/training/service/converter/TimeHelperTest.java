package ch.opentrainingcenter.otc.training.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import ch.opentrainingcenter.otc.training.service.converter.util.Interval;
import ch.opentrainingcenter.otc.training.service.converter.util.TimeHelper;

class TimeHelperTest {

	private final Locale locale = Locale.GERMAN;

	@Before
	public void setUp() {
		Locale.setDefault(Locale.GERMAN);
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));

		final DateTimeZone zoneUTC = DateTimeZone.forID("Europe/Berlin");
		DateTimeZone.setDefault(zoneUTC);
	}

	@Test
	public void testConvertSekundenInReadableFormat() {
		final String t = TimeHelper.convertSecondsToHumanReadableZeit(6273.27);
		assertEquals("01:44:33", t);
	}

	@Test
	public void testConvertNegative() {
		final String t = TimeHelper.convertSecondsToHumanReadableZeit(-1);
		assertEquals("--:--:--", t);
	}

	@Test
	public void testConvert0() {
		final String t = TimeHelper.convertSecondsToHumanReadableZeit(0);
		assertEquals("00:00:00", t);
	}

	@Test
	public void getKalenderWoche1() {
		final Calendar cal = Calendar.getInstance(locale);
		cal.set(2012, 0, 1);
		final int kw = TimeHelper.getKalenderWoche(cal.getTime(), Locale.GERMAN);
		assertEquals(52, kw);
	}

	@Test
	public void getKalenderWoche2() {
		final Calendar cal = Calendar.getInstance(locale);
		cal.set(2012, 1, 1);
		final int kw = TimeHelper.getKalenderWoche(cal.getTime(), Locale.GERMAN);
		assertEquals(5, kw);
	}

	@Test
	public void getKalenderWoche3() {
		final Calendar cal = Calendar.getInstance(locale);
		cal.set(2012, 2, 1);
		final int kw = TimeHelper.getKalenderWoche(cal.getTime(), Locale.GERMAN);
		assertEquals(9, kw);
	}

	@Test
	public void getKalenderWoche4() {
		final Calendar cal = Calendar.getInstance(locale);
		cal.set(2012, 3, 1);
		final int kw = TimeHelper.getKalenderWoche(cal.getTime(), Locale.GERMAN);
		assertEquals(13, kw);
	}

	@Test
	public void getKalenderWoche5() {
		final Calendar cal = Calendar.getInstance(locale);
		cal.set(2012, 4, 1);
		final int kw = TimeHelper.getKalenderWoche(cal.getTime(), Locale.GERMAN);
		assertEquals(18, kw);
	}

	@Test
	public void getKalenderWoche6() {
		final Calendar cal = Calendar.getInstance(locale);
		cal.set(2012, 5, 1);
		final int kw = TimeHelper.getKalenderWoche(cal.getTime(), Locale.GERMAN);
		assertEquals(22, kw);
	}

	@Test
	public void getKalenderWoche7() {
		final Calendar cal = Calendar.getInstance(locale);
		cal.set(2012, 6, 1);
		final int kw = TimeHelper.getKalenderWoche(cal.getTime(), Locale.GERMAN);
		assertEquals(26, kw);
	}

	@Test
	public void getKalenderWoche8() {
		final Calendar cal = Calendar.getInstance(locale);
		cal.set(2012, 7, 1);
		final int kw = TimeHelper.getKalenderWoche(cal.getTime(), Locale.GERMAN);
		assertEquals(31, kw);
	}

	@Test
	public void getKalenderWoche9() {
		final Calendar cal = Calendar.getInstance(locale);
		cal.set(2012, 8, 1);
		final int kw = TimeHelper.getKalenderWoche(cal.getTime(), Locale.GERMAN);
		assertEquals(35, kw);
	}

	@Test
	public void getKalenderWoche10() {
		final Calendar cal = Calendar.getInstance(locale);
		cal.set(2012, 9, 1);
		final int kw = TimeHelper.getKalenderWoche(cal.getTime(), Locale.GERMAN);
		assertEquals(40, kw);
	}

	@Test
	public void getKalenderWoche11() {
		final Calendar cal = Calendar.getInstance(locale);
		cal.set(2012, 10, 1);
		final int kw = TimeHelper.getKalenderWoche(cal.getTime(), Locale.GERMAN);
		assertEquals(44, kw);
	}

	@Test
	public void getKalenderWoche12() {
		final Calendar cal = Calendar.getInstance(locale);
		cal.set(2012, 11, 1);
		final int kw = TimeHelper.getKalenderWoche(cal.getTime(), Locale.GERMAN);
		assertEquals(48, kw);
	}

	@Test
	public void testIntervallStart() {

		final Interval interval = TimeHelper.getInterval(2012, 44);
		final LocalDate start = LocalDate.of(2012, 10, 29);
		assertDatum(start, interval.getStart());
	}

	@Test
	public void testIntervallEnd() {
		final Interval interval = TimeHelper.getInterval(2012, 44);
		final LocalDate end = LocalDate.of(2012, 11, 4);
		assertDatum(end, interval.getEnd());
	}

	@Test
	public void testIntervall52_2011() {
		final Interval interval = TimeHelper.getInterval(2011, 52);
		final LocalDate start = LocalDate.of(2011, 12, 26);
		final LocalDate end = LocalDate.of(2012, 1, 1);
		assertDatum(start, interval.getStart());
		assertDatum(end, interval.getEnd());
	}

	@Test
	public void testIntervall1_2012() {
		final Interval interval = TimeHelper.getInterval(2012, 1);
		final LocalDate start = LocalDate.of(2012, 1, 2);
		final LocalDate end = LocalDate.of(2012, 1, 8);
		assertDatum(start, interval.getStart());
		assertDatum(end, interval.getEnd());
	}

	@Test
	public void testIntervall52_2012() {
		final Interval interval = TimeHelper.getInterval(2012, 52);
		final LocalDate start = LocalDate.of(2012, 12, 24);
		final LocalDate end = LocalDate.of(2012, 12, 30);
		assertDatum(start, interval.getStart());
		assertDatum(end, interval.getEnd());
	}

	@Test
	public void testIntervall1() {
		final Interval interval = TimeHelper.getInterval(2013, 1);
		final LocalDate start = LocalDate.of(2012, 12, 31);
		final LocalDate end = LocalDate.of(2013, 1, 6);
		assertDatum(start, interval.getStart());
		assertDatum(end, interval.getEnd());
	}

	@Test
	public void testIntervall2() {
		final Interval interval = TimeHelper.getInterval(2013, 2);
		final LocalDate start = LocalDate.of(2013, 1, 7);
		final LocalDate end = LocalDate.of(2013, 1, 13);
		assertDatum(start, interval.getStart());
		assertDatum(end, interval.getEnd());
	}

	@Test
	public void testConvertDateToStringOhneTag() {
		final DateTime dateTime = new DateTime(2013, 1, 13, 0, 0);

		final String converted = TimeHelper.convertDateToString(dateTime.toDate(), false);
		assertEquals("13.01.2013 00:00:00", converted);
	}

	@Test
	public void testConvertDateToString_Millis() {
		final DateTime dateTime = new DateTime(2013, 1, 13, 0, 0);
		final Date datum = dateTime.toDate();

		final Calendar calendar = Calendar.getInstance(Locale.GERMAN);
		calendar.setTime(datum);
		final String converted = TimeHelper.convertDateToString(datum.getTime());
		assertEquals("13.01.2013", converted);
	}

	@Test
	public void testConvertDateToStringMitTag() {
		final DateTime dateTime = new DateTime(2013, 1, 13, 0, 0);
		final Date datum = dateTime.toDate();
		Locale.setDefault(Locale.GERMAN);
		final Calendar calendar = Calendar.getInstance(Locale.GERMAN);
		calendar.setTime(datum);
		final String tag = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.GERMAN);
		final String converted = TimeHelper.convertDateToString(datum, true);
		assertEquals(tag + " 13.01.2013 00:00:00", converted);
	}

	@Test
	public void testConvertToFileName() {
		final DateTime dateTime = new DateTime(2013, 1, 13, 0, 0);
		final Date datum = dateTime.toDate();
		final String fileName = TimeHelper.convertDateToFileName(datum);
		assertEquals("20130113000000000", fileName);
	}

	@Test
	public void testConvertToString() {
		final DateTime dateTime = new DateTime(2013, 1, 13, 0, 0);
		final Date datum = dateTime.toDate();
		final String fileName = TimeHelper.convertDateToString(datum);
		assertEquals("13.01.2013", fileName);
	}

	@Test
	public void testGetJahr2013() {
		final DateTime dateTime = new DateTime(2013, 1, 13, 0, 0);
		final Date datum = dateTime.toDate();
		final int jahr = TimeHelper.getJahr(datum, Locale.GERMAN);
		assertEquals(2013, jahr);
	}

	@Test
	public void testGetJahr2012() {
		final DateTime dateTime = new DateTime(2012, 1, 1, 0, 0);
		final Date datum = dateTime.toDate();
		final int jahr = TimeHelper.getJahr(datum, Locale.GERMAN);
		assertEquals(2011, jahr);
	}

	@Test
	public void testConvertMillisToTime() {
		Locale.setDefault(Locale.CANADA_FRENCH);
		final String time = TimeHelper.convertTimeToString(0);
		assertEquals("00:00:00", time);
	}

	@Test
	public void testConvertMillisToTime_sekunden_als_tag() {
		Locale.setDefault(Locale.GERMAN);
		final String time = TimeHelper.convertTimeToStringHourUnlimited(86399000);
		assertEquals("23:59:59", time);
	}

	@Test
	public void testConvertMillisToTime_Mehr_sekunden_als_tag() {
		Locale.setDefault(Locale.GERMAN);
		final String time = TimeHelper.convertTimeToStringHourUnlimited(86400000);
		assertEquals("24:00:00", time);
	}

	@Test
	public void testConvertMillisToTime_Mehr_sekunden_als_tag_2() {
		Locale.setDefault(Locale.GERMAN);
		final String time = TimeHelper.convertTimeToStringHourUnlimited(86461000);
		assertEquals("24:01:01", time);
	}

	@Test
	public void testConvertMillisToTime_Mehr_sekunden_als_tag_3() {
		Locale.setDefault(Locale.GERMAN);
		final String time = TimeHelper.convertTimeToStringHourUnlimited(86518000);
		assertEquals("24:01:58", time);
	}

	@Test
	public void testFirstDayOfWeek() {
		final LocalDateTime dateTime = LocalDateTime.of(2013, 12, 15, 9, 11, 12, 42);
		final LocalDateTime expected = LocalDateTime.of(2013, 12, 9, 0, 0, 0, 0);

		final LocalDateTime firstDayOfWeek = TimeHelper.getFirstDayOfWeek(dateTime);

		assertDatum(expected, firstDayOfWeek);
	}

	@Test
	public void testFirstDayOfMonth() {
		final LocalDateTime dateTime = LocalDateTime.of(2013, 12, 15, 9, 11, 12, 42);
		final LocalDateTime expected = LocalDateTime.of(2013, 12, 1, 0, 0, 0, 0);

		final LocalDateTime firstDayOfMonth = TimeHelper.getFirstDayOfMonth(dateTime);

		assertDatum(expected, firstDayOfMonth);
	}

	private void assertDatum(final LocalDateTime expected, final LocalDateTime ldt) {
		assertEquals(expected.getHour(), ldt.getHour());
		assertEquals(expected.getMinute(), ldt.getMinute());
		assertEquals(expected.getSecond(), ldt.getSecond());
		assertEquals(expected.getDayOfMonth(), ldt.getDayOfMonth());
		assertEquals(expected.getMonth().getValue(), ldt.getMonth().getValue());
		assertEquals(expected.getYear(), ldt.getYear());
	}

	private void assertDatum(final LocalDate startExpected, final LocalDate start) {
		assertEquals(startExpected.getDayOfMonth(), start.getDayOfMonth());
		assertEquals(startExpected.getMonth(), start.getMonth());
		assertEquals(startExpected.getYear(), start.getYear());
	}

}
