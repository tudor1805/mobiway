package ro.pub.acs.trafficcollector.rest.service;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import ro.pub.acs.trafficcollector.general.Constants;
import ro.pub.acs.trafficcollector.rest.TypedJsonString;
import ro.pub.acs.trafficcollector.rest.model.Location;
import ro.pub.acs.trafficcollector.rest.model.Place;
import ro.pub.acs.trafficcollector.rest.model.User;

public interface TrafficCollectorService {

    @Headers({
                "Content-Type: application/json",
                "Accept: application/json"
            })
    @POST(Constants.URL_CREATE_ACCOUNT)
    User createAccount(@Body User body);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST(Constants.URL_LOG_IN)
    User loginUserPass(@Body User body);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST(Constants.URL_AUTH_FACEBOOK)
    User loginFacebook(@Body User body);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST(Constants.URL_UPDATE_USER)
    String updateUSer(@Body TypedJsonString body);


    @GET(Constants.URL_GET_USER + "/{userId}")
    User getUser(@Path("userId") int userId);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST(Constants.URL_NEW_JOURNEY)
    boolean newJourney(@Body Integer userId);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @PUT(Constants.URL_UPDATE_LOCATION)
    boolean updateLocation(@Body Location location);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @GET(Constants.URL_GET_FRIENDS_NAMES)
    List<User> getFriendsNames();

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @GET(Constants.URL_GET_FRIENDS_LOCATIONS)
    List<Location> getFriendsLocations();

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @GET(Constants.URL_GET_USERS_WITH_PHONE)
    List<User> getUsersWithPhone();

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @PUT(Constants.URL_ADD_FRIENDS)
    boolean addFriends(@Body List<User> contacts);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST(Constants.URL_GET_NEARBY_LOCATIONS)
    List<Place> getNearbyLocations(@Body ArrayList<String> types);


    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST(Constants.URL_GET_ROUTE)
    List<Location> getRoute(@Body ArrayList<Location> locations);
}