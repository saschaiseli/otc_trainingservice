package ch.opentrainingcenter.otc.training.service.goal;

import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;
import ch.opentrainingcenter.otc.training.entity.GoalDuration;

import javax.ejb.Stateless;
import java.time.LocalDate;

@Stateless
public class TrainingGoalDateCalculator {

    public LocalDate getBeginDate(final LocalDate beginDate, final GoalDuration duration) {
        if (GoalDuration.MONTH.equals(duration)) {
            return beginDate.withDayOfMonth(1);
        } else {
            return beginDate;
        }
    }

    public LocalDate getEndDate(final LocalDate beginDate, final GoalDuration duration) {
        if (!GoalDuration.MONTH.equals(duration)) {
            return beginDate.plusDays(duration.getDays() - 1L);
        } else {
            return beginDate.withDayOfMonth(beginDate.lengthOfMonth());
        }
    }

    /**
     * Start inclusive, end exclusive
     */
    public boolean isActive(final TrainingGoalDto dto, final LocalDate now) {
        final LocalDate begin = dto.getBegin();
        return (now.equals(begin) || now.isAfter(begin)) && now.isBefore(dto.getEnd());
    }
}
