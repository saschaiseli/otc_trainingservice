package ch.opentrainingcenter.otc.training.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Weather {

    @Id
    private long id;
    private String wetter;
    private String imageicon;

    public Weather() {
    }

    public Weather(final int id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getWetter() {
        return this.wetter;
    }

    public void setWetter(final String wetter) {
        this.wetter = wetter;
    }

    public String getImageicon() {
        return this.imageicon;
    }

    public void setImageicon(final String imageicon) {
        this.imageicon = imageicon;
    }

    @Override
    @SuppressWarnings("nls")

    public String toString() {
        return "Weather [wetter=" + wetter + ", imageicon=" + imageicon + "]";
    }
}
