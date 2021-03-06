package ch.opentrainingcenter.otc.training.boundary;

import ch.opentrainingcenter.otc.training.entity.Athlete;
import ch.opentrainingcenter.otc.training.entity.CommonTransferFactory;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class AthleteServiceTest {

    private AthleteService service;
    @Mock
    private AthleteRepository dao;
    private final String firstName = "firstName";
    private final String lastName = "lastName";
    private final String email = "email";
    private final String password = "password";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new AthleteService();
        service.dao = dao;
    }

    @Test
    void testGetAthlete() {
        // Given
        final Athlete athlete = CommonTransferFactory.createAthleteHashedPass(firstName, lastName, email, password);
        final long athleteId = 42L;
        athlete.setId(athleteId);

        Mockito.when(dao.find(Athlete.class, athleteId)).thenReturn(athlete);

        // When
        final Response response = service.getAthlete(athleteId);

        // Then
        Mockito.verify(dao).find(Athlete.class, athleteId);
        Mockito.verifyNoMoreInteractions(dao);
        assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
        final Athlete entity = (Athlete) response.getEntity();
        assertThat(entity, is(athlete));
        assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
    }

    @Test
    void testDeleteAthlete() {
        // Given
        final Athlete athlete = CommonTransferFactory.createAthleteHashedPass(firstName, lastName, email, password);
        final long athleteId = 42L;
        athlete.setId(athleteId);

        // When
        final Response response = service.deleteAthlete(athleteId);

        // Then
        Mockito.verify(dao).remove(Athlete.class, athleteId);
        Mockito.verifyNoMoreInteractions(dao);
        assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
        assertThat(response.getEntity(), Matchers.nullValue());
        assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
    }
}
