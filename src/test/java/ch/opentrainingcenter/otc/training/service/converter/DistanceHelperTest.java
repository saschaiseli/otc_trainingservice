package ch.opentrainingcenter.otc.training.service.converter;

import ch.opentrainingcenter.otc.training.service.converter.util.DistanceHelper;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class DistanceHelperTest {
    @Test
    public void testConvert() {
        final String roundDistance = DistanceHelper.roundDistanceFromMeterToKmMitEinheit(10123.4567890);
        assertEquals("10.123km", roundDistance);
    }

    @Test
    public void testConvertLessZeros() {
        final String roundDistance = DistanceHelper.roundDistanceFromMeterToKmMitEinheit(0.0);
        assertEquals("0.000km", roundDistance);
    }

    @Test
    public void testConvertNoZeros() {
        final String roundDistance = DistanceHelper.roundDistanceFromMeterToKm(4);
        assertEquals("0.004", roundDistance);
    }

    @Test
    public void testConvertBig() {
        final String roundDistance = DistanceHelper.roundDistanceFromMeterToKmMitEinheit(10123456.7890);
        assertEquals("10123.457km", roundDistance);
    }

    @Test
    public void testConvertSmall() {
        final String roundDistance = DistanceHelper.roundDistanceFromMeterToKmMitEinheit(12.34567890);
        assertEquals("0.012km", roundDistance);
    }

    @Test
    public void testConvertSmallRoundUp() {
        final String roundDistance = DistanceHelper.roundDistanceFromMeterToKmMitEinheit(12.64567890);
        assertEquals("0.013km", roundDistance);
    }

    @Test
    public void testCalculatePace() {
        final String pace = DistanceHelper.calculatePace(1000, 300);
        assertEquals("5:00", pace);
    }

    @Test
    public void testCalculatePaceSekunden() {
        final String pace = DistanceHelper.calculatePace(1000, 315);
        assertEquals("5:15", pace);
    }

    @Test
    public void testCalculateGeschwindigkeit() {
        final String pace = DistanceHelper.calculateGeschwindigkeit(42000, 3600);
        assertEquals("42.0", pace);
    }

    @Test
    public void testCalculateGeschwindigkeit_2() {
        final String pace = DistanceHelper.calculateGeschwindigkeit(42111.213, 3600);
        assertEquals("42.1", pace);
    }

    @Test
    public void testCalculatePaceRunning() {
        final String pace = DistanceHelper.calculatePace(42111.213, 3600);
        assertEquals("1:25", pace);
    }

    @Test
    public void shouldReturnDistanceFromMeterToKm() {
        // Given

        // When
        final String result = DistanceHelper.roundDistanceFromMeterToKm(1234);

        // Then
        assertThat("1.234", Matchers.equalTo(result));
    }
}
