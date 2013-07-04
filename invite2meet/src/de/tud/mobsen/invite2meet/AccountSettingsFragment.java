package de.tud.mobsen.invite2meet;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * 
 * @author based on: wsn, WiFun4
 *
 */
public class AccountSettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
	
}
