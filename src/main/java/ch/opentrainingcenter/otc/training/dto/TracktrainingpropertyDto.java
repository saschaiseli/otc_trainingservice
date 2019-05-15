package ch.opentrainingcenter.otc.training.dto;

import ch.opentrainingcenter.otc.training.entity.raw.Tracktrainingproperty;
import lombok.Getter;

@Getter
public class TracktrainingpropertyDto {
    private final long id;
    private final double distance;
    private final int heartbeat;
    private final int altitude;
    private final long time;
    private final Double lng;
    private final Double lat;
    private final int lap;

    public TracktrainingpropertyDto(final Tracktrainingproperty p) {
        id = p.getId();
        distance = p.getDistance();
        heartbeat = p.getHeartBeat();
        altitude = p.getAltitude();
        time = p.getZeit();
        lng = p.getLongitude();
        lat = p.getLatitude();
        lap = p.getLap();
    }

}
