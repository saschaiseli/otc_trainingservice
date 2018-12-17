package ch.opentrainingcenter.otc.training.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "TARGET")
public class Target {

	@Id
	@SequenceGenerator(name = "TARGET_ID_SEQUENCE", sequenceName = "TARGET_ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TARGET_ID_SEQUENCE")
	private long id;

	private LocalDate begin;
}
