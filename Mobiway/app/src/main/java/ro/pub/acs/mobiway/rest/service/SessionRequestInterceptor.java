package ro.pub.acs.mobiway.rest.service;

import retrofit.RequestInterceptor;
import ro.pub.acs.mobiway.general.SharedPreferencesManagement;

public class SessionRequestInterceptor implements RequestInterceptor
{
    @Override
    public void intercept(RequestInterceptor.RequestFacade request)
    {
        if (SharedPreferencesManagement.getInstance(null).isLoggedIn())
            request.addHeader("X-Auth-Token", SharedPreferencesManagement.getInstance(null).getAuthToken());
    }
}