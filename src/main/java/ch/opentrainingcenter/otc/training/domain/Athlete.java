package ch.opentrainingcenter.otc.training.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

import ch.opentrainingcenter.otc.training.domain.raw.Training;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedQueries({ //
		@NamedQuery(name = "Athlete.findByEmail", query = "SELECT a FROM ATHLETE a where a.email=:email") })

@Entity(name = "ATHLETE")
@Data
@NoArgsConstructor
public class Athlete {

	@Id
	@SequenceGenerator(name = "ATHLETE_ID_SEQUENCE", sequenceName = "ATHLETE_ID_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATHLETE_ID_SEQUENCE")
	private long id;

	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Pattern(regexp = ".+@.+", message = "{user.email.pattern}")
	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(nullable = true)
	private int maxheartrate;

	@Embedded
	private Settings settings;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	@OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL)
	private Set<Health> healths = new HashSet<>();

	@OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL)
	private Set<Training> trainings = new HashSet<>();

	@OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL)
	private Set<PlaningWeek> planungwoches = new HashSet<>();

	@OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL)
	private Set<Shoe> shoes = new HashSet<>();

	public Athlete(final String firstName, final String lastName, final String email, final String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	@Override
	public String toString() {
		return "Athlete [id=" + id + ", name=" + firstName + ", birthday=" + birthday + ", maxheartrate=" + maxheartrate
				+ "]";
	}

}
