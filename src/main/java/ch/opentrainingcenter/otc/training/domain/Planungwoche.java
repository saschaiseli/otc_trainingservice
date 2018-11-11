package ch.opentrainingcenter.otc.training.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Planungwoche {
    @Id
    @SequenceGenerator(name = "PLANUNGWOCHE_ID_SEQUENCE", sequenceName = "PLANUNGWOCHE_ID_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLANUNGWOCHE_ID_SEQUENCE")
    private long id;

    @ManyToOne
    @JoinColumn(name = "ID_FK_ATHLETE")
    private Athlete athlete;
    private Integer kw;
    private Integer jahr;
    private Integer kmprowoche;
    private Boolean interval;
    private Integer langerLauf;

    public Planungwoche() {
    }

    public Planungwoche(final Athlete athlete, final Integer jahr, final Integer kw, final int kmprowoche, final Boolean interval, final Integer langerlauf) {
        this.athlete = athlete;
        this.kw = kw;
        this.jahr = jahr;
        this.kmprowoche = kmprowoche;
        this.interval = interval;
        langerLauf = langerlauf;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(final Athlete athlete) {
        this.athlete = athlete;
    }

    public int getKw() {
        return kw;
    }

    public void setKw(final Integer kw) {
        this.kw = kw;
    }

    public int getJahr() {
        return jahr;
    }

    public void setJahr(final Integer jahr) {
        this.jahr = jahr;
    }

    public Integer getKmprowoche() {
        return kmprowoche;
    }

    public void setKmprowoche(final Integer kmprowoche) {
        this.kmprowoche = kmprowoche;
    }

    public Boolean getInterval() {
        return interval;
    }

    public void setInterval(final Boolean interval) {
        this.interval = interval;
    }

    public Integer getLangerlauf() {
        return langerLauf;
    }

    public void setLangerlauf(final Integer langerlauf) {
        langerLauf = langerlauf;
    }

    public void setKw(final int kw) {
        this.kw = kw;
    }

    public void setJahr(final int jahr) {
        this.jahr = jahr;

    }

    public int getKmProWoche() {
        return kmprowoche;
    }

    public void setKmProWoche(final int kmProWoche) {
        kmprowoche = kmProWoche;
    }

    public boolean isInterval() {
        return interval;
    }

    public void setInterval(final boolean interval) {
        this.interval = interval;

    }

    public int getLangerLauf() {
        return langerLauf;
    }

    public void setLangerLauf(final int langerLauf) {
        this.langerLauf = langerLauf;
    }

}
