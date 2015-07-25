package ro.pub.acs.mobiway.gui.auth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;

import ro.pub.acs.mobiway.R;
import ro.pub.acs.mobiway.general.SharedPreferencesManagement;

public class PolicyFragment extends Fragment {

    private static final String TAG = PolicyFragment.class.getSimpleName();

    private Button acceptPolicyButton;
    private WebView showPolicyText;
    private CheckBox shareLocationCheckbox;
    private CheckBox shareSpeedCheckbox;

    private PolicyFragment thisFragment = this;

    public PolicyFragment() {
        // Required empty public constructor
    }

    private class AcceptPolicyButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.policy_accept_button: {

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

            boolean checked = ((CheckBox) v).isChecked();

            switch (v.getId()) {
                case R.id.share_location_checkbox: {
                    spm.setShareLocationEnabled(checked);
                }
                case R.id.share_speed_checkbox: {
                    spm.setShareSpeedEnabled(checked);
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle state) {

        final String content =
                "Traffic congestions are realities of urban environments. The road infrastructure capacities " +
                "cannot cope with the rate of increase in the number of cars. This, coupled with traffic incidents, " +
                "work zones, weather conditions, make traffic congestions one major concern for municipalities " +
                "and research organizations." +
                "<br/><br/>Mobiway is an application that gathers information about daily traffic. The idea is " +
                "to collect data that can be used to construct a model of the traffic patterns in a large city such" +
                " as Bucharest. This means getting data about the traffic on a particular road, depending on the" +
                "hour of the day or time of the week for example. The model can be used to make predictions about" +
                "traffic, to construct smart navigation application that might tell you to avoid a certain route" +
                "because it's always congested at this time of the day. We rely on public crowds (meaning you) to collect the data, and give back freely \"\n" +
                "the obtained data to anyone interesting (everything is public).<br/><br/>" +
                "The project is developed in a joint collaboration between University POLITEHNICA of Bucharest" +
                "and Rutgers University.";


        String html = "<html>"
                + "<head></head>"
                + "<body style=\"text-align:justify;color:white;background-color:black; font-size: 12px;\">"
                + content + "</body>" + "</html>";

        View view = layoutInflater.inflate(R.layout.fragment_policy, container, false);

        showPolicyText = (WebView) view.findViewById(R.id.policy_textview);
        showPolicyText.loadData(html, "text/html", "utf-8");

        shareLocationCheckbox = (CheckBox) view.findViewById(R.id.share_location_checkbox);
        shareSpeedCheckbox = (CheckBox) view.findViewById(R.id.share_speed_checkbox);

        PolicyOptionsOnClickListener policyOptionsOnClick = new PolicyOptionsOnClickListener();
        shareLocationCheckbox.setOnClickListener(policyOptionsOnClick);
        shareSpeedCheckbox.setOnClickListener(policyOptionsOnClick);

        acceptPolicyButton = (Button) view.findViewById(R.id.policy_accept_button);
        acceptPolicyButton.setOnClickListener(new AcceptPolicyButtonOnClickListener());

        return view;
    }
}