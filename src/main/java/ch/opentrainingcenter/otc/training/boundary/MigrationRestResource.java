package ch.opentrainingcenter.otc.training.boundary;

import ch.opentrainingcenter.otc.training.service.db.DataMigration;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URISyntaxException;

@Path("/migration")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MigrationRestResource {

    @Inject
    DataMigration migration;

    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAthlete() throws IOException, URISyntaxException {
        migration.migrate();
        return Response.status(200).build();
    }
}
