package ch.opentrainingcenter.otc.training.service.json;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ch.opentrainingcenter.otc.training.domain.raw.Tracktrainingproperty;
import ch.opentrainingcenter.otc.training.domain.raw.Training;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrackPointSerializer extends StdSerializer<Training> {

	private static final long serialVersionUID = 1L;
	private final Function<Tracktrainingproperty, BigDecimal> function;
	private final String name;

	public TrackPointSerializer(final String name, final Function<Tracktrainingproperty, BigDecimal> function) {
		this(null, name, function);
	}

	public TrackPointSerializer(final Class<Training> c, final String name,
			final Function<Tracktrainingproperty, BigDecimal> function) {
		super(c);
		this.name = name;
		this.function = function;
	}

	@Override
	public void serialize(final Training t, final JsonGenerator gen, final SerializerProvider p) throws IOException {
		gen.writeStartArray();
		gen.writeStartObject();
		gen.writeStringField("name", name);

		gen.writeArrayFieldStart("series");
		t.getTrackPoints().forEach(x -> {
			try {
				gen.writeStartObject();
				gen.writeNumberField("name", (int) x.getDistance());
				gen.writeNumberField("value", function.apply(x));
				gen.writeEndObject();
			} catch (final IOException e) {
				log.error("WriteNumberField was not successful");
			}
		});
		gen.writeEndArray();
		gen.writeEndObject();
		gen.writeEndArray();
	}

}
