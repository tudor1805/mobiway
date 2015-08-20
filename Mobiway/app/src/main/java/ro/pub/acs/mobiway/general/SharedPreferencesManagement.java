package ro.pub.acs.mobiway.general;

import android.content.*;
import android.util.Log;

import java.util.*;

import ro.pub.acs.mobiway.gui.auth.AuthenticationActivity;

public class SharedPreferencesManagement {

    // Log tag
    private static final String TAG = SharedPreferencesManagement.class.getSimpleName();

    private static SharedPreferencesManagement instance;

    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    /* Constructor */
    public SharedPreferencesManagement(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(Constants.PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static SharedPreferencesManagement getInstance(Context context) {
        if (instance != null)
            return instance;
        else {
            instance = new SharedPreferencesManagement(context);
            return instance;
        }
    }

    /**
     * Create login session
     */
    public void createLoginSession(int userId,
                                   String lastName, String email, String password, String firstName,
                                   String token,
                                   long expiresOn) {
        /* create new session */
        editor.putBoolean(Constants.IS_LOGGED_IN, true);

        editor.putInt(Constants.KEY_USER_ID, userId);

        editor.putString(Constants.KEY_EMAIL, email);
        editor.putString(Constants.KEY_PASSWORD, password);
        editor.putString(Constants.KEY_FIRSTNAME, firstName);
        editor.putString(Constants.KEY_LASTNAME, lastName);
        editor.putString(Constants.KEY_AUTH_TOKEN, token);
        editor.putLong(Constants.KEY_EXPIRES_ON, expiresOn);

        // commit changes
        editor.commit();

    }

    public String getTokenFB() {
        return pref.getString(Constants.KEY_AUTH_TOKEN_FB, Constants.EMPTY_STRING);
    }

    public void setTokenFB(String token) {
        editor.putString(Constants.KEY_AUTH_TOKEN_FB, token);
        editor.commit();
    }

    public void setToken(String token) {
        editor.putString(Constants.KEY_AUTH_TOKEN, token);
        editor.commit();
    }

    public int getExpiresInFB() {
        return pref.getInt(Constants.KEY_EXPIRES_IN_FB, -1);
    }

    public void setExpiresInFB(int expiresInFB) {
        editor.putInt(Constants.KEY_EXPIRES_IN_FB, expiresInFB);
        editor.commit();
    }

    public void setExpiresOn(String expiresOn) {
        editor.putString(Constants.KEY_EXPIRES_ON, expiresOn);
        editor.commit();
    }

    public String getLanguage() {
        return pref.getString(Constants.KEY_CURRENT_LANGUAGE, Constants.LANG_EN);
    }

    public void setLanguage(String lang) {
        editor.putString(Constants.KEY_CURRENT_LANGUAGE, lang);
        editor.commit();
    }

    public double getLatitude() {
        return pref.getFloat(Constants.KEY_LATITUDE, 0);
    }

    public void setLatitude(double latitude) {
        float fLatitude = (float) latitude;
        editor.putFloat(Constants.KEY_LATITUDE, fLatitude);
        editor.commit();
    }

    public double getLongitude() {
        return pref.getFloat(Constants.KEY_LONGITUDE, 0);
    }

    public void setLongitude(double longitude) {
        float fLongitude = (float) longitude;
        editor.putFloat(Constants.KEY_LONGITUDE, fLongitude);
        editor.commit();
    }

    public boolean getNotificationsEnabled() {
        return pref.getBoolean(Constants.KEY_NOTIFICATIONS_ENABLED, true);
    }

    public void setNotificationsEnabled(boolean value) {
        editor.putBoolean(Constants.KEY_NOTIFICATIONS_ENABLED, value);
        editor.commit();
    }

    public boolean getShareLocationEnabled() {
        return getUserPolicies().contains(Constants.KEY_SHARE_LOCATION);
    }

    public boolean getShareSpeedEnabled() {
        return getUserPolicies().contains(Constants.KEY_SHARE_SPEED);
    }

    public Set<String> getUserPolicies() {
        return pref.getStringSet(Constants.KEY_POLICY_PREFERENCES, new HashSet<String>());
    }

    public void setUserPolicy(Set<String> policyPreferences) {
        editor.putStringSet(Constants.KEY_POLICY_PREFERENCES, policyPreferences);
        for (String policy : policyPreferences) {
            editor.putBoolean(policy, true);
        }
        editor.commit();
    }

    public void inspectSettings() {
        Map<String, ?> allEntries = pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("Setting values", entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    public Set<String> getUserLocPreferences() {
        return pref.getStringSet(Constants.KEY_LOC_PREFERENCES, new HashSet<String>());
    }

    public void setUserLocPreferences(Set<String> userLocPreferences) {
        editor.putStringSet(Constants.KEY_LOC_PREFERENCES, userLocPreferences);
        editor.commit();
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        /* First clear all data from Shared Preferences */
        editor.remove(Constants.IS_LOGGED_IN);
        editor.remove(Constants.KEY_USER_ID);
        editor.remove(Constants.KEY_EMAIL);
        editor.remove(Constants.KEY_FIRSTNAME);
        editor.remove(Constants.KEY_LASTNAME);
        editor.remove(Constants.KEY_AUTH_TOKEN);
        editor.remove(Constants.KEY_EXPIRES_ON);
        editor.remove(Constants.KEY_EXPIRES_IN_FB);
        editor.remove(Constants.KEY_AUTH_TOKEN_FB);

        editor.commit();

        /* After logout redirect user to Authentication Activity */
        Intent i = new Intent(_context, AuthenticationActivity.class);

        /* Closing all the Activities */
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        /* Staring Authentication Activity */
        _context.startActivity(i);
    }

    public boolean isLoggedIn() {
        boolean loggedIn = pref.getBoolean(Constants.IS_LOGGED_IN, false);
        long expiresOn = pref.getLong(Constants.KEY_EXPIRES_ON, 0);
        long now = new Date().getTime() / 1000;

        if (expiresOn != 0) {
            return loggedIn && expiresOn > now;
        } else {
            return loggedIn;
        }
    }

    /**
     * Gets userId from session
     */
    public int getAuthUserId() {
        return pref.getInt(Constants.KEY_USER_ID, -1);
    }

    public String getAuthUserLastName() {
        return pref.getString(Constants.KEY_LASTNAME, Constants.EMPTY_STRING);
    }

    public String getAuthUserEmail() {
        return pref.getString(Constants.KEY_EMAIL, Constants.EMPTY_STRING);
    }

    public String getAuthUserFirstName() {
        return pref.getString(Constants.KEY_FIRSTNAME, Constants.EMPTY_STRING);
    }

    public String getAuthToken() {
        String token = pref.getString(Constants.KEY_AUTH_TOKEN, Constants.EMPTY_STRING);
        return token;
    }

    public boolean isFirstTimeUse() {
        return pref.getBoolean(Constants.KEY_FIRST_TIME_USE, true);
    }

    public void setFirstTimeUse() {
        editor.putBoolean(Constants.KEY_FIRST_TIME_USE, false);
        editor.commit();
    }

}