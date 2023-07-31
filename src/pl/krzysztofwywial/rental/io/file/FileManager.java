package pl.krzysztofwywial.rental.io.file;

import pl.krzysztofwywial.rental.model.Rental;

public interface FileManager {
    Rental importData();
    void exportData(Rental rental);
}
