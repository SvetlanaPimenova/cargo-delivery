package ua.pimenova.model.database.entity;

public class Freight {
    private int id;
    private double weight;
    private double length;
    private double width;
    private double height;
    private int estimatedCost;
    private FreightType type;

    public Freight() {
    }

    public Freight(int id, double weight, double length, double width, double height, int estimatedCost, FreightType type) {
        this.id = id;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.estimatedCost = estimatedCost;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(int estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public FreightType getType() {
        return type;
    }

    public void setType(FreightType type) {
        this.type = type;
    }

    public enum FreightType {
        GOODS(1, 1.0),
        GLASS(2, 1.5),
        COMPACT(3, 1.0);

        private double coefficient;
        private int id;
        FreightType(int id, double coefficient) {
            this.id = id;
            this.coefficient = coefficient;
        }

        public int getId() {return id;}

        public double getCoefficientOfFreightType() {
            return coefficient;
        }

        public static FreightType getTypeById(int id) {
            if(id == 1) {
                return GOODS;
            } else if (id == 2) {
                return GLASS;
            }
            return COMPACT;
        }
    }

    @Override
    public String toString() {
        return type.toString() + ", " + weight + " kg,\n" + length + "x" + width + "x" +
                height + " cm,\n" +
                "Estimated cost: " + estimatedCost + " UAH";
    }

    public static class FreightFields {
        public static final String ID = "id";
        public static final String WEIGHT = "weight";
        public static final String LENGTH = "length";
        public static final String WIDTH = "width";
        public static final String HEIGHT = "height";
        public static final String ESTIMATED_COST = "estimated_cost";
        public static final String FREIGHT_TYPE_NAME = "name";
    }
}
