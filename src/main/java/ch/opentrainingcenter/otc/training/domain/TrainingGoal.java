package ch.opentrainingcenter.otc.training.domain;

import java.time.LocalDate;

import lombok.Data;

//@Entity
@Data
public class TrainingGoal {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long athleteId;
	private String name;
	private LocalDate begin;
	private LocalDate end;
	private long value;
//	@OneToMany
	private Unity unity;
//	@OneToMany
	private Sport sport;
	private String property;
}
