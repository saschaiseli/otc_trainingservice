package ch.opentrainingcenter.otc.training.dto;

import ch.opentrainingcenter.otc.training.domain.raw.LapInfo;
import lombok.Getter;

@Getter
public class LapInfoDto {
	private final long id;
	private final int lap;
	private final int start;
	private final int end;
	private final long time;
	private final int heartBeat;
	private final String pace;
	private final String speed;

	public LapInfoDto(final LapInfo l) {
		this.id = l.getId();
		this.lap = l.getLap();
		this.start = l.getStart();
		this.end = l.getEnd();
		this.time = l.getTime();
		this.heartBeat = l.getHeartBeat();
		this.pace = l.getPace();
		this.speed = l.getGeschwindigkeit();
	}
}
