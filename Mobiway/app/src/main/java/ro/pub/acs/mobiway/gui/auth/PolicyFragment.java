package ro.pub.acs.mobiway.gui.auth;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ro.pub.acs.mobiway.R;
import ro.pub.acs.mobiway.general.Constants;
import ro.pub.acs.mobiway.general.SharedPreferencesManagement;
import ro.pub.acs.mobiway.rest.RestClient;
import ro.pub.acs.mobiway.rest.model.Policy;

import android.widget.ArrayAdapter;

public class PolicyFragment extends Fragment {

    private static final String TAG = PolicyFragment.class.getSimpleName();

    private final SharedPreferencesManagement spm = SharedPreferencesManagement.getInstance(null);

    private Button acceptPolicyButton;

    private PolicyFragment thisFragment = this;

    private PolicyOptionsOnClickListener policyOptionsOnClick = new PolicyOptionsOnClickListener();

    private List<CheckBox> checkBoxePolicies = new ArrayList<>();

    public PolicyFragment() {
        // Required empty public constructor
    }

    private Map<Policy, Boolean> getPolicies() {
        Map<Policy, Boolean> policyConfig = new HashMap<>();

        List<Policy> policies = new ArrayList<>();
        try {
            RestClient restClient = new RestClient();
            policies = restClient.getApiService().getPolicyListApp(Constants.APP_NAME);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return policyConfig;
    }


    private void createWebView(View view) {
        final String content =
                "Traffic congestions are realities of urban environments. The road infrastructure capacities " +
                        "cannot cope with the rate of increase in the number of cars. This, coupled with traffic incidents, " +
                        "work zones, weather conditions, make traffic congestions one major concern for municipalities " +
                        "and research organizations." +
                        "<br/><br/>Mobiway is an application that gathers information about daily traffic. The idea is " +
                        "to collect data that can be used to construct a model of the traffic patterns in a large city such " +
                        "as Bucharest. This means getting data about the traffic on a particular road, depending on the " +
                        "hour of the day or time of the week for example. The model can be used to make predictions about " +
                        "traffic, to construct smart navigation application that might tell you to avoid a certain route " +
                        "because it's always congested at this time of the day. We rely on public crowds (meaning you) to collect the data, and give back freely \"\n" +
                        "the obtained data to anyone interesting (everything is public).<br/><br/>" +
                        "The project is developed in a joint collaboration between University POLITEHNICA of Bucharest" +
                        "and Rutgers University.";

        String html = "<html>"
                + "<head></head>"
                + "<body style=\"text-align:justify;color:black;background-color:white; font-size: 12px;\">"
                + content + "</body>" + "</html>";

        WebView showPolicyText = (WebView) view.findViewById(R.id.policy_textview);
        showPolicyText.loadData(html, "text/html", "utf-8");
    }

    private void createPolicyCheckBox(View view, Policy policy) {
        try {
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.fragment_login);

            CheckBox ck = new CheckBox(getActivity());
            ck.setText(policy.getPolicyName());
            ck.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            ck.setClickable(true);
            ck.setChecked(false);
            ck.setActivated(true);
            ck.setTextColor(Color.BLACK);

            ck.setOnClickListener(policyOptionsOnClick);
            checkBoxePolicies.add(ck);
            ll.addView(ck);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void buildPoliciesCheckbox(final List<Policy> policies, final View view) {
        getActivity().runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (Policy policy : policies) createPolicyCheckBox(view, policy);
                    }
                }
        );
    }

    private void createPoliciesList(final View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Policy> policies = new ArrayList<>();
                try {
                    RestClient restClient = new RestClient();
                    policies = restClient.getApiService().getPolicyListApp(Constants.APP_NAME);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                buildPoliciesCheckbox(policies, view);
            }
        });

        thread.start();
    }


    private class AcceptPolicyButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.policy_accept_button: {

                    Set<String> acceptedPolicies = new HashSet<String>();
                    for (CheckBox ck : checkBoxePolicies) {
                        if (ck.isChecked()) {
                            acceptedPolicies.add(ck.getText().toString());
                        }
                    }

                    spm.setUserPolicy(acceptedPolicies);

                    // Proceed with Login
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(thisFragment);
                    fragmentTransaction.add(R.id.container, new LoginFragment());
                    fragmentTransaction.commit();
                }
            }
        }
    }

    private class PolicyOptionsOnClickListener implements View.OnClickListener {
        private final SharedPreferencesManagement spm = SharedPreferencesManagement.getInstance(null);

        @Override
        public void onClick(View v) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle state) {

        final View view = layoutInflater.inflate(R.layout.fragment_policy, container, false);

        getActivity().runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                    }
                }
        );

        createWebView(view);
        createPoliciesList(view);

        acceptPolicyButton = (Button) view.findViewById(R.id.policy_accept_button);
        acceptPolicyButton.setOnClickListener(new AcceptPolicyButtonOnClickListener());

        return view;
    }

}