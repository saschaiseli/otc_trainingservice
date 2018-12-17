package ch.opentrainingcenter.otc.training.domain.converter;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn(final LocalDate ld) {
		return (ld == null ? null : Date.valueOf(ld));
	}

	@Override
	public LocalDate convertToEntityAttribute(final Date date) {
		return (date == null ? null : date.toLocalDate());
	}

}
