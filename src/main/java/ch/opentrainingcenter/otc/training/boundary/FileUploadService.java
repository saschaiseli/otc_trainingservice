package ch.opentrainingcenter.otc.training.boundary;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.opentrainingcenter.otc.training.domain.Athlete;
import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.repository.AthleteRepository;
import ch.opentrainingcenter.otc.training.repository.TrainingRepository;
import ch.opentrainingcenter.otc.training.service.converter.fit.GarminConverter;

/**
 */
@Path("/upload")
public class FileUploadService {

	public FileUploadService() {
	}

	@Inject
	private TrainingRepository trainingRepo;

	@Inject
	private AthleteRepository athleteRepo;

	@Context
	private UriInfo context;

	/**
	 * Returns text response to caller containing uploaded file location
	 *
	 * @return error response in case of missing parameters an internal exception or
	 *         success response if file has been stored successfully
	 */
	@Inject
	private GarminConverter garminConverter;

	@GET
	public Response doGet() {
		return Response.status(200).entity("doGet ").build();
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(final MultipartFormDataInput input) {
		final Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		final List<InputPart> inputParts = uploadForm.get("file");
		final InputPart inputPart = inputParts.get(0);
		try {
			final InputStream inputStream = getInputStream(inputPart);
			final Training training = convertAndStoreTraining(inputStream);
			final String json = createResult(training);
			return Response.status(200).entity(json).build();
		} catch (final Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	private Training convertAndStoreTraining(final InputStream inputStream) {
		final Training training = garminConverter.convert(inputStream);
		final Athlete athlete = getOrCreateAthlete();
		training.setAthlete(athlete);
		trainingRepo.doSave(training).getId();
		return training;
	}

	private InputStream getInputStream(final InputPart inputPart) throws IOException {
		final MultivaluedMap<String, String> header = inputPart.getHeaders();
		final String fileName = getFileName(header);
		final InputStream inputStream = inputPart.getBody(InputStream.class, null);
		return inputStream;
	}

	private String createResult(final Training training) throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		final String json = mapper.writeValueAsString(new SimpleTraining(training));
		return json;
	}

	private Athlete getOrCreateAthlete() {
		Athlete athlete = athleteRepo.findByEmail("sascha.iseli@gmx.ch");
		if (athlete == null) {
			athlete = new Athlete("sascha", "iseli", "sascha.iseli@gmx.ch", "secret");
			athlete = athleteRepo.doSave(athlete);
		}
		return athlete;
	}

	private String getFileName(final MultivaluedMap<String, String> header) {

		final String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (final String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				final String[] name = filename.split("=");

				final String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

}