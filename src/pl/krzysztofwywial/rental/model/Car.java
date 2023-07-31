package pl.krzysztofwywial.rental.model;

import java.util.Objects;

public class Car extends Vehicle {
    public static final String TYPE = "Samochód";
    private String carBody;
    private int doorsAmount;

    public Car(String vin, String brand, String model, String engine, int year, int price,
               String carBody, int doorsAmount) {
        super(vin, brand, model, engine, year, price);
        this.carBody = carBody;
        this.doorsAmount = doorsAmount;
    }

    @Override
    public String toCsv() {
        return TYPE + ";" + getVin() + ";" + getBrand() + ";" + getModel() + ";" +
                getEngine() + ";" + getYear() + ";" + getPrice()
                + ";" + carBody + ";" + doorsAmount;
    }

    @Override
    public String toString() {
        return super.toString() + ", rodzaj nadwozia: " + carBody + ", ilość drzwi: " + doorsAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return doorsAmount == car.doorsAmount && Objects.equals(carBody, car.carBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), carBody, doorsAmount);
    }
}
