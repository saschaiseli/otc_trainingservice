package ch.opentrainingcenter.otc.training.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(final LocalDateTime ldt) {
        return (ldt == null ? null : Timestamp.valueOf(ldt));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(final Timestamp time) {
        return (time == null ? null : time.toLocalDateTime());
    }

}
