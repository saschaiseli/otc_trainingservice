package ch.opentrainingcenter.otc.training.dto;

import ch.opentrainingcenter.otc.training.domain.raw.Tracktrainingproperty;
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
		this.id = p.getId();
		this.distance = p.getDistance();
		this.heartbeat = p.getHeartBeat();
		this.altitude = p.getAltitude();
		this.time = p.getZeit();
		this.lng = p.getLongitude();
		this.lat = p.getLatitude();
		this.lap = p.getLap();
	}

}
