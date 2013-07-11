package de.tud.mobsen.invite2meet;

import java.util.Date;

import de.tud.mobsen.invite2meet.objects.Invitation;
import de.tud.mobsen.invite2meet.objects.Place;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author based on: code from wsn WiFun projects
 */
public class MainActivity extends Activity {

	private Invitation invitation;

	private FragmentWho fragmentWho;
	private Tab tabWho;
	private FragmentWhen fragmentWhen;
	private Tab tabWhen;
	private FragmentWhere fragmentWhere;
	private Tab tabWhere;
	
	public void setWhoActive() {
		performFragmentTransaction(fragmentWho);
		getActionBar().selectTab(tabWho);
	}
	
	public void setWhenActive() {
		performFragmentTransaction(fragmentWhen);
		getActionBar().selectTab(tabWhen);
	}
	
	public void setWhereActive() {
		performFragmentTransaction(fragmentWhere);
		getActionBar().selectTab(tabWhere);
	}
	
	private void performFragmentTransaction(Fragment fragment) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.fragment_content, fragment);
		ft.commit();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get the action bar and enable the navigation mode
		final ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// add two new tabs
		fragmentWho = new FragmentWho();
		tabWho = addTab("Who?", fragmentWho);
		
		fragmentWhen = new FragmentWhen();
		tabWhen = addTab("When?", fragmentWhen);
		
		fragmentWhere = new FragmentWhere();
		tabWhere = addTab("Where?", fragmentWhere);	
		
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
			return true;
		case R.id.action_places:
			getFragmentManager().beginTransaction().replace(R.id.fragment_content, new PlacesFragment()).commit();
			return true;
		case R.id.action_account:
			getFragmentManager().beginTransaction().replace(R.id.fragment_content, new AccountSettingsFragment()).commit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * Helper method for creating a Tab
	 * @param text Tab title
	 * @param fragment Tab view fragment
	 */
	private Tab addTab(String text, Fragment fragment) {
		final ActionBar bar = getActionBar();
		Tab tab = bar.newTab();
		tab.setText(text);
		tab.setTabListener(new TabListener(fragment));
		bar.addTab(tab);
		return tab;
	}

	/**
	 * Member class for a Tab listener
	 * @author based on: wsn
	 */
	private class TabListener implements ActionBar.TabListener {
		private Fragment fragment;

		public TabListener(Fragment fragment) {
			this.fragment = fragment;
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			ft.replace(R.id.fragment_content, fragment, fragment.getTag());
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			ft.remove(fragment);
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {

			ft.replace(R.id.fragment_content, fragment, fragment.getTag());
		}
	}
	
	public void inviteNew() {
		invitation = new Invitation();
		//TODO
		invitation.setInviterName("TODO");
	}
	
	public void inviteSetWho(String inviteeName) {
		invitation.setInviteeName(inviteeName);
	}
	
	public void inviteSetWhen(int meetingOffsetMinutes) {
		invitation.setMeetingOffsetMinutes(meetingOffsetMinutes);
	}
	
	public void inviteSetMeetingPlace(Place p) {
		Bitmap bitmap = BitmapFactory.decodeFile(p.getPhotoUri());
		invitation.setMeetingPlaceImage(bitmap);
		invitation.setMeetingPlaceLatitude(p.getLatitude());
		invitation.setMeetingPlaceLongitude(p.getLongitude());
		invitation.setMeetingPlaceName(p.getName());
	}
	
	public void inviteSetCurrentInvitersPosition(double latitude, double longitude) {
		invitation.setInviterLatitude(latitude);
		invitation.setInviterLongitude(longitude);
	}
	
	/**
	 * Returns the invitation if its valid or null otherwise
	 * @return Invitation <b>OR null</b>
	 */
	public Invitation invateGetIt() {
		Date d = new Date();
		invitation.setSendingTime(d.getTime());
		
		if(invitation.isValid()) {
			return invitation;
		} else {
			return null;
		}
	}
	
	public String inviteDump() {
		return invitation.toString();
	}
}
