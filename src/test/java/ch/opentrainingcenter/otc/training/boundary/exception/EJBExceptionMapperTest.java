package ch.opentrainingcenter.otc.training.boundary.exception;

import ch.opentrainingcenter.otc.training.entity.Athlete;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ejb.EJBException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

class EJBExceptionMapperTest {
    private EJBExceptionMapper mapper;
    @Mock
    private Athlete athlete;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mapper = new EJBExceptionMapper();
    }

    @Test
    void testToResponseOptimisticLockException() {
        final EJBException ejbException = new EJBException(new OptimisticLockException(athlete));

        final Response response = mapper.toResponse(ejbException);

        assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_CONFLICT)));
    }

    @Test
    void testToResponsePersistenceException() {
        final EJBException ejbException = new EJBException(new PersistenceException());

        final Response response = mapper.toResponse(ejbException);

        assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_BAD_REQUEST)));
    }

    @Test
    void testToResponseOtherException() {
        final EJBException ejbException = new EJBException(new NullPointerException());

        final Response response = mapper.toResponse(ejbException);

        assertThat(response.getStatus(), is(equalTo(HttpStatus.SC_INTERNAL_SERVER_ERROR)));
    }
}
