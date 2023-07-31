package pl.krzysztofwywial.rental.model;

public class Motorcycle extends Vehicle {
    public final static String TYPE = "Motocykl";

    public Motorcycle(String vin, String brand, String model, String engine, int year, int price) {
        super(vin, brand, model, engine, year, price);
    }

    @Override
    public String toCsv() {
        return TYPE + ";" + getVin() + ";" + getBrand() + ";" + getModel() + ";" +
                getEngine() + ";" + getYear() + ";" + getPrice();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}