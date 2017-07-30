package clicks;

import java.util.Arrays;
import java.util.List;

/**
 * Listing together some of the  user activites on an online shopping
 * website and categorizing their type
 */
public enum Activity {
    ACCESSORIES(ActivityType.CLICK),
    WOMENS_WEAR(ActivityType.CLICK),
    MENS_WEAR(ActivityType.CLICK),
    CART(ActivityType.VIEW),
    WISHLIST(ActivityType.VIEW),
    NEW_ARRIVAL(ActivityType.VIEW),
    CHECKOUT(ActivityType.VIEW),
    SALE(ActivityType.VIEW);

    Activity(ActivityType activityType) {
        this.activityType = activityType;
    }

    private ActivityType activityType;

    public ActivityType getActivityType() {
        return activityType;
    }

    public static void main(String[] args) {
        for (Activity activity : Activity.values()) {
            System.out.println("Activity : " + activity.name() + ", Activity type : " + activity.getActivityType());
        }


        List<Activity> activityList = Arrays.asList(Activity.values());
        for (Activity activity : activityList) {
            System.out.println("----" + activity.name());
        }

    }
}
