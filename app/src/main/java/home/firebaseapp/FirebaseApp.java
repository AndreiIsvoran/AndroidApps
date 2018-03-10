package home.firebaseapp;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by isvor on 3/10/2018.
 */

public class FirebaseApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
