package home.firebaseapp;

/**
 * Created by isvor on 3/10/2018.
 */

public class User {

    public String FirstName;
    public String LastName;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String FirstName, String LastName) {
        this.FirstName = FirstName;
        this.LastName = LastName;
    }

}
