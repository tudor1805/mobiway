package ro.pub.acs.trafficcollector.gui.auth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;

import ro.pub.acs.trafficcollector.R;
import ro.pub.acs.trafficcollector.general.Constants;
import ro.pub.acs.trafficcollector.general.SharedPreferencesManagement;
import ro.pub.acs.trafficcollector.general.Util;
import ro.pub.acs.trafficcollector.gui.main.MainActivity;
import ro.pub.acs.trafficcollector.rest.RestClient;
import ro.pub.acs.trafficcollector.rest.model.User;

public class LoginFragment extends Fragment {

    // Log tag
    private static final String TAG = LoginFragment.class.getSimpleName();
    private Integer tokenExpiresIn;
    private String accessToken;

    private SharedPreferencesManagement sharedPreferencesManagement;
    private LoginButtonOnClickListener buttonOnClickListener = new LoginButtonOnClickListener();
    EditText emailEditText;
    EditText passEditText;
    TextView authErrorTextView;
    LoginButton loginButton;
    CallbackManager callbackManager;
    private ProgressDialog pDialog;
    private Activity activity;

    private static boolean loggedInWithFacebook = false;

    private class LoginButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_button: {
                    authErrorTextView.setVisibility(View.INVISIBLE);

                    final String emailAddress = emailEditText.getText().toString();
                    final String password = passEditText.getText().toString();

                    if (emailAddress.equals(Constants.EMPTY_STRING)) {
                        emailEditText.setHintTextColor(getActivity().getResources().getColor(R.color.custom_hint_text_color));
                        emailEditText.setHint(getActivity().getResources().getString(R.string.emailAddressRequired));
                        break;
                    } else {
                        if(!Util.emailValidator(emailAddress)){
                            emailEditText.setText(Constants.EMPTY_STRING);
                            emailEditText.setHintTextColor(getActivity().getResources().getColor(R.color.custom_hint_text_color));
                            emailEditText.setHint(getActivity().getResources().getString(R.string.emailAddressNotValid));
                            break;
                        }
                    }

                    if (password.equals(Constants.EMPTY_STRING)) {
                        passEditText.setHintTextColor(getActivity().getResources().getColor(R.color.custom_hint_text_color));
                        passEditText.setHint(getActivity().getResources().getString(R.string.passwordRequired));
                        break;
                    }

                    Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                showDialog();

                                RestClient restClient = new RestClient();
                                User user = new User();
                                user.setUsername(emailAddress);
                                user.setPassword(new String(Hex.encodeHex(DigestUtils.sha(password))));

                                User result = restClient.getApiService().loginUserPass(user);
                                if (result != null){
                                    sharedPreferencesManagement.createLoginSession(result.getId(),
                                            result.getLastname(), result.getUsername(), result.getPassword(),
                                            result.getFirstname(), result.getAuth_token(), result.getAuthExpiresIn());

                                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                    dismissDialog();
                                } else {
                                    authErrorTextView.post(new Runnable() {
                                        public void run() {
                                            authErrorTextView.setVisibility(View.VISIBLE);
                                        }
                                    });
                                    dismissDialog();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                dismissDialog();
                            }
                        }
                    });
                    thread.start();
                }
                break;

                case R.id.create_account_button: {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.container, new CreateAccountUserFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                break;
            }
        }
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    public void showDialog(){
        activity.runOnUiThread(new Runnable() {
            public void run() {
                pDialog.show();
            }
        });
    }

    public void dismissDialog(){
        activity.runOnUiThread(new Runnable() {
            public void run() {
                pDialog.dismiss();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle state) {
        Log.d(TAG, "onCreateView");

        callbackManager = CallbackManager.Factory.create();

        if(loggedInWithFacebook){
            LoginManager.getInstance().logOut();
            sharedPreferencesManagement.logoutUser();

            loggedInWithFacebook = false;
        }

        View view = layoutInflater.inflate(R.layout.fragment_login, container, false);

        loginButton = (LoginButton) view.findViewById(R.id.login_facebook_button);
        loginButton.setReadPermissions(Arrays.asList("user_friends, public_profile, basic_info, email, user_birthday"));

        // If using in a fragment
        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                long msNow = new Date().getTime();
                long msExpiresIn = loginResult.getAccessToken().getExpires().getTime();

                accessToken = loginResult.getAccessToken().getToken();
                tokenExpiresIn = (int)((msExpiresIn - msNow) / 1000);
                showDialog();

                final GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    final GraphResponse response) {
                                JSONObject responseObj = response.getJSONObject();

                                if (responseObj != null) {
                                    try {

                                        Log.d(TAG, "FBObjectResponse" + responseObj.toString());

                                        final String email = responseObj.getString("email");
                                        final String firstName = responseObj.getString("first_name");
                                        final String lastName = responseObj.getString("last_name");
                                        final String phoneNumber = Util.getPhoneNumber(getActivity());

                                        Thread thread = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    RestClient restClient = new RestClient();
                                                    User user = new User();
                                                    user.setUsername(email);
                                                    user.setPhone(phoneNumber);
                                                    user.setFirstname(firstName);
                                                    user.setLastname(lastName);
                                                    user.setFacebook_token(accessToken);
                                                    user.setFacebookExpiresIn(tokenExpiresIn);
                                                    user.setPassword("");

                                                    User result = restClient.getApiService().loginFacebook(user);

                                                    if (result != null) {
                                                        sharedPreferencesManagement.createLoginSession(result.getId(),
                                                                result.getLastname(), result.getUsername(), result.getPassword(),
                                                                result.getFirstname(), result.getAuth_token(), result.getAuthExpiresIn());
                                                        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                                                        startActivity(intent);
                                                        getActivity().finish();
                                                        dismissDialog();
                                                    } else {
                                                        LoginManager.getInstance().logOut();
                                                        dismissDialog();
                                                    }
                                                    Log.e(TAG, "authenticatedUser: " + result);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    LoginManager.getInstance().logOut();
                                                    dismissDialog();
                                                }
                                            }
                                        });
                                        thread.start();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        dismissDialog();
                                    }
                                }

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email, gender, birthday, first_name, last_name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("FB CANCEL", "onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("FB ERROR", exception.toString());
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        Button loginButton = (Button) getActivity().findViewById(R.id.login_button);
        loginButton.setOnClickListener(buttonOnClickListener);

        Button createAccountButton = (Button) getActivity().findViewById(R.id.create_account_button);
        createAccountButton.setOnClickListener(buttonOnClickListener);

        authErrorTextView = (TextView) getActivity().findViewById(R.id.auth_error_text_view);
        emailEditText = (EditText) getActivity().findViewById(R.id.username_edit_text);
        passEditText = (EditText) getActivity().findViewById(R.id.password_edit_text);

        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.hide();

        sharedPreferencesManagement = SharedPreferencesManagement.getInstance(getActivity().getApplicationContext());

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getResources().getString(R.string.loading));
        activity = getActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}