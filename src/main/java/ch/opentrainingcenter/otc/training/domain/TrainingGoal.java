package ch.opentrainingcenter.otc.training.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class TrainingGoal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long athleteId;
	private String name;
	private LocalDate begin;
	private LocalDate end;
	private long value;

	@Enumerated(EnumType.STRING)
	private Unity unity;
	@OneToOne
	private Sport sport;
	private String property;

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getAthleteId() {
		return athleteId;
	}

	public void setAthleteId(final long athleteId) {
		this.athleteId = athleteId;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public LocalDate getBegin() {
		return begin;
	}

	public void setBegin(final LocalDate begin) {
		this.begin = begin;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(final LocalDate end) {
		this.end = end;
	}

	public long getValue() {
		return value;
	}

	public void setValue(final long value) {
		this.value = value;
	}

	public Unity getUnity() {
		return unity;
	}

	public void setUnity(final Unity unity) {
		this.unity = unity;
	}

	public Sport getSport() {
		return sport;
	}

	public void setSport(final Sport sport) {
		this.sport = sport;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(final String property) {
		this.property = property;
	}

}
