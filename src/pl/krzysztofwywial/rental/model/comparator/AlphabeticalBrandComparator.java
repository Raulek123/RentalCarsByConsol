package pl.krzysztofwywial.rental.model.comparator;

import pl.krzysztofwywial.rental.model.Vehicle;

import java.util.Comparator;

public class AlphabeticalBrandComparator implements Comparator<Vehicle> {

    @Override
    public int compare(Vehicle v1, Vehicle v2) {
        if (v1 == null && v2 == null)
            return 0;
        else if (v1 == null)
            return 1;
        else if (v2 == null)
            return -1;
        int brandCompare = v1.getBrand().compareToIgnoreCase(v2.getBrand());
        if (brandCompare != 0)
            return brandCompare;
        return v1.getModel().compareToIgnoreCase(v2.getModel());
    }
}
