package ro.pub.acs.trafficcollector.rest.service;

import retrofit.RequestInterceptor;
import ro.pub.acs.trafficcollector.general.SharedPreferencesManagement;

public class SessionRequestInterceptor implements RequestInterceptor
{
    @Override
    public void intercept(RequestInterceptor.RequestFacade request)
    {
        if (SharedPreferencesManagement.getInstance(null).isLoggedIn())
            request.addHeader("X-Auth-Token", SharedPreferencesManagement.getInstance(null).getAuthToken());
    }
}