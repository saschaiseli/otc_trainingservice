package ch.opentrainingcenter.otc.training.domain;

import lombok.Data;

//@Entity
@Data
public class Sport {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String description;
	private String property;
}
