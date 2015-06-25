package ro.pub.acs.trafficcollector.rest;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import ro.pub.acs.trafficcollector.general.Constants;
import ro.pub.acs.trafficcollector.rest.service.SessionRequestInterceptor;
import ro.pub.acs.trafficcollector.rest.service.TrafficCollectorService;

public class RestClient {
    private TrafficCollectorService apiService;

    public RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Constants.SERVICES_URL)
                .setRequestInterceptor(new SessionRequestInterceptor())
                .setClient(new OkClient(new OkHttpClient()))
                .build();

        apiService = restAdapter.create(TrafficCollectorService.class);
    }

    public TrafficCollectorService getApiService() {
        return apiService;
    }
}