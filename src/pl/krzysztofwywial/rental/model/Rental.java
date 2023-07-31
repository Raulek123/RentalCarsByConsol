package pl.krzysztofwywial.rental.model;

import pl.krzysztofwywial.rental.exception.UserAlreadyExistsException;

import java.io.Serializable;
import java.util.*;

public class Rental implements Serializable {

    private Map<String, Vehicle> vehicles = new HashMap<>();
    private Map<String, RentalUser> users = new HashMap<>();

    public Map<String, Vehicle> getVehicles() {
        return vehicles;
    }

    public Collection<Vehicle> getSortedVehicles(Comparator<Vehicle> comparator) {
        List<Vehicle> list = new ArrayList<>(this.vehicles.values());
        list.sort(comparator);
        return list;
    }

    public Map<String, RentalUser> getUsers() {
        return users;
    }

    public Collection<RentalUser> getSortedUsers(Comparator<RentalUser> comparator) {
        ArrayList<RentalUser> list = new ArrayList<>(users.values());
        list.sort(comparator);
        return list;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.put(vehicle.getVin(), vehicle);
    }

    public void addUser(RentalUser user) {
        if (users.containsKey(user.getPesel())) {
            throw new UserAlreadyExistsException(
                    "Użytkownik o takim numerze pesel już istnieje " + user.getPesel()
            );
        }
        users.put(user.getPesel(), user);
    }

    public boolean removeVehicle(Vehicle veh) {
        if (vehicles.containsValue(veh)) {
            vehicles.remove(veh.getVin());
            return true;
        } else {
            return false;
        }
    }

    public Optional<Vehicle> findVehicleByVin(String vin) {
        return Optional.ofNullable(vehicles.get(vin));
    }
}
