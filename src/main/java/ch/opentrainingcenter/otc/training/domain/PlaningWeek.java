package ch.opentrainingcenter.otc.training.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "PLANING_WEEK")
@Data
@NoArgsConstructor
public class PlaningWeek {
	@Id
	@SequenceGenerator(name = "PLANING_WEEK_ID_SEQUENCE", sequenceName = "PLANING_WEEK_ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLANING_WEEK_ID_SEQUENCE")
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
