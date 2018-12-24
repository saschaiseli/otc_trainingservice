package ch.opentrainingcenter.otc.training.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedQuery(name = "TrainingGoal.findByAthlete", query = "SELECT tg FROM TRAINING_GOAL tg where tg.athlete.id=:athleteId")
@NoArgsConstructor
@Data
@Entity(name = "TRAINING_GOAL")
public class TrainingGoal {
	@Id
	@SequenceGenerator(name = "TRAINING_GOAL_ID_SEQUENCE", sequenceName = "TRAINING_GOAL_ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINING_GOAL_ID_SEQUENCE")
	private long id;
	private String name;
	private LocalDate begin;
	private LocalDate end;
	private int distanceOrHour;
	private double currentValue;
	private boolean active;
	@ManyToOne
	@JoinColumn(name = "ID_FK_ATHLETE", nullable = false)
	private Athlete athlete;

	public TrainingGoal(final TrainingGoalDto dto, final Athlete athlete) {
		this.id = dto.getId();
		this.athlete = athlete;
		this.name = "dummy";
		this.begin = dto.getBegin();
		this.end = dto.getEnd();
		this.distanceOrHour = dto.getDistanceOrHour();
		this.currentValue = dto.getCurrentValue();
		this.active = dto.isActive();

	}
}
