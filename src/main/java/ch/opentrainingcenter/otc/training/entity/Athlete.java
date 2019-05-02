package ch.opentrainingcenter.otc.training.entity;

import ch.opentrainingcenter.otc.training.entity.raw.Training;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NamedQuery(name = "Athlete.findByEmail", query = "SELECT a FROM ATHLETE a where a.email=:email")
@Entity(name = "ATHLETE")
@Data
@NoArgsConstructor
@ToString
public class Athlete {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private String password;
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(nullable = true)
    private int maxheartrate;

    @Embedded
    private Settings settings;

    @Column
    private LocalDateTime lastLogin;

    @JsonIgnore
    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Training> trainings = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL)
    private List<TrainingGoal> goals = new ArrayList<>();

    @Transient
    private String token;

    public Athlete(final String firstName, final String lastName, final String email, final String hashedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        password = hashedPassword;
    }

    public void addTarget(final TrainingGoal goal) {
        goals.add(goal);
        goal.setAthlete(this);
    }

    public void removeTarget(final TrainingGoal goal) {
        goals.remove(goal);
        goal.setAthlete(null);
    }

    public void addTraining(final Training training) {
        trainings.add(training);
        training.setAthlete(this);
    }

    public void removeTraining(final Training training) {
        trainings.remove(training);
        training.setAthlete(null);
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
