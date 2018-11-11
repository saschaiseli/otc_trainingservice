package ch.opentrainingcenter.otc.training.domain.raw;

public enum Sport {

	RUNNING("training.raw.running"), BIKING("training.raw.biking"), OTHER("training.raw.other");

	private String message;

	private Sport(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
