package ch.opentrainingcenter.otc.training.boundary;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.enterprise.event.Event;
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

import ch.opentrainingcenter.otc.training.domain.raw.Training;
import ch.opentrainingcenter.otc.training.dto.SimpleTraining;
import ch.opentrainingcenter.otc.training.events.EventAnnotations.Created;
import ch.opentrainingcenter.otc.training.service.converter.fit.GarminConverter;
import lombok.extern.slf4j.Slf4j;

@Path("/upload")
@Slf4j
public class FileUploadService {

	public FileUploadService() {
	}

	@Inject
	@Created
	private Event<Training> newTrainingEvent;

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
		log.info("upload a file ----------------------_>");
		final Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		final List<InputPart> inputParts = uploadForm.get("file");

		final InputPart inputPart = inputParts.get(0);
		try {
			final InputStream inputStream = inputPart.getBody(InputStream.class, null);
			final MultivaluedMap<String, String> header = inputPart.getHeaders();
			final String fileName = getFileName(header);
			log.info("Upload file %s", fileName);

			final Training training = garminConverter.convert(inputStream);
			training.setFileName(fileName);

			newTrainingEvent.fire(training);
			final String json = createResult(training);
			log.info("File successfully uploaded {}", json);
			return Response.status(200).header("Access-Control-Allow-Origin", "*") //
					.header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
					.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")//
					.entity(json).build();
		} catch (final Exception e) {
			log.warn("ned guet", e);
			return Response.status(500)//
					.header("Access-Control-Allow-Origin", "*") //
					.header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
					.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")//
					.entity(e.getMessage()).build();
		}
	}

	private String createResult(final Training training) throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		final String json = mapper.writeValueAsString(new SimpleTraining(training));
		return json;
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