package pl.krzysztofwywial.rental.io.file;

import pl.krzysztofwywial.rental.exception.DataImportException;
import pl.krzysztofwywial.rental.exception.InvalidMidiDataException;
import pl.krzysztofwywial.rental.model.*;

import java.io.*;
import java.util.Collection;

public class CsvFileManager implements FileManager {
    private static final String FILE_NAME = "Rental.csv";
    private static final String USERS_FILE_NAME = "Rental_users.csv";

    @Override
    public Rental importData() {
        Rental rental = new Rental();
        importVehicles(rental);
        importUsers(rental);
        return rental;
    }

    private void importUsers(Rental rental) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_FILE_NAME))) {
            bufferedReader.lines()
                    .map(this::createUsertFromString)
                    .forEach(rental::addUser);
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku " + USERS_FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Błąd odczytu pliku " + USERS_FILE_NAME);
        }
    }

    private RentalUser createUsertFromString(String csvText) {
        String[] split = csvText.split(";");
        String firstName = split[0];
        String lastName = split[1];
        String pesel = split[2];
        return new RentalUser(firstName, lastName, pesel);
    }

    private void importVehicles(Rental rental) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            bufferedReader.lines()
                    .map(this::createObjectFromString)
                    .forEach(rental::addVehicle);
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku " + FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Błąd odczytu pliku " + FILE_NAME);
        }
    }

    private Vehicle createObjectFromString(String line) {
        String[] split = line.split(";");
        String type = split[0];
        if (Car.TYPE.equals(type)) {
            return createCar(split);
        } else if (Motorcycle.TYPE.equals(type)) {
            return createMotorcycle(split);
        }
        throw new InvalidMidiDataException("Nieznany typ pojazdu " + type);
    }

    private Motorcycle createMotorcycle(String[] data) {
        String vin = data[1];
        String brand = data[2];
        String model = data[3];
        String engine = data[4];
        int year = Integer.valueOf(data[5]);
        int price = Integer.valueOf(data[6]);
        return new Motorcycle(vin, brand, model, engine, year, price);
    }

    private Car createCar(String[] data) {
        String vin = data[1];
        String brand = data[2];
        String model = data[3];
        String engine = data[4];
        int year = Integer.valueOf(data[5]);
        int price = Integer.valueOf(data[6]);
        String carBody = data[7];
        int doorsAmount = Integer.valueOf(data[8]);
        return new Car(vin, brand, model, engine, year, price, carBody, doorsAmount);
    }


    @Override
    public void exportData(Rental rental) {
        exportVehicles(rental);
        exportUsers(rental);

    }

    private void exportUsers(Rental rental) {
        Collection<RentalUser> users = rental.getUsers().values();
        exportToCsv(users, USERS_FILE_NAME);
    }

    private void exportVehicles(Rental rental) {
        Collection<Vehicle> vehicles = rental.getVehicles().values();
        exportToCsv(vehicles, FILE_NAME);
    }

    private <T extends CsvConvertible> void exportToCsv(Collection<T> collection, String fileName) {
        try (
                FileWriter fileWriter = new FileWriter(fileName);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (T element : collection) {
                bufferedWriter.write(element.toCsv());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new DataImportException("Błąd zapisu danych do pliku " + fileName);
        }
    }
}
