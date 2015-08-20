package ro.pub.acs.mobiway.gui.auth;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import java.util.List;
import java.util.Locale;

import ro.pub.acs.mobiway.R;
import ro.pub.acs.mobiway.core.LocationService;
import ro.pub.acs.mobiway.general.SharedPreferencesManagement;
import ro.pub.acs.mobiway.gui.main.MainActivity;

public class AuthenticationActivity extends ActionBarActivity implements FragmentManager.OnBackStackChangedListener {
    CallbackManager callbackManager;

    // Log tag
    private static final String TAG = AuthenticationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "===  onCreate === ");

        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        SharedPreferencesManagement sharedPreferencesManagement = SharedPreferencesManagement.getInstance(getApplicationContext());
        Double latitude = sharedPreferencesManagement.getLatitude();
        Double longitude = sharedPreferencesManagement.getLongitude();

        if(latitude == 0 && longitude == 0){
            LocationService ls = new LocationService(getApplicationContext());
            sharedPreferencesManagement.setLatitude(ls.getLatitude());
            sharedPreferencesManagement.setLongitude(ls.getLongitude());
        }

        /* set selected language */
        Locale myLocale = new Locale(sharedPreferencesManagement.getLanguage());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        /* verify if user is logged in */
        if (sharedPreferencesManagement.isLoggedIn()) {
            Log.d(TAG, "user is already logged in");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.d(TAG, "going to log in");
            setContentView(R.layout.activity_authentication);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, new LoginFragment());
            fragmentTransaction.commit();

            fragmentManager.addOnBackStackChangedListener(this);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_authentication, menu);
        return true;
    }

    @Override
    public void onBackStackChanged() {
        boolean canback = getSupportFragmentManager().getBackStackEntryCount() > 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                FragmentManager fm = getSupportFragmentManager();

                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }

                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
