package ch.opentrainingcenter.otc.training.events;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

public class EventAnnotations {
	@Qualifier
	@Target({ FIELD, PARAMETER })
	@Retention(RUNTIME)
	public @interface Created {
	}

	@Qualifier
	@Target({ FIELD, PARAMETER })
	@Retention(RUNTIME)
	public @interface Updated {
	}

	@Qualifier
	@Target({ FIELD, PARAMETER })
	@Retention(RUNTIME)
	public @interface Deleted {
	}
}
