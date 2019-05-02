package ch.opentrainingcenter.otc.training.entity.raw;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "TRACK_PROPERTIES")
public class Tracktrainingproperty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double distance;
    private int heartbeat;
    private int altitude;
    private long zeit;
    private Double longitude;
    private Double latitude;

    private int lap;

    public Tracktrainingproperty() {
    }

    public Tracktrainingproperty(final double distance, final int heartbeat, final int altitude, final long zeit,
                                 final int lap, final Double longitude, final Double latitude) {
        this.distance = distance;
        this.heartbeat = heartbeat;
        this.altitude = altitude;
        this.zeit = zeit;
        this.lap = lap;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(final double distance) {
        this.distance = distance;
    }

    public int getHeartBeat() {
        return heartbeat;
    }

    public void setHeartBeat(final int heartbeat) {
        this.heartbeat = heartbeat;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(final int altitude) {
        this.altitude = altitude;
    }

    public long getZeit() {
        return zeit;
    }

    public void setZeit(final long zeit) {
        this.zeit = zeit;
    }

    public int getLap() {
        return lap;
    }

    public void setLap(final int lap) {
        this.lap = lap;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Tracktrainingproperty [id=" + id + ", distance=" + distance + ", heartbeat=" + heartbeat + ", altitude="
                + altitude + ", zeit=" + zeit + ", longitude=" + longitude + ", latitude=" + latitude + ", lap=" + lap
                + "]";
    }

}
