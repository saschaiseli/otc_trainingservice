package ch.opentrainingcenter.otc.training.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Data
@ToString(includeFieldNames = true)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Settings {

    @Enumerated(EnumType.STRING)
    private SystemOfUnit unit;

    @Enumerated(EnumType.STRING)
    private Speed speed;

}
