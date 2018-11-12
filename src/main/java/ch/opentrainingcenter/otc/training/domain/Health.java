package ch.opentrainingcenter.otc.training.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "HEALTH")
public class Health {
	@Id
	@SequenceGenerator(name = "HEALTH_ID_SEQUENCE", sequenceName = "HEALTH_ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HEALTH_ID_SEQUENCE")
	private long id;

	@ManyToOne
	@JoinColumn(name = "ID_FK_ATHLETE")
	private Athlete athlete;

	private Integer weight;
	private Integer cardio;

	@Temporal(TemporalType.DATE)
	private Date dateofmeasure;

	public Health() {
	}

	public Health(final Athlete athlete, final Integer weight, final Integer cardio, final Date dateofmeasure) {
		this.athlete = athlete;
		this.weight = weight;
		this.cardio = cardio;
		this.dateofmeasure = dateofmeasure;
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

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(final Integer weight) {
		this.weight = weight;
	}

	public Integer getCardio() {
		return cardio;
	}

	public void setCardio(final Integer cardio) {
		this.cardio = cardio;
	}

	public Date getDateofmeasure() {
		return dateofmeasure;
	}

	public void setDateofmeasure(final Date dateofmeasure) {
		this.dateofmeasure = dateofmeasure;
	}

	@Override
	@SuppressWarnings("nls")

	public String toString() {
		return "Health [athlete=" + athlete + ", weight=" + weight + ", cardio=" + cardio + ", dateofmeasure="
				+ dateofmeasure + "]";
	}

}
