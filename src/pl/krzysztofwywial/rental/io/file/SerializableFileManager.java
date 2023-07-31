package pl.krzysztofwywial.rental.io.file;

import pl.krzysztofwywial.rental.exception.DataExportException;
import pl.krzysztofwywial.rental.exception.DataImportException;
import pl.krzysztofwywial.rental.model.Rental;

import java.io.*;

public class SerializableFileManager implements FileManager {
    private static final String FILE_NAME = "Rental.o";

    @Override
    public Rental importData() {
        try (
                FileInputStream fis = new FileInputStream(FILE_NAME);
                ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            return (Rental) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku " + FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Błąd odczytu pliku " + FILE_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataImportException("Nieprawidlowy typ danych w pliku " + FILE_NAME);
        }
    }

    @Override
    public void exportData(Rental rental) {
        try (
                FileOutputStream fos = new FileOutputStream(FILE_NAME);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(rental);
        } catch (FileNotFoundException e) {
            throw new DataExportException("Brak pliku " + FILE_NAME);
        } catch (IOException e) {
            throw new DataExportException("Błędny zapis danych do pliku " + FILE_NAME);
        }
    }
}
