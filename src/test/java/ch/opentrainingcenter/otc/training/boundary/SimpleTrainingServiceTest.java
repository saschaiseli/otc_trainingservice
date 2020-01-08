package ch.opentrainingcenter.otc.training.boundary;

import ch.opentrainingcenter.otc.training.boundary.security.JWTService;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import io.jsonwebtoken.Claims;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ch.opentrainingcenter.otc.training.boundary.SimpleTrainingService.DATE_PATTERN;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

class SimpleTrainingServiceTest {

    private SimpleTrainingService service;
    @Mock
    private TrainingRepository trainingRepo;
    @Mock
    private HttpHeaders httpHeaders;
    @Mock
    private JWTService jwtService;
    @Mock
    private Claims claims;

    private final long athleteId = 42L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new SimpleTrainingService();
        service.dao = trainingRepo;
        service.jwtService = jwtService;
    }

    @Test
    void testGetSimpleTrainingByAthlete() {

        final List<SimpleTraining> trainings = new ArrayList<>();
        final SimpleTraining simpleTraining = Mockito.mock(SimpleTraining.class);
        trainings.add(simpleTraining);
        final String start = "06-01-2020";
        final String end = "13-01-2020";
        final LocalDate startDate = LocalDate.parse(start, ofPattern(DATE_PATTERN));
        final LocalDate endDate = LocalDate.parse(end, ofPattern(DATE_PATTERN));
        when(trainingRepo.findByAthleteAndDate(athleteId, startDate, endDate)).thenReturn(trainings);
        when(jwtService.getClaims(httpHeaders)).thenReturn(claims);
        when(claims.get("id", Long.class)).thenReturn(athleteId);

        // When
        final Response response = service.getSimpleTrainingByAthlete(httpHeaders, start, end);

        // Then
        assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
        @SuppressWarnings("unchecked") final List<SimpleTraining> entities = (List<SimpleTraining>) response.getEntity();
        assertThat(entities, Matchers.contains(simpleTraining));
        assertThat(entities.size(), is(1));
    }

    @Test
    void testGetSimpleTrainingByAthlete_invalidStartDate() {

        final List<SimpleTraining> trainings = new ArrayList<>();
        final SimpleTraining simpleTraining = Mockito.mock(SimpleTraining.class);
        trainings.add(simpleTraining);
        final String start = "invalid";
        final String end = "13-01-2020";
        final LocalDate startDate = LocalDate.MIN;
        final LocalDate endDate = LocalDate.parse(end, ofPattern(DATE_PATTERN));
        when(trainingRepo.findByAthleteAndDate(athleteId, startDate, endDate)).thenReturn(trainings);
        when(jwtService.getClaims(httpHeaders)).thenReturn(claims);
        when(claims.get("id", Long.class)).thenReturn(athleteId);


        // When
        final Response response = service.getSimpleTrainingByAthlete(httpHeaders, start, end);

        // Then
        assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_INTERNAL_SERVER_ERROR)));
    }

    @Test
    void testGetSimpleTrainingByAthlete_invalidEndDate() {

        final List<SimpleTraining> trainings = new ArrayList<>();
        final SimpleTraining simpleTraining = Mockito.mock(SimpleTraining.class);
        trainings.add(simpleTraining);
        final String start = "13-01-2020";
        final String end = "invalid";
        final LocalDate startDate = LocalDate.parse(start, ofPattern(DATE_PATTERN));
        final LocalDate endDate = LocalDate.MAX;
        when(trainingRepo.findByAthleteAndDate(athleteId, startDate, endDate)).thenReturn(trainings);
        when(jwtService.getClaims(httpHeaders)).thenReturn(claims);
        when(claims.get("id", Long.class)).thenReturn(athleteId);


        // When
        final Response response = service.getSimpleTrainingByAthlete(httpHeaders, start, end);

        // Then
        assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_INTERNAL_SERVER_ERROR)));
    }

    @Test
    void testGetSimpleTrainingByAthlete_invalidDate() {

        final List<SimpleTraining> trainings = new ArrayList<>();
        final SimpleTraining simpleTraining = Mockito.mock(SimpleTraining.class);
        trainings.add(simpleTraining);
        final String start = "invalid";
        final String end = "invalid";
        final LocalDate startDate = LocalDate.MIN;
        final LocalDate endDate = LocalDate.MAX;
        when(trainingRepo.findByAthleteAndDate(athleteId, startDate, endDate)).thenReturn(trainings);
        when(jwtService.getClaims(httpHeaders)).thenReturn(claims);
        when(claims.get("id", Long.class)).thenReturn(athleteId);


        // When
        final Response response = service.getSimpleTrainingByAthlete(httpHeaders, start, end);

        // Then
        assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_INTERNAL_SERVER_ERROR)));
    }

    @Test
    void testGetSimpleTrainingByAthlete_nullDate() {

        final List<SimpleTraining> trainings = new ArrayList<>();
        final SimpleTraining simpleTraining = Mockito.mock(SimpleTraining.class);
        trainings.add(simpleTraining);
        final String start = null;
        final String end = "13-01-2020";
        final LocalDate startDate = LocalDate.MIN;
        final LocalDate endDate = LocalDate.parse(end, ofPattern(DATE_PATTERN));
        when(trainingRepo.findByAthleteAndDate(athleteId, startDate, endDate)).thenReturn(trainings);
        when(jwtService.getClaims(httpHeaders)).thenReturn(claims);
        when(claims.get("id", Long.class)).thenReturn(athleteId);


        // When
        final Response response = service.getSimpleTrainingByAthlete(httpHeaders, start, end);

        // Then
        assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
        @SuppressWarnings("unchecked") final List<SimpleTraining> entities = (List<SimpleTraining>) response.getEntity();
        assertThat(entities, Matchers.contains(simpleTraining));
        assertThat(entities.size(), is(1));
    }

    @Test
    void testExistsTrainingYes() {
        // Given
        final String fileName = "abcdef.fit";
        when(trainingRepo.existsFile(athleteId, fileName)).thenReturn(true);
        when(jwtService.getClaims(httpHeaders)).thenReturn(claims);
        when(claims.get("id", Long.class)).thenReturn(athleteId);

        // When
        final Response response = service.existsTraining(httpHeaders, fileName);

        assertThat(response.getStatus(), is(HttpStatus.SC_OK));
        assertThat(((Boolean) response.getEntity()), is(true));
    }

    @Test
    void testExistsTrainingNo() {
        // Given
        final String fileName = "abcdef.fit";
        when(trainingRepo.existsFile(athleteId, fileName)).thenReturn(false);
        when(jwtService.getClaims(httpHeaders)).thenReturn(claims);
        when(claims.get("id", Long.class)).thenReturn(athleteId);
        // When
        final Response response = service.existsTraining(httpHeaders, fileName);

        assertThat(response.getStatus(), is(HttpStatus.SC_OK));
        assertThat(((Boolean) response.getEntity()), is(false));
    }
}
