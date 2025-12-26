public class BookingVehicle {

    public static String[] car = {
            "Lamborghini", "BMW", "Mercedes", "Audi", "Ferrari",
            "Mehran", "Black Vigo", "Limo", "JAC"
    };

    public static int[] rent = {
            45000, 55000, 60000, 60000, 90000,
            5000, 20000, 120000, 18000
    };

    public static int[] available = {
            3, 4, 3, 4, 1,
            4, 3, 3, 4
    };

    public static String[][] bookedBy = new String[car.length][10];
    public static int[][] daysBook = new int[car.length][10];
    public static long[][] cost = new long[car.length][10];

    public static int searchCar(String name) {
        for (int i = 0; i < car.length; i++) {
            if (car[i].equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    public static String bookVehicle(String user, String carName, int days) {

        if (days <= 0) return "Days must be greater than 0";
        if (days > 365) return "Maximum booking is 365 days";

        int index = searchCar(carName);
        if (index == -1) return "Car not found";

        if (available[index] <= 0)
            return "No available units";

        for (int i = 0; i < 10; i++) {
            if (bookedBy[index][i] == null) {
                bookedBy[index][i] = user;
                daysBook[index][i] = days;
                cost[index][i] = (long) days * rent[index];
                available[index]--;
                return "Booked Successfully! Total Cost: " + cost[index][i];
            }
        }

        return "Booking limit reached";
    }

    public static String returnVehicle(String carName, String user) {

        int index = searchCar(carName);
        if (index == -1) return "Car not found";

        for (int i = 0; i < 10; i++) {
            if (user.equalsIgnoreCase(bookedBy[index][i])) {
                bookedBy[index][i] = null;
                daysBook[index][i] = 0;
                cost[index][i] = 0;
                available[index]++;
                return "Car returned successfully";
            }
        }

        return "You have not booked this car";
    }

    public static String[] getVehicleStatus() {

        String[] status = new String[car.length * 12];
        int k = 0;

        for (int i = 0; i < car.length; i++) {

            boolean found = false;

            for (int j = 0; j < 10; j++) {
                if (bookedBy[i][j] != null) {
                    status[k++] =
                            car[i] + " | Booked by: " + bookedBy[i][j] +
                                    " | Days: " + daysBook[i][j] +
                                    " | Cost: " + cost[i][j];
                    found = true;
                }
            }

            status[k++] = car[i] + " | Available Units: " + available[i];

            if (!found) {
                status[k++] = "--------------------";
            }
        }

        String[] result = new String[k];
        for (int i = 0; i < k; i++) {
            result[i] = status[i];
        }

        return result;
    }
}
