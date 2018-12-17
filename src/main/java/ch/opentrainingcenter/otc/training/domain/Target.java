package ch.opentrainingcenter.otc.training.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NamedQuery(name = "Target.findByAthlete", query = "SELECT t FROM TARGET t where t.athlete.id=:athleteId")
@Data
@NoArgsConstructor
@Entity(name = "TARGET")
public class Target {

	@Id
	@SequenceGenerator(name = "TARGET_ID_SEQUENCE", sequenceName = "TARGET_ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TARGET_ID_SEQUENCE")
	private long id;
	@NotNull
	private LocalDate targetBegin;
	@NotNull
	private int amount;
	@NotNull
	@Enumerated(EnumType.STRING)
	private Duration duration;
	@NotNull
	private int distance;
	@ManyToOne
	@JoinColumn(name = "ID_FK_ATHLETE", nullable = false)
	private Athlete athlete;

}
