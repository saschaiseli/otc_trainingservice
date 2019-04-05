package ch.opentrainingcenter.otc.training.entity.raw;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "LAP_INFO")
public class LapInfo {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private long id;

    private int lap;
    @Column(name = "LAP_START")
    private int start;

    @Column(name = "LAP_END")
    private int end;
    private long time;
    private int heartBeat;
    private String pace;

    private String geschwindigkeit;

    public LapInfo() {

    }

    /**
     * Start und End beziehen sich immer auf die kilometrierung der strecke.
     *
     * <pre>
     *
     *                           LapInfo
     * |----------------------<--------->-----|
     * 0m                   140m       180m
     *
     *                     Start       End
     *
     *
     * </pre>
     *
     * @param lap             Runde [Anzahl]
     * @param start           start in Meter [m]
     * @param end             ende der runde in [m]
     * @param time            Zeit in Millisekunden [ms]
     * @param heartBeat       Herzschlag in [Bpm]
     * @param pace            Pace in [min/km]
     * @param geschwindigkeit durchschnittliche Geschwindigkeit [km/h]
     */
    public LapInfo(final int lap, final int start, final int end, final long time, final int heartBeat,
                   final String pace, final String geschwindigkeit) {
        this.lap = lap;
        this.start = start;
        this.end = end;
        this.time = time;
        this.heartBeat = heartBeat;
        this.pace = pace;
        this.geschwindigkeit = geschwindigkeit;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public int getLap() {
        return lap;
    }

    public void setLap(final int lap) {
        this.lap = lap;
    }

    public int getStart() {
        return start;
    }

    public void setStart(final int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(final int end) {
        this.end = end;
    }

    public long getTime() {
        return time;
    }

    public void setTime(final long time) {
        this.time = time;
    }

    public int getHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(final int heartBeat) {
        this.heartBeat = heartBeat;
    }

    public String getPace() {
        return pace;
    }

    public void setPace(final String pace) {
        this.pace = pace;
    }

    public String getGeschwindigkeit() {
        return geschwindigkeit;
    }

    public void setGeschwindigkeit(final String geschwindigkeit) {
        this.geschwindigkeit = geschwindigkeit;
    }

    @Override
    @SuppressWarnings("nls")

    public String toString() {
        return "LapInfo [id=" + id + ", lap=" + lap + ", start=" + start + ", end=" + end + ", time=" + time
                + ", heartBeat=" + heartBeat + ", pace=" + pace + "]";
    }

}
