package ch.opentrainingcenter.otc.training.service.json;

import ch.opentrainingcenter.otc.training.TestConfig;
import ch.opentrainingcenter.otc.training.entity.CommonTransferFactory;
import ch.opentrainingcenter.otc.training.entity.raw.Tracktrainingproperty;
import ch.opentrainingcenter.otc.training.entity.raw.Training;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

class TrackPointSerializerTest {

    private static final String HEART = "[{\"name\":\"Herzfrequenz\",\"series\":[{\"name\":0,\"value\":20},{\"name\":1,\"value\":40}]}]";
    private static final String HEART_EMPTY = "[{\"name\":\"Herzfrequenz\",\"series\":[]}]";
    private Training training;

    @BeforeEach
    public void setUp() throws JsonParseException, JsonMappingException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final File jsonTraining = new File(TestConfig.FOLDER + "/training.json");
        training = objectMapper.readValue(jsonTraining, Training.class);
    }

    @Test
    void testSerializeHeart() throws IOException {
        final List<Tracktrainingproperty> trackPoints = new ArrayList<>();
        trackPoints.add(createHeartTrackPoint(0, 20));//
        trackPoints.add(createHeartTrackPoint(1, 40));//
        training.setTrackPoints(trackPoints);

        final ObjectMapper mapper = new ObjectMapper();
        final SimpleModule module = new SimpleModule();
        module.addSerializer(Training.class,
                new TrackPointSerializer("Herzfrequenz", x -> BigDecimal.valueOf(x.getHeartBeat())));
        mapper.registerModule(module);

        final String serialized = mapper.writeValueAsString(training);

        assertThat(serialized, equalTo(HEART));
    }

    @Test
    void testSerializeEmptyHeart() throws IOException {
        training.setTrackPoints(Collections.emptyList());

        final ObjectMapper mapper = new ObjectMapper();
        final SimpleModule module = new SimpleModule();
        module.addSerializer(Training.class,
                new TrackPointSerializer("Herzfrequenz", x -> BigDecimal.valueOf(x.getHeartBeat())));
        mapper.registerModule(module);

        final String serialized = mapper.writeValueAsString(training);

        assertThat(serialized, equalTo(HEART_EMPTY));
    }

    private Tracktrainingproperty createHeartTrackPoint(final int dist, final int heart) {
        return CommonTransferFactory.createTrackPointProperty(dist, heart, 0, 0, 0, 0d, 0d);
    }
}
