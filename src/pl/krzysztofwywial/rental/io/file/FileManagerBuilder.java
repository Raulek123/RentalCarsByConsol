package pl.krzysztofwywial.rental.io.file;

import pl.krzysztofwywial.rental.exception.NoSuchFileException;
import pl.krzysztofwywial.rental.io.ConsolePrinter;
import pl.krzysztofwywial.rental.io.Reader;

public class FileManagerBuilder {
    private ConsolePrinter printer;
    private Reader reader;

    public FileManagerBuilder(ConsolePrinter printer, Reader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    public FileManager build() {
        printer.printLine("Wybierz format danych:");
        FileType fileType = getFileType();
        switch (fileType) {
            case SERIAL:
                return new SerializableFileManager();
            case CSV:
                return new CsvFileManager();
            default:
                throw new NoSuchFileException("Nieobsługiwany typ danych");
        }
    }

    private FileType getFileType() {
        boolean typeOk = false;
        FileType result = null;
        do {
            printTypes();
            String type = reader.getString().toUpperCase();
            try {
                result = FileType.valueOf(type);
                typeOk = true;
            } catch (IllegalArgumentException e) {
                printer.printLine("Nieobsługiwany typ dancyh");
            }
        } while (!typeOk);
        return result;
    }

    private void printTypes() {
        for (FileType value : FileType.values()) {
            printer.printLine(value.name());
        }
    }
}
