package ua.pimenova.model.database.entity;

public class Rate {
    private double weight;
    private int fare;

    public Rate(double weight, int fare) {
        this.weight = weight;
        this.fare = fare;
    }

    public Rate() {
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public static class RateFields {
        public static final String WEIGHT = "weight";
        public static final String FARE = "fare";
    }
}
