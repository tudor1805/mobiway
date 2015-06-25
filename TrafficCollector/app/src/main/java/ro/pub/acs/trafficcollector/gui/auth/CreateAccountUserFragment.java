package ro.pub.acs.trafficcollector.gui.auth;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import ro.pub.acs.trafficcollector.R;
import ro.pub.acs.trafficcollector.general.Constants;
import ro.pub.acs.trafficcollector.general.SharedPreferencesManagement;
import ro.pub.acs.trafficcollector.general.Util;
import ro.pub.acs.trafficcollector.gui.main.MainActivity;
import ro.pub.acs.trafficcollector.rest.RestClient;
import ro.pub.acs.trafficcollector.rest.model.User;

public class CreateAccountUserFragment extends Fragment {

    private static final String TAG = CreateAccountUserFragment.class.getSimpleName();
    private CreateAccountButtonListener buttonOnClickListener = new CreateAccountButtonListener();

    EditText firstnameEditText;
    EditText lastnameEditText;
    EditText emailEditText;
    EditText passEditText;
    EditText phoneEditText;

    private static String emailAddress = Constants.EMPTY_STRING;
    private static String password = Constants.EMPTY_STRING;
    private static String firstname = Constants.EMPTY_STRING;
    private static String lastname = Constants.EMPTY_STRING;
    private static String phone = Constants.EMPTY_STRING;

    private SharedPreferencesManagement sharedPreferencesManagement;

    private ProgressDialog pDialog;
    private Activity activity;

    private class CreateAccountButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.create_account_next_button: {
                    emailAddress = emailEditText.getText().toString();
                    password = passEditText.getText().toString();
                    firstname = firstnameEditText.getText().toString();
                    lastname = lastnameEditText.getText().toString();
                    phone = phoneEditText.getText().toString();

                    /* validate input from user */
                    if (firstname.equals(Constants.EMPTY_STRING)) {
                        firstnameEditText.setHintTextColor(getActivity().getResources().getColor(R.color.custom_hint_text_color));
                        firstnameEditText.setHint(getActivity().getResources().getString(R.string.firstNameRequired));
                        break;
                    }

                    if (lastname.equals(Constants.EMPTY_STRING)) {
                        lastnameEditText.setHintTextColor(getActivity().getResources().getColor(R.color.custom_hint_text_color));
                        lastnameEditText.setHint(getActivity().getResources().getString(R.string.lastNameRequired));
                        break;
                    }

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

                    if(phone.equals(Constants.EMPTY_STRING)){
                        phone = Util.getPhoneNumber(getActivity());
                    }

                    if(phone.length() > 10) {
                        phone = phone.substring(phone.length() - 10, phone.length());
                    }

                    final String phoneNumber = phone;
                    password = new String(Hex.encodeHex(DigestUtils.sha(password)));
                    Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                showDialog();

                                RestClient restClient = new RestClient();
                                User user = new User();
                                user.setUsername(emailAddress);
                                user.setFirstname(firstname);
                                user.setLastname(lastname);
                                user.setPassword(password);
                                user.setPhone(phoneNumber);

                                User result = restClient.getApiService().createAccount(user);
                                if(result != null) {
                                    Log.e(TAG, result+"");
                                    sharedPreferencesManagement.createLoginSession(result.getId(),
                                            result.getLastname(), result.getUsername(), result.getPassword(),
                                            result.getFirstname(), result.getAuth_token(), result.getAuthExpiresIn());

                                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                    dismissDialog();
                                } else {
                                    emailEditText.setHintTextColor(getActivity().getResources().getColor(R.color.custom_hint_text_color));
                                    emailEditText.setHint(getActivity().getResources().getString(R.string.emailAddressExists));
                                    dismissDialog();
                                }
                                Log.e(TAG, "authenticatedUser: " + result);
                            } catch (Exception e) {
                                e.printStackTrace();
                                dismissDialog();
                            }
                        }
                    });

                    thread.start();
                }
                break;
            }
        }

    }


    public CreateAccountUserFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account_user, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button nextButton = (Button) getActivity().findViewById(R.id.create_account_next_button);
        nextButton.setOnClickListener(buttonOnClickListener);

        emailEditText = (EditText) getActivity().findViewById(R.id.username_ca_edit_text);
        passEditText = (EditText) getActivity().findViewById(R.id.password_ca_edit_text);
        firstnameEditText = (EditText) getActivity().findViewById(R.id.firstname_edit_text);
        lastnameEditText = (EditText) getActivity().findViewById(R.id.lastname_edit_text);
        phoneEditText = (EditText) getActivity().findViewById(R.id.phone_number_edit_text);

        emailEditText.setText(emailAddress);
        firstnameEditText.setText(firstname);
        lastnameEditText.setText(lastname);
        passEditText.setText(password);
        phoneEditText.setText(phone);

        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getActivity().getString(R.string.create_account_action_bar_content));
        actionBar.show();

        sharedPreferencesManagement = SharedPreferencesManagement.getInstance(getActivity().getApplicationContext());

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getResources().getString(R.string.loading));
        activity = getActivity();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        Log.e("CreateAccount", "onSaveInstanceState" + bundle.toString());

        bundle.putString("firstName", firstnameEditText.getText().toString());
        bundle.putString("lastName", lastnameEditText.getText().toString());
        bundle.putString("emailAddress", emailEditText.getText().toString());
        bundle.putString("password", passEditText.getText().toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
    }

}
