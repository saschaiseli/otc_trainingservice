package ch.opentrainingcenter.otc.training.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import ch.opentrainingcenter.otc.training.domain.raw.Training;

@Entity
public class Route {

    @Id
    @SequenceGenerator(name = "ROUTE_ID_SEQUENCE", sequenceName = "ROUTE_ID_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROUTE_ID_SEQUENCE")
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String beschreibung;

    @ManyToOne
    @JoinColumn(name = "ID_FK_ATHLETE", nullable = false)
    private Athlete athlete;

    @OneToOne
    @JoinColumn(name = "ID_FK_TRAINING", nullable = false)
    private Training referenzTrack;

    public Route() {
    }

    public Route(final String routenName, final String routenBeschreibung, final Training referenzTraining) {
        name = routenName;
        beschreibung = routenBeschreibung;
        athlete = referenzTraining.getAthlete();
        referenzTrack = referenzTraining;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(final String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setAthlete(final Athlete athlete) {
        this.athlete = athlete;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public Training getReferenzTrack() {
        return referenzTrack;
    }

    public void setReferenzTrack(final Training referenzTrack) {
        this.referenzTrack = referenzTrack;
    }

    @Override
    public String toString() {
        return "Route [name=" + name + ", beschreibung=" + beschreibung + ", athlete=" + athlete + "]";
    }

}
