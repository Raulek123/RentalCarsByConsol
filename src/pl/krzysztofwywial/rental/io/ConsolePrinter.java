package pl.krzysztofwywial.rental.io;

import pl.krzysztofwywial.rental.model.Car;
import pl.krzysztofwywial.rental.model.Motorcycle;
import pl.krzysztofwywial.rental.model.RentalUser;
import pl.krzysztofwywial.rental.model.Vehicle;

import java.util.Collection;

public class ConsolePrinter {
    public void printCars(Collection<Vehicle> vehicles) {
        long count = vehicles.stream()
                .filter(v -> v instanceof Car)
                .map(Vehicle::toString)
                .peek(this::printLine)
                .count();
        if (count == 0) {
            printLine("Brak dostępnych samochodów");

        }
    }

    public void printMotorcycle(Collection<Vehicle> vehicles) {
        long count = vehicles.stream()
                .filter(v -> v instanceof Motorcycle)
                .map(Vehicle::toString)
                .peek(this::printLine)
                .count();
        if (count == 0) {
            printLine("Brak dostępnych motocykli");

        }
    }

    public void printUsers(Collection<RentalUser> users) {
        for (RentalUser user : users) {
            printLine(user.toString());
        }
        if (users.isEmpty()) {
            printLine("Brak wynajmujących");
        }
    }

    public void printLine(String text) {
        System.out.println(text);
    }
}
