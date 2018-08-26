//package ch.opentrainingcenter.otc.training.domain;
//
//import java.time.LocalDate;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//
//@Entity
//public class TrainingGoal {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private long id;
//	private long athleteId;
//	private String name;
//	private LocalDate begin;
//	private LocalDate end;
//	private long value;
//	@OneToMany
//	private Unity unity;
//	@OneToMany
//	private Sport sport;
//	private String property;
//
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public long getAthleteId() {
//		return athleteId;
//	}
//
//	public void setAthleteId(long athleteId) {
//		this.athleteId = athleteId;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public LocalDate getBegin() {
//		return begin;
//	}
//
//	public void setBegin(LocalDate begin) {
//		this.begin = begin;
//	}
//
//	public LocalDate getEnd() {
//		return end;
//	}
//
//	public void setEnd(LocalDate end) {
//		this.end = end;
//	}
//
//	public long getValue() {
//		return value;
//	}
//
//	public void setValue(long value) {
//		this.value = value;
//	}
//
//	public Unity getUnity() {
//		return unity;
//	}
//
//	public void setUnity(Unity unity) {
//		this.unity = unity;
//	}
//
//	public Sport getSport() {
//		return sport;
//	}
//
//	public void setSport(Sport sport) {
//		this.sport = sport;
//	}
//
//	public String getProperty() {
//		return property;
//	}
//
//	public void setProperty(String property) {
//		this.property = property;
//	}
//
//}
