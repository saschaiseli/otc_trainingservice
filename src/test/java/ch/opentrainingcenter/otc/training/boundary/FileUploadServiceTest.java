package ch.opentrainingcenter.otc.training.boundary;

import ch.opentrainingcenter.otc.training.TestConfig;
import ch.opentrainingcenter.otc.training.boundary.security.JWTService;
import ch.opentrainingcenter.otc.training.events.TrainingEvent;
import ch.opentrainingcenter.otc.training.service.converter.fit.GarminConverter;
import io.jsonwebtoken.Claims;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

class FileUploadServiceTest {

    private static final String MAIL = "test@mail.ch";
    private FileUploadService service;
    @Mock
    private UriInfo context;

    private final DummyEvent event = new DummyEvent();
    @Mock
    private HttpHeaders httpHeaders;
    @Mock
    private MultipartFormDataInput input;
    final File file = new File(TestConfig.FOLDER, TestConfig.FIT_FILE);
    @Mock
    private JWTService jwtService;
    private final String expectedJson = "{\"id\":null,\"start\":1476272557000,\"timeInSeconds\":5641,\"distanceInKm\":15.71,\"avgHeartBeat\":154,\"maxHeartBeat\":196,\"trainingEffect\":\"41\",\"anaerobTrainingEffect\":\"-\",\"pace\":\"10.0\"}";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new FileUploadService();
        service.context = context;
        service.garminConverter = new GarminConverter();
        service.jwtService = jwtService;
        service.newTrainingEvent = event;
    }

    @Test
    public void testUploadFile() throws IOException {
        // Given
        final Map<String, List<InputPart>> uploadForm = new HashMap<>();
        final List<InputPart> inputParts = new ArrayList<>();
        final InputPart inputPart = Mockito.mock(InputPart.class);
        final InputStream inputStream = new FileInputStream(file);
        when(inputPart.getBody(InputStream.class, null)).thenReturn(inputStream);
        inputParts.add(inputPart);
        uploadForm.put("file", inputParts);
        when(input.getFormDataMap()).thenReturn(uploadForm);

        @SuppressWarnings("unchecked") final MultivaluedMap<String, String> headers = Mockito.mock(MultivaluedMap.class);
        final String contentDisposition = "filename=" + TestConfig.FIT_FILE + ";schnabber=abc";
        when(headers.getFirst("Content-Disposition")).thenReturn(contentDisposition);
        when(inputPart.getHeaders()).thenReturn(headers);

        final Claims claims = Mockito.mock(Claims.class);
        when(claims.get("email", String.class)).thenReturn(MAIL);
        when(jwtService.getClaims(httpHeaders)).thenReturn(claims);

        // When
        final Response response = service.uploadFile(httpHeaders, input);

        // Then
        final TrainingEvent trainingEvent = event.getEvent();
        assertThat(trainingEvent.getEmail(), is(Matchers.equalTo(MAIL)));
        assertThat(trainingEvent.getTraining().getFileName(), is(Matchers.equalTo(TestConfig.FIT_FILE)));

        assertThat(response.getHeaders(), equalTo(Collections.EMPTY_MAP));
        assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_OK)));
        assertThat(((String) response.getEntity()), is(equalTo(expectedJson)));
    }

}
