package ch.opentrainingcenter.otc.training.entity;

public class HeartRate {
    private final int average;
    private final int max;

    public HeartRate(final int average, final int max) {
        this.average = average;
        this.max = max;
    }

    public int getAverage() {
        return average;
    }

    public int getMax() {
        return max;
    }

}
