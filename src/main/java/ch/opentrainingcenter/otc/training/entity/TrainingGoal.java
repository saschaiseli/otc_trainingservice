package ch.opentrainingcenter.otc.training.entity;

import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NamedQuery(name = "TrainingGoal.findByAthlete", query = "SELECT tg FROM TRAINING_GOAL tg where tg.athlete.id=:athleteId")
@NamedQuery(name = "TrainingGoal.findByAthleteAndDate", query = "SELECT t FROM TRAINING_GOAL t where "
        + "t.athlete.id=:athleteId " //
        + "and "//
        + "t.derStart<=:date and t.dasEnde>:date")

@NoArgsConstructor
@Entity(name = "TRAINING_GOAL")
public class TrainingGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private LocalDate derStart;
    private LocalDate dasEnde;
    private int distanceOrHour;
    @Enumerated(EnumType.STRING)
    private TargetUnit unit;
    private double currentValue;
    private double prediction;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "ID_FK_ATHLETE", nullable = false)
    private Athlete athlete;

    public TrainingGoal(final TrainingGoalDto dto, final Athlete athlete) {
        id = dto.getId();
        name = "dummy";
        derStart = dto.getBegin();
        dasEnde = dto.getEnd();
        distanceOrHour = dto.getDistanceOrHour();
        unit = dto.getUnit();
        currentValue = dto.getCurrentValue();
        active = dto.isActive();
        this.athlete = athlete;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDerStart() {
        return derStart;
    }

    public LocalDate getDasEnde() {
        return dasEnde;
    }

    public int getDistanceOrHour() {
        return distanceOrHour;
    }

    public TargetUnit getUnit() {
        return unit;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public double getPrediction() {
        return prediction;
    }

    public boolean isActive() {
        return active;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDerStart(final LocalDate begin) {
        derStart = begin;
    }

    public void setDasEnde(final LocalDate endOf) {
        dasEnde = endOf;
    }

    public void setDistanceOrHour(final int distanceOrHour) {
        this.distanceOrHour = distanceOrHour;
    }

    public void setUnit(final TargetUnit unit) {
        this.unit = unit;
    }

    public void setCurrentValue(final double currentValue) {
        this.currentValue = currentValue;
    }

    public void setPrediction(final double prediction) {
        this.prediction = prediction;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public void setAthlete(final Athlete athlete) {
        this.athlete = athlete;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TrainingGoal)) {
            return false;
        }
        final TrainingGoal other = (TrainingGoal) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        if (getId() != other.getId()) {
            return false;
        }
        final Object this$name = getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        final Object this$begin = getDerStart();
        final Object other$begin = other.getDerStart();
        if (this$begin == null ? other$begin != null : !this$begin.equals(other$begin)) {
            return false;
        }
        final Object this$endOf = getDasEnde();
        final Object other$endOf = other.getDasEnde();
        if (this$endOf == null ? other$endOf != null : !this$endOf.equals(other$endOf)) {
            return false;
        }
        if (getDistanceOrHour() != other.getDistanceOrHour()) {
            return false;
        }
        final Object this$unit = getUnit();
        final Object other$unit = other.getUnit();
        if (this$unit == null ? other$unit != null : !this$unit.equals(other$unit)) {
            return false;
        }
        if (Double.compare(getCurrentValue(), other.getCurrentValue()) != 0) {
            return false;
        }
        if (Double.compare(getPrediction(), other.getPrediction()) != 0) {
            return false;
        }
        if (isActive() != other.isActive()) {
            return false;
        }
        final Object this$athlete = getAthlete();
        final Object other$athlete = other.getAthlete();
        if (this$athlete == null ? other$athlete != null : !this$athlete.equals(other$athlete)) {
            return false;
        }
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TrainingGoal;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        final Object $name = getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $begin = getDerStart();
        result = result * PRIME + ($begin == null ? 43 : $begin.hashCode());
        final Object $endOf = getDasEnde();
        result = result * PRIME + ($endOf == null ? 43 : $endOf.hashCode());
        result = result * PRIME + getDistanceOrHour();
        final Object $unit = getUnit();
        result = result * PRIME + ($unit == null ? 43 : $unit.hashCode());
        final long $currentValue = Double.doubleToLongBits(getCurrentValue());
        result = result * PRIME + (int) ($currentValue >>> 32 ^ $currentValue);
        final long $prediction = Double.doubleToLongBits(getPrediction());
        result = result * PRIME + (int) ($prediction >>> 32 ^ $prediction);
        result = result * PRIME + (isActive() ? 79 : 97);
        final Object $athlete = getAthlete();
        result = result * PRIME + ($athlete == null ? 43 : $athlete.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "TrainingGoal(id=" + getId() + ", name=" + getName() + ", begin=" + getDerStart() + ", endOf=" + getDasEnde() + ", distanceOrHour=" + getDistanceOrHour() + ", unit=" + getUnit() + ", currentValue=" + getCurrentValue() + ", prediction=" + getPrediction() + ", active=" + isActive() + ", athlete=" + getAthlete() + ")";
    }
}
