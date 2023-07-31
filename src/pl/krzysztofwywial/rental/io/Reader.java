package pl.krzysztofwywial.rental.io;

import pl.krzysztofwywial.rental.model.Car;
import pl.krzysztofwywial.rental.model.Motorcycle;
import pl.krzysztofwywial.rental.model.RentalUser;

import java.util.Scanner;

public class Reader {

    private Scanner sc = new Scanner(System.in);
    private ConsolePrinter printer;

    public Reader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public Car readAndCreateCar() {
        printer.printLine("Podaj numer vin:");
        String vin = sc.nextLine();
        printer.printLine("Podaj markę:");
        String brand = sc.nextLine();
        printer.printLine("Podaj model:");
        String model = sc.nextLine();
        printer.printLine("Podaj rodzaj silnika:");
        String engine = sc.nextLine();
        printer.printLine("Podaj rodzaj nadwozia");
        String carBody = sc.nextLine();
        printer.printLine("Podaj liczbę drzwi:");
        int doorsAmount = getInt();
        printer.printLine("Podaj rok produkcji");
        int year = getInt();
        printer.printLine("Podaj cenę:");
        int price = getInt();
        return new Car(vin, brand, model, engine, year, price, carBody, doorsAmount);
    }

    public RentalUser createRentalUser() {
        printer.printLine("Podaj imię");
        String firstName = sc.nextLine();
        printer.printLine("Podaj nazwisko");
        String lastName = sc.nextLine();
        printer.printLine("Podaj numer pesel");
        String pesel = sc.nextLine();
        return new RentalUser(firstName, lastName, pesel);
    }


    public Motorcycle readAndCreateMotor() {
        printer.printLine("Podaj numer vin:");
        String vin = sc.nextLine();
        printer.printLine("Podaj markę:");
        String brand = sc.nextLine();
        printer.printLine("Podaj model:");
        String model = sc.nextLine();
        printer.printLine("Podaj rodzaj silnika:");
        String engine = sc.nextLine();
        printer.printLine("Podaj rok produkcji");
        int year = getInt();
        printer.printLine("Podaj cenę:");
        int price = getInt();
        return new Motorcycle(vin, brand, model, engine, year, price);
    }

    public int getInt() {
        try {
            return sc.nextInt();
        } finally {
            sc.nextLine();
        }
    }

    public String getString() {
        return sc.nextLine();
    }

    public void close() {
        sc.close();
    }

}
