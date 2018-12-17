package ch.opentrainingcenter.otc.training.domain.converter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalDateAttributeConverterTest {

	private LocalDateAttributeConverter converter;

	@BeforeEach
	public void setUp() {
		converter = new LocalDateAttributeConverter();
	}

	@Test
	public void testConvertToDatabaseColumn() {
		final LocalDate ld = LocalDate.of(2018, 8, 29);

		final Date converted = converter.convertToDatabaseColumn(ld);

		assertThat(converted, is(equalTo(Date.valueOf(ld))));
	}

	@Test
	public void testConvertToEntityAttribute() {
		final LocalDate ld = LocalDate.of(2018, 8, 29);
		final Date date = Date.valueOf(ld);

		final LocalDate converted = converter.convertToEntityAttribute(date);

		assertThat(converted, is(equalTo(ld)));
	}
}
