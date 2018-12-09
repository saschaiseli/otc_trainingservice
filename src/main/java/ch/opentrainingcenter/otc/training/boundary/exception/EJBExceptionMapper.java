package ch.opentrainingcenter.otc.training.boundary.exception;

import javax.ejb.EJBException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {

	@Override
	public Response toResponse(final EJBException ex) {
		log.error("uiuiuiuiuiui");
		log.error("uiuiuiuiuiui");
		log.error("uiuiuiuiuiui");
		log.error("uiuiuiuiuiui");
		log.error("uiuiuiuiuiui");
		log.error("uiuiuiuiuiui");
		final Throwable cause = ex.getCause();
		if (cause instanceof OptimisticLockException) {
			final OptimisticLockException actual = (OptimisticLockException) cause;
			return Response.status(Response.Status.CONFLICT)
					.header("cause", "conflict caused by entity: " + actual.getEntity())
					.header("additional-info", actual.getMessage()).build();

		}
		if (cause instanceof PersistenceException) {
			final PersistenceException actual = (PersistenceException) cause;
			return Response.status(Response.Status.BAD_REQUEST)
					.header("cause", "conflict caused by entity: " + actual.getMessage())
					.header("additional-info", actual.getMessage()).build();

		}
		return Response.status(Response.Status.CONFLICT)
				.header("cause", "conflict caused by entity: " + cause.getMessage())//
				.header("schnabber", "gugues")//
				.header("additional-info", cause.getMessage())//
				.build();
	}

}
