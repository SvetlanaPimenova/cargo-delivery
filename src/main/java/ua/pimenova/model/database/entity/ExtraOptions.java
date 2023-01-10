package ua.pimenova.model.database.entity;

public class ExtraOptions {
    public enum DeliveryType {
        TO_THE_BRANCH(1, 1.0),
        COURIER(2, 2.0);

        private double coefficient;
        private int id;

        DeliveryType(int id, double coefficient) {
            this.id = id;
            this.coefficient = coefficient;
        }

        public int getId() {
            return id;
        }

        public double getCoefficientOfDeliveryType() {
            return coefficient;
        }

        public static DeliveryType getTypeById(int id) {
            if(id == 1) {
                return TO_THE_BRANCH;
            }
            return COURIER;
        }
    }

    public enum Direction {
        CITY(1.0),
        COUNTRY(1.3);
        double coefficient;

        Direction(double coefficient) {
            this.coefficient = coefficient;
        }

        public double getCoefficientOfDirection() {
            return coefficient;
        }
    }
}
