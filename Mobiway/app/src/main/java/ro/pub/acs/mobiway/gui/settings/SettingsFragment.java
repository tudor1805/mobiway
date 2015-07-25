package ro.pub.acs.mobiway.gui.settings;


import android.os.Bundle;
import android.preference.*;

import java.util.*;

import ro.pub.acs.mobiway.R;
import ro.pub.acs.mobiway.general.SharedPreferencesManagement;
import ro.pub.acs.mobiway.gui.main.MainActivity;

public class SettingsFragment extends PreferenceFragment {

    private final SharedPreferencesManagement spm = SharedPreferencesManagement.getInstance(null);

    private ArrayList<String> categories = new ArrayList<>();
    private ArrayList<String> checkedCategories = new ArrayList<>();

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_settings);

        final EditTextPreference etp = (EditTextPreference) findPreference(getResources().getString(R.string.key_account_preference));
        final SwitchPreference sp = (SwitchPreference) findPreference(getResources().getString(R.string.key_location_preference));

        etp.setSummary(spm.getAuthUserFirstName() + " " + spm.getAuthUserLastName());

        sp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                spm.setNotificationsEnabled(new Boolean(newValue.toString()));
                return true;
            }
        });

        setListeners();
    }

    private void getPreferences() {
        Set<String> userLocPreferences = new HashSet<>();

        for (String category : categories) {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference(category);
            if (checkBoxPreference.isChecked()) {
                userLocPreferences.add(category);
            }
        }

        spm.setUserLocPreferences(userLocPreferences);
    }

    private void setListeners() {
        categories.add("bar");
        categories.add("cafe");
        categories.add("fast_food");
        categories.add("restaurant");
        categories.add("pub");
        categories.add("school");
        categories.add("college");
        categories.add("library");
        categories.add("university");
        categories.add("fuel");
        categories.add("taxi");
        categories.add("car_wash");
        categories.add("atm");
        categories.add("bank");
        categories.add("clinic");
        categories.add("dentist");
        categories.add("hospital");
        categories.add("pharmacy");
        categories.add("cinema");
        categories.add("night_club");
        categories.add("theatre");
        categories.add("gym");
        categories.add("marketplace");
        categories.add("police");

        for (String category : categories) {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference(category);
            checkBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    getPreferences();
                    return true;
                }
            });
        }

        getPreferences();
    }

}