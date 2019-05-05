package ch.opentrainingcenter.otc.training.entity.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class LocalDateTimeAttributeConverterTest {

    private LocalDateTimeAttributeConverter converter;

    @BeforeEach
    public void setUp() {
        converter = new LocalDateTimeAttributeConverter();
    }

    @Test
    public void testConvertToDatabaseColumn() {
        final LocalDateTime ldt = LocalDateTime.of(2018, 8, 29, 14, 59);

        final Timestamp converted = converter.convertToDatabaseColumn(ldt);

        assertThat(converted, is(equalTo(Timestamp.valueOf(ldt))));
    }

    @Test
    public void testConvertToEntityAttribute() {
        final LocalDateTime ldt = LocalDateTime.of(2018, 8, 29, 14, 59);
        final Timestamp time = Timestamp.valueOf(ldt);

        final LocalDateTime converted = converter.convertToEntityAttribute(time);

        assertThat(converted, is(equalTo(ldt)));
    }
}
