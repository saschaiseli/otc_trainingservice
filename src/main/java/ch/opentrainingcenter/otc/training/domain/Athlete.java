package ch.opentrainingcenter.otc.training.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.opentrainingcenter.otc.training.domain.raw.Training;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NamedQuery(name = "Athlete.findByEmail", query = "SELECT a FROM ATHLETE a where a.email=:email")
@NamedQuery(name = "Athlete.authenticate", query = "SELECT a FROM ATHLETE a where a.email=:email AND a.pwd=:pwd")
@Entity(name = "ATHLETE")
@Data
@NoArgsConstructor
@ToString
public class Athlete {

	@Id
	@SequenceGenerator(name = "ATHLETE_ID_SEQUENCE", sequenceName = "ATHLETE_ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATHLETE_ID_SEQUENCE")
	@ToString.Include
	private long id;
	@Column(nullable = false)
	@ToString.Include
	private String firstName;
	@Column(nullable = false)
	@ToString.Include
	private String lastName;
	@Pattern(regexp = ".+@.+", message = "{user.email.pattern}")
	@Column(unique = true, nullable = false)
	@ToString.Include
	private String email;
	@Column(nullable = false)
	private String pwd;
	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(nullable = true)
	private int maxheartrate;

	@Embedded
	private Settings settings;

	@Column
	private LocalDateTime lastLogin;

	@JsonIgnore
	@OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL)
	private List<Training> trainings = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL)
	private List<Target> targets = new ArrayList<>();

	@Transient
	private String token;

	public Athlete(final String firstName, final String lastName, final String email, final String pwd) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.pwd = pwd;
	}

	public void addTarget(final Target target) {
		targets.add(target);
		target.setAthlete(this);
	}

	public void removeTarget(final Target target) {
		targets.remove(target);
		target.setAthlete(null);
	}

	public void addTraining(final Training training) {
		trainings.add(training);
		training.setAthlete(this);
	}

	public void removeTraining(final Training training) {
		trainings.remove(training);
		training.setAthlete(null);
	}
}
