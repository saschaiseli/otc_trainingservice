package ch.opentrainingcenter.otc.training.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "PLANING_WEEK")
@Data
@NoArgsConstructor
public class PlaningWeek {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
//            generator = "native"
    )
//    @GenericGenerator(
//            name = "native",
//            strategy = "native"
//    )
    private long id;

    @ManyToOne
    @JoinColumn(name = "ID_FK_ATHLETE")
    private Athlete athlete;
    private Integer kw;
    private Integer jahr;
    private Integer kmprowoche;
    private boolean hasInterval;
    private Integer langerLauf;

    public PlaningWeek(final Athlete athlete, final Integer jahr, final Integer kw, final int kmprowoche,
                       final boolean hasInterval, final Integer langerlauf) {
        this.athlete = athlete;
        this.kw = kw;
        this.jahr = jahr;
        this.kmprowoche = kmprowoche;
        this.hasInterval = hasInterval;
        langerLauf = langerlauf;
    }

}
