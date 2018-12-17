package ch.opentrainingcenter.otc.training.service.converter.fit;

import java.io.InputStream;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import com.garmin.fit.Decode;

import ch.opentrainingcenter.otc.training.domain.raw.Training;

@SessionScoped
public class GarminConverter implements Serializable {

	private static final String FIT = "fit";
	private static final long serialVersionUID = 5169027790370807110L;

	public String getFilePrefix() {
		return FIT;
	}

	public Training convert(final InputStream inputStream) {
		final TrainingListener listener = new TrainingListener();
		final Decode decode = new Decode();
		decode.read(inputStream, listener);
		return listener.getTraining();
	}

}
