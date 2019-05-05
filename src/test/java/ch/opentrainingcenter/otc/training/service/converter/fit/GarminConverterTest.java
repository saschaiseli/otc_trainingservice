package ch.opentrainingcenter.otc.training.service.converter.fit;

import ch.opentrainingcenter.otc.training.TestConfig;
import ch.opentrainingcenter.otc.training.entity.raw.LapInfo;
import ch.opentrainingcenter.otc.training.entity.raw.Sport;
import ch.opentrainingcenter.otc.training.entity.raw.Tracktrainingproperty;
import ch.opentrainingcenter.otc.training.entity.raw.Training;
import ch.opentrainingcenter.otc.training.service.converter.util.DistanceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GarminConverterTest {

    private final GarminConverter service = new GarminConverter();

    @BeforeEach
    public void setUp() {
        Locale.setDefault(Locale.GERMAN);
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Zurich"));
    }

    @Test
    public void testIntervallTraining() throws FileNotFoundException {
        final File file = new File(TestConfig.FOLDER, TestConfig.FIT_FILE);

        final Training training = service.convert(new FileInputStream(file));
        final long dauer = training.getDauer();
        assertEquals(5641, dauer);
    }

    @Test
    public void testActivityConvertMit2Runden() throws FileNotFoundException {
        final File file = new File(TestConfig.FOLDER, "2_runden.fit");

        final Training training = service.convert(new FileInputStream(file));
        assertNotNull(training);
        assertThat("Ist null, da dieser Timestamp erst vom importer gesetzt", training.getDateOfImport(),
                is(nullValue()));

        final List<LapInfo> lapInfos = training.getLapInfos();
        assertEquals(2, lapInfos.size());

        final LapInfo lap1 = lapInfos.get(0);
        assertEquals(0, lap1.getLap());
        assertEquals(0, lap1.getStart());
        assertEquals(5495, lap1.getEnd());
        assertEquals(1959000, lap1.getTime());
        assertEquals(DistanceHelper.calculatePace(5495, 1959, Sport.RUNNING), lap1.getPace());
        assertEquals(DistanceHelper.calculatePace(5495, 1959, Sport.BIKING), lap1.getGeschwindigkeit());

        final LapInfo lap2 = lapInfos.get(1);
        assertEquals(1, lap2.getLap());
        assertEquals(5495, lap2.getStart());
        assertEquals(10499, lap2.getEnd()); // kleiner rundungsfehler
        assertEquals(1745000, lap2.getTime());
        assertEquals(DistanceHelper.calculatePace(10500 - 5495, lap2.getTime() / 1000, Sport.RUNNING), lap2.getPace());
        assertEquals(DistanceHelper.calculatePace(10500 - 5495, lap2.getTime() / 1000, Sport.BIKING),
                lap2.getGeschwindigkeit());
    }

    @Test
    public void testRealActivityConvert() throws ParseException, FileNotFoundException {
        final Training training = service.convert(new FileInputStream(new File(TestConfig.FOLDER, "2014_09_11.fit")));

        assertNotNull(training);
        assertThat("Ist null, da dieser Timestamp erst vom importer gesetzt", training.getDateOfImport(),
                is(nullValue()));
//		assertThat("Lauf startet um 2014-09-11 19:18:35", convertToDate("2014-09-11 19:18:35"),
//				equalTo(training.getDateOfStart().getTime()));
        assertThat("<TotalTimeSeconds>2003.2</TotalTimeSeconds>", 2003L, equalTo(training.getDauer()));
        assertThat("<DistanceMeters>5297.08</DistanceMeters>", 5297L, equalTo(training.getLaengeInMeter()));
        assertThat("<MaximumSpeed>5.067</MaximumSpeed>", 5.067, closeTo(training.getMaxSpeed(), 0.00001));
        assertThat("AverageHeartRateBpm", 132, equalTo(training.getAverageHeartBeat()));
        assertThat("MaximumHeartRateBpm", 159, equalTo(training.getMaxHeartBeat()));

        assertThat("Positive Höhenmeter", 198, equalTo(training.getUpMeter().intValue()));
        assertThat("Negative Höhenmeter", 195, equalTo(training.getDownMeter().intValue()));

        assertEquals(Sport.RUNNING, training.getSport());

        final List<Tracktrainingproperty> points = training.getTrackPoints();

        assertTrackPoint(0, "2014-09-11 19:18:35", 0.0, 558.0, 46.95485311, 7.449236233, points.get(0), training);
        assertTrackPoint(1, "2014-09-11 19:18:43", 12.57, 555.0, 46.95496534, 7.449198263, points.get(1), training);
        assertTrackPoint(2, "2014-09-11 19:18:48", 31.83, 555.0, 46.95501345, 7.448971029, points.get(2), training);
        assertTrackPoint(3, "2014-09-11 19:18:52", 45.68, 554.0, 46.95496056, 7.448805906, points.get(3), training);
        assertTrackPoint(4, "2014-09-11 19:18:55", 51.47, 554.0, 46.95492133, 7.448757458, points.get(4), training);
        // noch letzter punkt
        assertTrackPoint(301, "2014-09-11 19:51:57", 5294.86, 562.0, 46.9547371, 7.448976813,
                points.get(points.size() - 1), training);

        final List<LapInfo> lapInfos = training.getLapInfos();
        assertEquals(1, lapInfos.size());
        final LapInfo lap = lapInfos.get(0);
        assertEquals(0, lap.getStart());
        assertEquals((int) training.getLaengeInMeter(), lap.getEnd());
        assertEquals(2003000, lap.getTime());
        assertEquals(132, lap.getHeartBeat());
        assertEquals(DistanceHelper.calculatePace(training.getLaengeInMeter(), lap.getTime() / 1000, Sport.RUNNING),
                lap.getPace());
        assertEquals(DistanceHelper.calculatePace(training.getLaengeInMeter(), lap.getTime() / 1000, Sport.BIKING),
                lap.getGeschwindigkeit());
        assertEquals(29, training.getTrainingEffect().intValue());
    }

    private void assertTrackPoint(final int index, final String datum, final double distanz, final double hoehe,
                                  final double latitude, final double longitude, final Tracktrainingproperty point, final Training training)
            throws ParseException {
        final String punkt = "[Punkt " + index + "] ";
        final long time = convertToDate(datum);
        final long convertedTime = point.getZeit();
        assertThat(punkt + "Distanz vom Punkt", distanz, closeTo(point.getDistance(), 0.001));
        assertThat(punkt + "Differenz muss 0 [ms] sein", 0L, equalTo(convertedTime - time));
        assertThat(punkt + "Höhe des Punktes", (int) hoehe, equalTo(point.getAltitude()));
        assertThat(punkt + "LatitudeDegrees", latitude, closeTo(point.getLatitude(), 0.00000001));
        assertThat(punkt + "LongitudeDegrees", longitude, closeTo(point.getLongitude(), 0.00000001));
    }

    private long convertToDate(final String datum) throws ParseException {
        final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("Europe/Zurich"));
        return formatter.parse(datum).getTime();
    }

    @Test
    public void testGetFilePrefix() throws NamingException {
        // final ConvertFitEJB service = (ConvertFitEJB)
        // ctx.lookup("java:global/classes/ConvertFitEJB");
        assertEquals("fit", service.getFilePrefix());
    }

}
