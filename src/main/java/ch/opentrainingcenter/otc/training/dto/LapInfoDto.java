package ch.opentrainingcenter.otc.training.dto;

import ch.opentrainingcenter.otc.training.entity.raw.LapInfo;
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
        id = l.getId();
        lap = l.getLap();
        start = l.getStart();
        end = l.getEnd();
        time = l.getTime();
        heartBeat = l.getHeartBeat();
        pace = l.getPace();
        speed = l.getGeschwindigkeit();
    }
}
