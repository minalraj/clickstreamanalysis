package clicks;

import java.util.List;
import java.util.Random;

/**
 * Utility to generate random user clicks.
 * ID : Generate random numbers between 1000 - 2000
 * User name : User<ID>
 * Gender : If ID % 2 or 3 = 0 Male else female
 * Geo location : Random values from the location array
 * Activity : Generate random values from Activities enum
 * Age : Generate age b/w 15-60 : (ID % 45) + 15
 */

public class UserClickGenerator {
    private static Random randomID = new Random();
    private static Random randomLocation = new Random();
    private static Random randomActivity = new Random();


    public static UserClick next() {
        int id = randomID.nextInt(1000) + 1001;
        String username = "user" + id;
        String gender = ((id % 2 == 0) || (id % 3 == 0) ? "male" : "female");

        int geoLocationIndex = randomLocation.nextInt(GeoLocations.locations.length);
        String geoLocation = GeoLocations.locations[geoLocationIndex];

        Activity[] activities = Activity.values();
        int activityIndex = randomActivity.nextInt(activities.length);
        Activity activity = activities[activityIndex];

        int age = (id % 45) + 15;

        return new UserClick(id, username, gender, geoLocation, age, activity);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            System.out.println(UserClickGenerator.next());
        }
    }



}
