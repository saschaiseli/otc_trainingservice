package ch.opentrainingcenter.otc.training.service;

import ch.opentrainingcenter.otc.training.boundary.security.JWTService;
import ch.opentrainingcenter.otc.training.entity.Athlete;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.HttpHeaders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

class JWTAthleteServiceTest {
    private static final String EXPECTED = "test@test.ch";
    private JWTAthleteService service;
    @Mock
    private HttpHeaders headers;
    @Mock
    private AthleteRepository athleteRepo;
    @Mock
    private JWTService jwtService;
    @Mock
    private Claims claims;
    @Mock
    private Athlete athlete;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new JWTAthleteService();
        service.athleteRepo = athleteRepo;
        service.jwtService = jwtService;
    }

    @Test
    void testGetAthlete() {
        when(claims.get(JWTAthleteService.EMAIL, String.class)).thenReturn(EXPECTED);
        when(jwtService.getClaims(headers)).thenReturn(claims);
        when(athleteRepo.findByEmail(EXPECTED)).thenReturn(athlete);

        final Athlete result = service.getAthlete(headers);

        verify(athleteRepo).findByEmail(EXPECTED);
        verifyNoMoreInteractions(athleteRepo);

        assertThat(result, is(equalTo(athlete)));
    }

}
