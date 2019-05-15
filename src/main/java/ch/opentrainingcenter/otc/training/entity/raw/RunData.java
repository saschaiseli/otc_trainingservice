package ch.opentrainingcenter.otc.training.entity.raw;

import java.util.Date;

/**
 * Info zu einem Lauf
 */
public class RunData {

    private final Date date;
    private final long timeInSeconds;
    private final long distanceInMeter;
    private final double maxSpeed;

    /**
     * @param dateOfStart     Zeitpunkt des Startes in ms
     * @param timeInSeconds   Dauer des Laufes in Sekunden
     * @param distanceInMeter Distanz in meter
     * @param maxSpeed
     */
    public RunData(final Date date, final long timeInSeconds, final long distanceInMeter, final double maxSpeed) {
        this.date = date;
        this.timeInSeconds = timeInSeconds;
        this.distanceInMeter = distanceInMeter;
        this.maxSpeed = maxSpeed;
    }

    /**
     * @return Zeitpunkt des Startes in ms
     */
    public Date getDateOfStart() {
        return date;
    }

    /**
     * @return Dauer des Laufes in Sekunden
     */
    public long getTimeInSeconds() {
        return timeInSeconds;
    }

    /**
     * @return Distanz in meter
     */
    public long getDistanceInMeter() {
        return distanceInMeter;
    }

    /**
     * @return maximale geschwindigkeit
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }
}
