package com.example.bioticclasses.other;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.bioticclasses.Activity.LoginActivity;
import com.example.bioticclasses.Activity.MainActivity;

import java.util.HashMap;

public class SessionManage {

    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences cart;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "UserSessionPref";

    // First time logic Check
    public static final String FIRST_TIME = "firsttime";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // userid address (make variable public to access from outside)
    public static final String USERID = "userid";

    // Email address (make variable public to access from outside)
    public static final String KEY_PASSWORD = "password";

    // Email address (make variable public to access from outside)
    public static final String KEYID = "keyid";

    // Mobile number (make variable public to access from outside)
    public static final String KEY_MOBiLE = "mobile";

    // user avatar (make variable public to access from outside)
    public static final String KEY_PHOTO = "photo";

    // number of items in our cart
    public static final String KEY_CART = "cartvalue";

    // number of items in our wishlist
    public static final String KEY_WISHLIST = "wishlistvalue";

    // check first time app launch
    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";



    public static final String CLASS1 = "Class";
    public static final String SUBJECT = "Subject";
    public static final String NAME = "Name";
    public static final String EMAIL = "Email";
    public static final String MOBILE = "Mobile";
    public static final String MEDIUN = "Medium";


    // Constructor
    public SessionManage(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createprofile(String email, String password, String name, String mobile, String userid, String token) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);


        // Storing password in shared pref
        editor.putString(KEY_PASSWORD, password);

        //  Storing phone number in pref
        editor.putString(KEY_MOBiLE, mobile);

//         Storing image url in pref
        editor.putString(KEYID, token);

        //         Storing token in pref
        editor.putString(USERID, userid);


        // commit changes
        editor.commit();
    }


    /**
     * Create login session
     */
//    public void createLoginSession(String name, String email, String mobile, String photo){     modified due to photo not available
    public void createLoginSession(String name, String email, String mobile, String class1, String subject,String medium, String Userid) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(MOBILE, mobile);
        editor.putString(CLASS1, class1);
        editor.putString(SUBJECT, subject);
        editor.putString(MEDIUN, medium);
        editor.putString(USERID, Userid);
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }

    }

    public Boolean Checkingcredential() {
        Boolean status = false;
        if (this.isLoggedIn()) {
            status = true;
        }
        return status;
    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // user phone number
        user.put(KEY_MOBiLE, pref.getString(KEY_MOBiLE, null));

        // user avatar
        user.put(KEYID, pref.getString(KEYID, null));

        user.put(USERID, pref.getString(USERID, null));


        user.put(CLASS1, pref.getString(CLASS1, null));
        user.put(SUBJECT, pref.getString(SUBJECT, null));

        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.putBoolean(IS_LOGIN, false);
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    /*
     *
     * Ginny session
     *
     * */

    public void userDetails(String name, String email, String mobile, String class1, String subject) {
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(MOBILE, mobile);
        editor.putString(CLASS1, class1);
        editor.putString(SUBJECT, subject);
        editor.commit();
    }


}




























