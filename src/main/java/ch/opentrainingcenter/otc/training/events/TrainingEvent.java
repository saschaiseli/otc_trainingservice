package ch.opentrainingcenter.otc.training.events;

import ch.opentrainingcenter.otc.training.domain.raw.Training;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrainingEvent {
	private final Training training;
	private final String email;
}
