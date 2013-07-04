package de.tud.mobsen.invite2meet;

import de.tud.mobsen.invite2meet.when.FragmentWhen;
import de.tud.mobsen.invite2meet.where.FragmentWhere;
import de.tud.mobsen.invite2meet.who.FragmentWho;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 
 * @author based on: code from wsn WiFun projects
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get the action bar and enable the navigation mode
		final ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// add two new tabs
		addTab("Who?", new FragmentWho());
		addTab("When?", new FragmentWhen());
		addTab("Where?", new FragmentWhere());
		
		//Set settings
		PreferenceManager.setDefaultValues(this, R.xml.settings, false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * @author based on: wsn
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_friends:
			getFragmentManager().beginTransaction().replace(R.id.fragment_content, new FriendsFragment()).commit();
			break;
		case R.id.action_places:
			getFragmentManager().beginTransaction().replace(R.id.fragment_content, new PlacesFragment()).commit();
			break;
		case R.id.action_account:
			getFragmentManager().beginTransaction().replace(R.id.fragment_content, new AccountSettingsFragment()).commit();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	/**
	 * Helper method for creating a Tab
	 * 
	 * @param text
	 *            Tab title
	 * @param fragment
	 *            Tab view fragment
	 */
	private void addTab(String text, Fragment fragment) {
		final ActionBar bar = getActionBar();
		Tab tab = bar.newTab();
		tab.setText(text);
		tab.setTabListener(new TabListener(fragment));
		bar.addTab(tab);
	}

	/**
	 * Member class for a Tab listener
	 * 
	 */
	private class TabListener implements ActionBar.TabListener {

		private Fragment fragment;


		public TabListener(Fragment fragment) {

			this.fragment = fragment;
		}


		public void onTabSelected(Tab tab, FragmentTransaction ft) {

			ft.add(R.id.fragment_content, fragment, fragment.getTag());
		}


		public void onTabUnselected(Tab tab, FragmentTransaction ft) {

			ft.remove(fragment);
		}


		public void onTabReselected(Tab tab, FragmentTransaction ft) {

			// nothing to do
		}
	}

}
