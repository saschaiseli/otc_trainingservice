package ch.opentrainingcenter.otc.training.boundary;

import java.lang.annotation.Annotation;
import java.util.concurrent.CompletionStage;

import javax.enterprise.event.Event;
import javax.enterprise.event.NotificationOptions;
import javax.enterprise.util.TypeLiteral;

import ch.opentrainingcenter.otc.training.events.TrainingEvent;

public class DummyEvent implements Event<TrainingEvent> {

	private TrainingEvent event;

	@Override
	public void fire(final TrainingEvent event) {
		this.setEvent(event);
	}

	@Override
	public <U extends TrainingEvent> CompletionStage<U> fireAsync(final U event) {
		return null;
	}

	@Override
	public <U extends TrainingEvent> CompletionStage<U> fireAsync(final U event, final NotificationOptions options) {
		return null;
	}

	@Override
	public Event<TrainingEvent> select(final Annotation... qualifiers) {
		return null;
	}

	@Override
	public <U extends TrainingEvent> Event<U> select(final Class<U> subtype, final Annotation... qualifiers) {
		return null;
	}

	@Override
	public <U extends TrainingEvent> Event<U> select(final TypeLiteral<U> subtype, final Annotation... qualifiers) {
		return null;
	}

	public TrainingEvent getEvent() {
		return event;
	}

	public void setEvent(TrainingEvent event) {
		this.event = event;
	}

}
