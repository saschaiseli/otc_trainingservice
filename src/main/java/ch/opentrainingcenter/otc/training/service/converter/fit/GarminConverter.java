package ch.opentrainingcenter.otc.training.service.converter.fit;

import ch.opentrainingcenter.otc.training.entity.raw.Training;
import com.garmin.fit.Decode;

import javax.ejb.Stateless;
import java.io.InputStream;
import java.io.Serializable;

@Stateless
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
