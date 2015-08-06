package ro.pub.acs.mobiway.gui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.*;

import ro.pub.acs.mobiway.general.Constants;
import ro.pub.acs.mobiway.general.SharedPreferencesManagement;
import ro.pub.acs.mobiway.general.Util;
import ro.pub.acs.mobiway.gui.settings.SettingsActivity;
import ro.pub.acs.mobiway.rest.RestClient;
import ro.pub.acs.mobiway.rest.model.Place;
import ro.pub.acs.mobiway.rest.model.Policy;
import ro.pub.acs.mobiway.rest.model.User;
import ro.pub.acs.mobiway.R;

public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static ArrayList<ro.pub.acs.mobiway.rest.model.Location> locationArrayList = new ArrayList<>();
    private static List<User> friendsNames = null;
    private static List<ro.pub.acs.mobiway.rest.model.Location> friendsLocations = null;
    private static boolean firstLocation = true;
    private SharedPreferencesManagement sharedPreferencesManagement;
    private GoogleMap googleMap = null; /* Might be null if Google Play services APK is not available. */
    private GoogleApiClient googleApiClient = null;
    private Location lastLocation = null;
    private LocationRequest locationRequest = null;
    private Marker marker;
    private LatLng latLngMarker;

    private Button showRouteButton;
    private Button hideRouteButton;
    private ArrayList<Polyline> aPolyline = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate() callback method was invoked");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferencesManagement = new SharedPreferencesManagement(getApplicationContext());

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        locationRequest = new LocationRequest();
        locationRequest.setInterval(Constants.LOCATION_REQUEST_INTERVAL);
        locationRequest.setFastestInterval(Constants.LOCATION_REQUEST_FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (sharedPreferencesManagement.isFirstTimeUse()) {
            getContacts();
            sharedPreferencesManagement.setFirstTimeUse();
        } else {
            getFriends();
        }

        setAcceptedPolicies();
        getNearbyLocations();

        showRouteButton = (Button) findViewById(R.id.button_show_route);
        showRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            RestClient restClient = new RestClient();

                            ArrayList<ro.pub.acs.mobiway.rest.model.Location> locations = new ArrayList<>();
                            ro.pub.acs.mobiway.rest.model.Location location1 = new ro.pub.acs.mobiway.rest.model.Location();
                            ro.pub.acs.mobiway.rest.model.Location location2 = new ro.pub.acs.mobiway.rest.model.Location();
                            location1.setLatitude((float) lastLocation.getLatitude());
                            location1.setLongitude((float) lastLocation.getLongitude());
                            location2.setLatitude((float) latLngMarker.latitude);
                            location2.setLongitude((float) latLngMarker.longitude);

                            locations.add(location1);
                            locations.add(location2);

                            List<ro.pub.acs.mobiway.rest.model.Location> result = restClient.getApiService().getRoute(locations);
                            showRouteOnMap(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });

        hideRouteButton = (Button) findViewById(R.id.button_hide_route);
        hideRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Polyline polyline : aPolyline) {
                    polyline.remove();
                }
                aPolyline.clear();
                showRouteButton.setVisibility(View.GONE);
                hideRouteButton.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart() callback method was invoked");
        super.onStart();
        googleApiClient.connect();
        if (googleMap == null) {
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            googleMap.setOnMarkerClickListener(this);
            googleMap.setOnMapClickListener(this);
        }

        if (googleApiClient != null && googleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop() callback method was invoked");
        stopLocationUpdates();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy() callback method was invoked");
        googleApiClient = null;
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "onSaveInstanceState() callback method was invoked");
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume() callback method was invoked");
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings: {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
                return true;
            }

            case R.id.action_logout: {
                new AlertDialog.Builder(this)
                        .setMessage(getResources().getString(R.string.logout_confirmation))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                LoginManager.getInstance().logOut();

                                sharedPreferencesManagement.logoutUser();
                                finish();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), null)
                        .show();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setAcceptedPolicies() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Set<String> policyPreferences = sharedPreferencesManagement.getUserPolicies();
                    List<String> policyList = new ArrayList<String>();
                    policyList.addAll(policyPreferences);

                    RestClient restClient = new RestClient();
                    restClient.getApiService().acceptUserPolicyListForApp(
                            sharedPreferencesManagement.getAuthUserId(), Constants.APP_NAME, policyList);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void navigateToLocation(final double latitude, final double longitude, final float speed) {
        if (firstLocation) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(latitude, longitude)
            ).zoom(Constants.CAMERA_ZOOM)
                    .build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            firstLocation = false;
        }

        final ro.pub.acs.mobiway.rest.model.Location location = new ro.pub.acs.mobiway.rest.model.Location();
        location.setLatitude((float) latitude);
        location.setLongitude((float) longitude);
        location.setSpeed((int) speed);
        location.setIdUser(sharedPreferencesManagement.getAuthUserId());

        if (sharedPreferencesManagement.getNotificationsEnabled()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        RestClient restClient = new RestClient();
                        if (!sharedPreferencesManagement.getShareLocationEnabled()) {
                            location.setLatitude(null);
                            location.setLongitude(null);
                        }

                        if (!sharedPreferencesManagement.getShareSpeedEnabled()) {
                            location.setSpeed(null);
                        }
                        restClient.getApiService().updateLocation(location);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        } else {
            //locationArrayList.add(location);
        }

    }

    private void navigateToLocation(Location location) {
        navigateToLocation(location.getLatitude(), location.getLongitude(), location.getSpeed());
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "onConnected() callback method has been invoked");
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "onConnectionSuspended() callback method has been invoked");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed() callback method has been invoked");
    }

    protected void startLocationUpdates() {
        // Notify Server that we started a new Journey
        if (sharedPreferencesManagement.getNotificationsEnabled()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        RestClient restClient = new RestClient();
                        restClient.getApiService().newJourney(sharedPreferencesManagement.getAuthUserId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        } else {
            //locationArrayList.add(location);
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient,
                locationRequest,
                this
        );

        googleMap.setMyLocationEnabled(true);

        if (lastLocation != null)
            navigateToLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), lastLocation.getSpeed());
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient,
                this
        );
        firstLocation = true;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v(TAG, "onLocationChanged() callback method has been invoked");
        lastLocation = location;
        navigateToLocation(lastLocation);
    }

    private void getContacts() {
        final Context context = getApplicationContext();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<String> phones = new ArrayList<>();
                    RestClient restClient = new RestClient();
                    List<User> users = restClient.getApiService().getUsersWithPhone();
                    HashMap<String, User> usersWithPhones = new HashMap<>();

                    for (User user : users) {
                        phones.add(user.getPhone());
                        usersWithPhones.put(user.getPhone(), user);
                    }

                    if (!phones.isEmpty()) {
                        ArrayList<String> contacts = Util.readContacts(context, phones);
                        users = new ArrayList<>();

                        if (!contacts.isEmpty()) {
                            for (String number : contacts) {
                                users.add(usersWithPhones.get(number));
                            }

                            restClient.getApiService().addFriends(users);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getFriends();
            }
        }).start();
    }

    private void getFriends() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RestClient restClient = new RestClient();
                    friendsNames = restClient.getApiService().getFriendsNames();
                    friendsLocations = restClient.getApiService().getFriendsLocations();

                    showFriendsOnMap();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void showFriendsOnMap() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                googleMap.clear();
                for (User friend : friendsNames) {
                    for (ro.pub.acs.mobiway.rest.model.Location location : friendsLocations) {
                        if (location.getIdUser() == friend.getId()) {
                            googleMap.addMarker(new MarkerOptions()
                                    .title(friend.getFirstname() + " " + friend.getLastname())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_face_black_24dp))
                                    .alpha(0.8f)
                                    .position(new LatLng(location.getLatitude(), location.getLongitude())));
                            break;
                        }
                    }
                }
            }
        });
    }

    private void getNearbyLocations() {
        Set<String> locPref = sharedPreferencesManagement.getUserLocPreferences();
        final ArrayList<String> prefList = new ArrayList<>();
        prefList.addAll(locPref);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RestClient restClient = new RestClient();
                    List<Place> result = restClient.getApiService().getNearbyLocations(prefList);

                    showPlacesOnMap(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void showPlacesOnMap(final List<Place> places) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Place place : places) {
                    googleMap.addMarker(new MarkerOptions()
                            .title(place.getName())
                            .alpha(0.8f)
                            .position(new LatLng(place.getLatitude(), place.getLongitude())));
                }
            }
        });
    }

    private void showRouteOnMap(final List<ro.pub.acs.mobiway.rest.model.Location> points) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PolylineOptions polylineOptions = new PolylineOptions();
                for (ro.pub.acs.mobiway.rest.model.Location point : points) {
                    polylineOptions.add(new LatLng(point.getLatitude(), point.getLongitude()));
                }
                aPolyline.add(googleMap.addPolyline(polylineOptions));
                hideRouteButton.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        this.marker = marker;
        latLngMarker = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
        marker.showInfoWindow();
        showRouteButton.setVisibility(View.VISIBLE);
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        showRouteButton.setVisibility(View.GONE);
        hideRouteButton.setVisibility(View.GONE);
    }
}