package ch.opentrainingcenter.otc.training.boundary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.opentrainingcenter.otc.training.boundary.security.JWTService;
import ch.opentrainingcenter.otc.training.boundary.security.JWTTokenNeeded;
import ch.opentrainingcenter.otc.training.domain.GoalDuration;
import ch.opentrainingcenter.otc.training.domain.TargetUnit;
import ch.opentrainingcenter.otc.training.domain.TrainingGoal;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.dto.TrainingGoalDto;
import ch.opentrainingcenter.otc.training.events.EventAnnotations.Created;
import ch.opentrainingcenter.otc.training.events.EventAnnotations.Updated;
import ch.opentrainingcenter.otc.training.repository.TrainingGoalRepository;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import ch.opentrainingcenter.otc.training.service.goal.GoalProgressCalculator;
import ch.opentrainingcenter.otc.training.service.goal.TrainingGoalDateCalculator;
import lombok.extern.slf4j.Slf4j;

@Path("/targets")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@JWTTokenNeeded
public class TrainingGoalService {

	@Inject
	protected JWTService jwtService;
	@Inject
	protected GoalProgressCalculator calculator;
	@Inject
	protected TrainingGoalDateCalculator dateCalculator;
	@Inject
	protected TrainingGoalRepository repository;
	@Inject
	protected TrainingRepository trainingRepo;

	@Inject
	@Created
	protected Event<TrainingGoalDto> newTrainingGoalEvent;

	@Inject
	@Updated
	protected Event<TrainingGoalDto> updatedTrainingGoalEvent;

	@GET
	public Response getTrainingGoals(@Context final HttpHeaders httpHeaders) {
		final Long athleteId = jwtService.getClaims(httpHeaders).get("id", Long.class);
		log.info("get targets from Athlete {}", athleteId);
		final List<TrainingGoal> targets = repository.findByAthlete(athleteId);
		final List<TrainingGoalDto> dtos = targets.stream().map(TrainingGoalDto::new).collect(Collectors.toList());
		return Response.status(200).entity(dtos).build();
	}

	@POST
	public Response addTarget(@Context final HttpHeaders httpHeaders, final Map<String, String> datas) {
		final Long athleteId = jwtService.getClaims(httpHeaders).get("id", Long.class);
		log.info("add targets");
		final String id = datas.get("id");
		final String text = datas.get("begin");
		final GoalDuration duration = GoalDuration.valueOfFromClient(datas.get("duration"));
		final TargetUnit targetUnit = TargetUnit.valueOfFromClient(datas.get("unit"));
		final Integer distanceOrHour = Integer.valueOf(datas.get("distanceOrHour"));

		final LocalDate beginLocalDate = getBeginLocalDate(text);

		// create DTO
		final LocalDate begin = dateCalculator.getBeginDate(beginLocalDate, duration);
		final LocalDate end = dateCalculator.getEndDate(beginLocalDate, duration);
		final TrainingGoalDto dto = new TrainingGoalDto(distanceOrHour, targetUnit, duration, begin, end);
		final List<SimpleTraining> trainings = trainingRepo.findByAthleteAndDate(athleteId, dto.getBegin(),
				dto.getEnd());
		final double currentValue = calculator.calculateTrainingGoalProgress(dto, trainings);
		dto.setCurrentValue(currentValue);
		dto.setActive(dateCalculator.isActive(dto, LocalDate.now()));
		dto.setAthleteId(athleteId);
		if (id == null) {
			newTrainingGoalEvent.fire(dto);
		} else {
			updatedTrainingGoalEvent.fire(dto);
		}
		return Response.status(200).entity(dto).build();
	}

	private LocalDate getBeginLocalDate(final String text) {
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		final LocalDateTime beginLocalDateTime = LocalDateTime.parse(text, dtf);
		return beginLocalDateTime.toLocalDate();
	}

}
