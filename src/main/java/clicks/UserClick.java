package clicks;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.StringTokenizer;

/**
 * Defining user attributes on the basis of which output graphs will be generated
 */
public class UserClick {
    private int userID;
    private String userName;
    private String gender;
    private String geoLocation;
    private int age;
    private Activity activity;

    public UserClick() {

    }

    public UserClick(int userID, String userName, String gender, String geoLocation, int age, Activity activity){
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.geoLocation = geoLocation;
        this.age = age;
        this.activity = activity;
    }


    public int getuserID() {return userID;}

    public void setuserID(int userID) {this.userID = userID;}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName){
        this.userName =userName;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getGeoLocation(){
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation){
        this.geoLocation = geoLocation;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String toString(){
        return "UserClick{" + "UserID= " + userID + ", UserName= " + userName  + ", Gender= "
                + gender  + ", geoLocation= " + geoLocation + ", age= " + age +  ", activity = " + activity.name() + '}';
    }

}
