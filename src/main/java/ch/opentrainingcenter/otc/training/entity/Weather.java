package ch.opentrainingcenter.otc.training.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "WEATHER")
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
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getWetter() {
        return wetter;
    }

    public void setWetter(final String wetter) {
        this.wetter = wetter;
    }

    public String getImageicon() {
        return imageicon;
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
