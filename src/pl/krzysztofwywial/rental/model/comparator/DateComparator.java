package pl.krzysztofwywial.rental.model.comparator;

import pl.krzysztofwywial.rental.model.Vehicle;

import java.util.Comparator;

public class DateComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle v1, Vehicle v2) {
        if (v1 == null && v2 == null)
            return 0;
        else if (v1 == null)
            return 1;
        else if (v2 == null)
            return -1;
        Integer y1 = v1.getYear().getValue();
        Integer y2 = v2.getYear().getValue();
        return y1.compareTo(y2);
    }
}
