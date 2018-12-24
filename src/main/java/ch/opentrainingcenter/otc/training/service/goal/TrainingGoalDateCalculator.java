package ch.opentrainingcenter.otc.training.service.goal;

import java.time.LocalDate;

import javax.ejb.Stateless;

import ch.opentrainingcenter.otc.training.domain.GoalDuration;
import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;

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
			return beginDate.plusDays(duration.getDays() - 1);
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
