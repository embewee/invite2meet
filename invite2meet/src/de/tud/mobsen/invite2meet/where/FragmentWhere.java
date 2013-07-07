package de.tud.mobsen.invite2meet.where;

import java.util.LinkedList;
import java.util.List;

import de.tud.mobsen.invite2meet.R;
import de.tud.mobsen.invite2meet.db.PlacesDbHelper;
import de.tud.mobsen.invite2meet.objects.Place;
import de.tud.mobsen.invite2meet.objects.PlacesListViewAdapter;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentWhere extends Fragment {
	
	private PlacesListViewAdapter placesListViewAdapter;
	ProgressDialog progressDialog;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.fragment_where, container, false);
		
		ListView friendsListView = (ListView) fragView.findViewById(R.id.fragwhere_listview);
		placesListViewAdapter = new PlacesListViewAdapter(getActivity(), friendsListView, new LinkedList<Place>()); 		
		friendsListView.setAdapter(placesListViewAdapter);
		getPlaces();
		
		return fragView;
	}
	
	private void getPlaces() {
		progressDialog = ProgressDialog.show(getActivity(), "Loading places", "Please wait...", true);
		new LoadPlacesDatabase().execute();
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
				
				// iterate over returned values.				
				c.moveToFirst();
				for (int index = 0; index < c.getCount(); index++) {
					Place p = new Place(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getDouble(4), c.getDouble(5), c.getInt(6));
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
