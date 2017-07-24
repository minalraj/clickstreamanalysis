package clicks;

import java.util.Arrays;
import java.util.List;

/**
 * Created by minal on 20/07/17.
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
