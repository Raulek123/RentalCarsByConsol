package pl.krzysztofwywial.rental.model;

import java.io.Serializable;
import java.time.Year;
import java.util.Objects;

public abstract class Vehicle implements Serializable, Comparable<Vehicle>, CsvConvertible {
    private String vin;
    private String brand;
    private String model;
    private String engine;
    private Year year;
    private int price;

    public Vehicle(String vin, String brand, String model, String engine, int year, int price) {
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.engine = engine;
        this.year = Year.of(year);
        this.price = price;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public int getPrice() {

        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Model: " + brand + " " + model + ", silnik: " + engine + ", rok produkcji: " + year
                + ", cena: " + price + " zł, numer vin: " + vin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return price == vehicle.price && Objects.equals(vin, vehicle.vin) && Objects.equals(brand, vehicle.brand)
                && Objects.equals(model, vehicle.model) && Objects.equals(engine,
                vehicle.engine) && Objects.equals(year, vehicle.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, brand, model, engine, year, price);
    }

    @Override
    public int compareTo(Vehicle o) {
//        int brandCompare = brand.compareToIgnoreCase(o.brand);
//        if (brandCompare !=0)
//            return brandCompare;
        return brand.compareToIgnoreCase(o.brand);
    }
}
