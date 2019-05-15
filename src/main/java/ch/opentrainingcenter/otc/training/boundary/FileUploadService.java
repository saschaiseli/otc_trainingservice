package ch.opentrainingcenter.otc.training.boundary;

import ch.opentrainingcenter.otc.training.boundary.security.JWTService;
import ch.opentrainingcenter.otc.training.boundary.security.JWTTokenNeeded;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.entity.raw.Training;
import ch.opentrainingcenter.otc.training.events.EventAnnotations.Created;
import ch.opentrainingcenter.otc.training.events.TrainingEvent;
import ch.opentrainingcenter.otc.training.service.converter.fit.GarminConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Path("/upload")
@Slf4j
@JWTTokenNeeded
public class FileUploadService {

    @Inject
    @Created
    protected Event<TrainingEvent> newTrainingEvent;
    @Inject
    protected JWTService jwtService;
    @Context
    protected UriInfo context;
    @Inject
    protected GarminConverter garminConverter;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@Context final HttpHeaders httpHeaders, final MultipartFormDataInput input)
            throws IOException {
        log.info("upload a file");

        final Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        final List<InputPart> inputParts = uploadForm.get("file");

        final InputPart inputPart = inputParts.get(0);

        final InputStream inputStream = inputPart.getBody(InputStream.class, null);
        final MultivaluedMap<String, String> header = inputPart.getHeaders();
        final String fileName = getFileName(header);
        log.info("Upload file %s", fileName);

        final Training training = garminConverter.convert(inputStream);
        training.setFileName(fileName);
        final String email = jwtService.getClaims(httpHeaders).get("email", String.class);

        newTrainingEvent.fire(new TrainingEvent(training, email));
        final String json = createResult(training);
        log.info("File {} successfully uploaded and event fired", fileName);
        return Response.status(200).entity(json).build();

    }

    private String createResult(final Training training) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(new SimpleTraining(training));
    }

    private String getFileName(final MultivaluedMap<String, String> header) {

        final String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (final String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                final String[] name = filename.split("=");

                return name[1].trim().replaceAll("\"", "");
            }
        }
        return "unknown";
    }

}