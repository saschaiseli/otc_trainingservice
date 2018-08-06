package ch.opentrainingcenter.otc.training.domain;

import lombok.Data;

//@Entity
@Data
public class Unity {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String abbreviation;
}
