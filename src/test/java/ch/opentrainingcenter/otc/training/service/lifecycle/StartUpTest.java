package ch.opentrainingcenter.otc.training.service.lifecycle;

import ch.opentrainingcenter.otc.training.boundary.security.BCryptService;
import ch.opentrainingcenter.otc.training.entity.Athlete;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import ch.opentrainingcenter.otc.training.service.db.DBService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class StartUpTest {
    private static final String EMAIL = "sascha.iseli@gmx.ch";
    @InjectMocks
    private StartUp startUp;
    @Mock
    private AthleteRepository athleteRepo;
    @Mock
    private Athlete athlete;
    @Mock
    private DBService dbservice;
    @Mock
    private BCryptService cryptService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testUserExists() {
        // Given
        when(athleteRepo.findByEmail(EMAIL)).thenReturn(athlete);

        // when
        startUp.init();

        // then
        verify(athleteRepo).findByEmail(EMAIL);
        Mockito.verifyNoMoreInteractions(athleteRepo);
    }

    @Test
    void testUserDoesNotExists() {
        // Given
        when(athleteRepo.findByEmail(EMAIL)).thenReturn(null);

        // when
        startUp.init();

        // then
        verify(athleteRepo).findByEmail(EMAIL);
        verify(athleteRepo).doSave(any());
        Mockito.verifyNoMoreInteractions(athleteRepo);
    }
}
