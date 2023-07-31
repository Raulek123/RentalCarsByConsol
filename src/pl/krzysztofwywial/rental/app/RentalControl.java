package pl.krzysztofwywial.rental.app;

import pl.krzysztofwywial.rental.exception.DataExportException;
import pl.krzysztofwywial.rental.exception.DataImportException;
import pl.krzysztofwywial.rental.exception.InvalidMidiDataException;
import pl.krzysztofwywial.rental.exception.UserAlreadyExistsException;
import pl.krzysztofwywial.rental.io.ConsolePrinter;
import pl.krzysztofwywial.rental.io.Reader;
import pl.krzysztofwywial.rental.io.file.FileManager;
import pl.krzysztofwywial.rental.io.file.FileManagerBuilder;
import pl.krzysztofwywial.rental.model.*;
import pl.krzysztofwywial.rental.model.comparator.AlphabeticalBrandComparator;
import pl.krzysztofwywial.rental.model.comparator.DateComparator;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class RentalControl {

    private ConsolePrinter printer = new ConsolePrinter();
    private Reader reader = new Reader(printer);
    private Rental rental;
    private FileManager fileManager;

    public RentalControl() {
        fileManager = new FileManagerBuilder(printer, reader).build();
        try {
            rental = fileManager.importData();
            printer.printLine("Zaimportowano dane z pliku");
        } catch (DataImportException | InvalidMidiDataException e) {
            printer.printLine(e.getMessage());
            printer.printLine("Utworzono nową bazę");
            rental = new Rental();
        }
    }

    public void controlLoop() {
        Option option;

        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_CAR:
                    addCar();
                    break;
                case ADD_MOTOR:
                    addMotor();
                    break;
                case PRINT_CARS:
                    printCars();
                    break;
                case PRINT_MOTOR:
                    printMotors();
                    break;
                case DELETE_CARS:
                    deleteCars();
                    break;
                case DELETE_MOTOR:
                    deleteMotor();
                    break;
                case FIND_VEHICLES:
                    findVehicles();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case PRINT_USERS:
                    printUsers();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("Nie ma takiej opcji, wprowadź ponownie");
            }
        } while (option != Option.EXIT);
    }

    private void findVehicles() {
        printer.printLine("Podaj numer vin pojazdu jaki chcesz wyszukać");
        String vin = reader.getString();
        String notFoundMessage = "Brak pojazdu o numerze vin: " + vin;
        rental.findVehicleByVin(vin)
                .map(Vehicle::toString)
                .ifPresentOrElse(System.out::println, () -> System.out.println(notFoundMessage));
    }

    private void printUsers() {
        printer.printUsers(rental.getSortedUsers(
                Comparator.comparing(User::getLastName, String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void addUser() {
        RentalUser rentalUser = reader.createRentalUser();
        try {
            rental.addUser(rentalUser);
        } catch (UserAlreadyExistsException e) {
            printer.printLine(e.getMessage());
        }
    }


    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(reader.getInt());
                optionOk = true;
            } catch (InputMismatchException e) {
                printer.printLine("Wprowadzona wartość nie jest liczbą, podaj ponownie opcję");
            } catch (NoSuchElementException e) {
                printer.printLine(e.getMessage());
            }
        }
        return option;
    }

    private void printCars() {
        Sorted sorted;
        printer.printLine("Wybierz sortowanie");
        for (Sorted value : Sorted.values()) {
            printer.printLine(value.toString());
        }
        sorted = getSorted();
        switch (sorted) {
            case ALPHABETICAL:
                printer.printCars(rental.getSortedVehicles(new AlphabeticalBrandComparator()));
                break;
            case DATE_COMPARATOR:
                printer.printCars(rental.getSortedVehicles(new DateComparator()));
                break;
        }
    }

    private void addCar() {
        try {
            Car car = reader.readAndCreateCar();
            rental.addVehicle(car);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się dodać samochodu, nieprawidłowe dane.");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Osiągnięto maksymalną pojemność");
        }
    }

    private void deleteCars() {
        try {
            Car car = reader.readAndCreateCar();
            if (rental.removeVehicle(car))
                printer.printLine("Usunięto samochód");
            else
                printer.printLine("Brak wskazanego samochodu");
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się usunąć samochodu, niepoprawne dane");
        }
    }

    private void printMotors() {
        Sorted sorted;
        printer.printLine("Wybierz sortowanie");
        for (Sorted value : Sorted.values()) {
            printer.printLine(value.toString());
        }
        sorted = getSorted();
        switch (sorted) {
            case ALPHABETICAL:
                printer.printMotorcycle(rental.getSortedVehicles(new AlphabeticalBrandComparator()));
                break;
            case DATE_COMPARATOR:
                printer.printMotorcycle(rental.getSortedVehicles(new DateComparator()));
                break;
        }
    }

    private void addMotor() {
        try {
            Motorcycle motorcycle = reader.readAndCreateMotor();
            rental.addVehicle(motorcycle);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się dodać motoru, nieprawidłowe dane.");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Osiągnięto maksymalną pojemność");
        }
    }

    private void deleteMotor() {
        try {
            Motorcycle motorcycle = reader.readAndCreateMotor();
            if (rental.removeVehicle(motorcycle))
                printer.printLine("Usunięto motocykl");
            else
                printer.printLine("Brak wskazanego motoru");
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się usunąć motoru, niepoprawne dane");
        }
    }

    private void exit() {
        try {
            fileManager.exportData(rental);
            printer.printLine("Wyeksportowano dane do pliku");
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());
        }
        printer.printLine("Zamykanie programu");
        reader.close();
    }

    private void printOptions() {
        printer.printLine("\n Wybierz opcję:");
        for (Option value : Option.values()) {
            printer.printLine(value.toString());
        }
    }

    private enum Option {
        EXIT(0, "wyjście z programu"),
        ADD_CAR(1, "dodanie nowego samochodu"),
        ADD_MOTOR(2, "dodanie nowego motocykla"),
        PRINT_CARS(3, "wyświetl dostępne samochody"),
        PRINT_MOTOR(4, "wyświetl dostępne motocykle"),
        DELETE_CARS(5, "Usuń samochód"),
        DELETE_MOTOR(6, "Usuń motor"),
        FIND_VEHICLES(7, "Wyszukaj pojazd"),
        ADD_USER(8, "Dodaj wynajmującego"),
        PRINT_USERS(9, "Wyświetl wszystkich wynajmujących");

        private final int value;
        private final String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchElementException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchElementException("Brak opcji o numerze: " + option);
            }
        }
    }

    private enum Sorted {
        ALPHABETICAL(0, "Alfabetyczne"),
        DATE_COMPARATOR(1, "Rok pojazdu - rosnąco");

        private final int value;

        private final String description;

        Sorted(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public int getValu() {
            return value;
        }

        public String getDescripti() {
            return description;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static pl.krzysztofwywial.rental.app.RentalControl.Sorted createFromInt(int sorted) throws NoSuchElementException {
            try {
                return pl.krzysztofwywial.rental.app.RentalControl.Sorted.values()[sorted];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchElementException("Brak sortowania o numerze: " + sorted);
            }
        }
    }

    private Sorted getSorted() {
        boolean sortedOk = false;
        Sorted sorted = null;
        while (!sortedOk) {
            try {
                sorted = Sorted.createFromInt(reader.getInt());
                sortedOk = true;
            } catch (InputMismatchException e) {
                printer.printLine("Wprowadzona wartość nie jest liczbą, wybierz ponownie sortowanie");
            } catch (NoSuchElementException e) {
                printer.printLine(e.getMessage());
            }
        }
        return sorted;
    }
}
