package de.tud.mobsen.invite2meet.where;

import java.util.LinkedList;
import java.util.List;

import de.tud.mobsen.invite2meet.MainActivity;
import de.tud.mobsen.invite2meet.R;
import de.tud.mobsen.invite2meet.db.PlacesDbHelper;
import de.tud.mobsen.invite2meet.objects.Place;
import de.tud.mobsen.invite2meet.objects.PlacesListViewAdapter;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentWhere extends Fragment {
	
	private final static String tag = "FragmentWhere";
	
	private LocationManager locationManager;
	private LocationListener locListener;
	
	double latitude;
	double longitude;
	
	private PlacesListViewAdapter placesListViewAdapter;
	private ProgressDialog progressDialog;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.fragment_where, container, false);
		
		ListView friendsListView = (ListView) fragView.findViewById(R.id.fragwhere_listview);
		placesListViewAdapter = new PlacesListViewAdapter(getActivity(), friendsListView, new LinkedList<Place>()); 		
		friendsListView.setAdapter(placesListViewAdapter);
		friendsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				onItemClicked(position);
			}
		});
		
		getPlaces();
		
		//start getting location
		getLocation();
		
		return fragView;
	}
	
	private void onItemClicked(int position) {
		MainActivity ma = (MainActivity) getActivity();
		
		//TODO: remove
		Log.i(tag, ma.inviteDump());
		
		//if((latitude == Double.MIN_VALUE) || (longitude == Double.MIN_VALUE)) {
		//	Toast.makeText(getActivity(), "Wait, getting your location. Try again in a few seconds.", Toast.LENGTH_SHORT).show();
		//	return;
		//}
		//TODO:
		Toast.makeText(getActivity(), "USING FAKE LOCATION", Toast.LENGTH_SHORT).show();
		latitude = 42;
		longitude = 4711;
		//
		
		
		Place p = (Place) placesListViewAdapter.getItem(position);
		ma.inviteSetMeetingPlace(p);
		ma.inviteSetCurrentInvitersPosition(latitude, longitude);
		
		//TODO: SEND!!!
		
		Toast.makeText(ma, "Sent invitation", Toast.LENGTH_SHORT).show();
		
		ma.setWhoActive();
	}
	
	private void getPlaces() {
		progressDialog = ProgressDialog.show(getActivity(), "Loading places", "Please wait...", true);
		new LoadPlacesDatabase().execute();
	}
	
	/**
	 * @author Based on: Wireless Sensor Networks Lab, WiFun6
	 * @author Based on: http://stackoverflow.com/questions/7979230/how-to-read-location-only-once-with-locationmanager-gps-and-network-provider-a
	 */
	private void getLocation() {
		latitude = Double.MIN_VALUE;
		longitude = Double.MIN_VALUE;
		
		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		locListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				newLocation(location);
			}

			@Override
			public void onProviderDisabled(String provider) {}
			@Override
			public void onProviderEnabled(String provider) {}
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
		};

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locListener);
		//String locationProvider = LocationManager.NETWORK_PROVIDER;
		//locationManager.requestSingleUpdate(locationProvider, locListener, );
		//return locationManager.getLastKnownLocation(locationProvider);
		Toast.makeText(getActivity(), "Waiting for location...", Toast.LENGTH_SHORT).show();
	}
	
	private void newLocation(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
	}
	
	/**
	 * Member class used for selecting all database records.
	 * @author based on: wsn
	 */
	private class LoadPlacesDatabase extends AsyncTask<Void, Void, List<Place>> {
		
		protected List<Place> doInBackground(Void... params) {
			PlacesDbHelper databaseHelper = new PlacesDbHelper(getActivity());
			SQLiteDatabase database = databaseHelper.getReadableDatabase();
			
			List<Place> places = new LinkedList<Place>();

			/** @author based on: wsn, http://stackoverflow.com/questions/1601151/how-do-i-check-in-sqlite-whether-a-table-exists (Stephen Quan)*/
			Cursor cEmpty = database.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", PlacesDbHelper.TABLE_NAME});
			boolean empty = true;
			if(cEmpty.moveToFirst()) {
				int entries = cEmpty.getInt(0);
				if(entries > 0) empty = false;
			}
			cEmpty.close();

			if(!empty) {
				// create and execute sql query
				String[] columns = null;
				Cursor c = database.query(PlacesDbHelper.TABLE_NAME, columns, null, null, PlacesDbHelper.KEY_TIMES_USED, null, null);
	
				int rows = c.getCount();
				Log.i(tag, "ROWS COUNT=" + rows);
				
				// iterate over returned values.				
				c.moveToFirst();
				for (int index = 0; index < c.getCount(); index++) {
					Place p = new Place(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getDouble(4), c.getDouble(5), c.getInt(6));
					
					Log.i(tag, "NEW PLACE: " + p.getDisplayText());
					
					places.add(p);
					c.moveToNext();
				}
			}
			
			database.close();
			return places;
		}

		@Override
		protected void onPostExecute(List<Place> list) {
			//TODO: ADD TO LISTVIEW
			placesListViewAdapter.setItems(list);
			progressDialog.dismiss();
			Toast.makeText(getActivity(), list.size() + " records loaded", Toast.LENGTH_SHORT).show();
		}
	}
}
