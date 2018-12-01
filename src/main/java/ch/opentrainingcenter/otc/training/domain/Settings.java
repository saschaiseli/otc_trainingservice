package ch.opentrainingcenter.otc.training.domain;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Data
@ToString(includeFieldNames = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Settings {

	@Enumerated(EnumType.STRING)
	private SystemOfUnit unit;

	@Enumerated(EnumType.STRING)
	private Speed speed;

}
