package de.tud.mobsen.invite2meet.who;

import java.util.LinkedList;
import java.util.List;

import de.tud.mobsen.invite2meet.MainActivity;
import de.tud.mobsen.invite2meet.R;
import de.tud.mobsen.invite2meet.db.FriendsDbHelper;
import de.tud.mobsen.invite2meet.objects.Friend;
import de.tud.mobsen.invite2meet.objects.FriendsListViewAdapter;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentWho extends Fragment {
	
	private ProgressDialog progressDialog;
	private ListView listView;
	private FriendsListViewAdapter friendsListViewAdapter; 

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.fragment_who, container, false);
		
		listView = (ListView) fragView.findViewById(R.id.fragwho_listview);
		friendsListViewAdapter = new FriendsListViewAdapter(getActivity(), listView, new LinkedList<Friend>()); 		
		listView.setAdapter(friendsListViewAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				onItemClicked(position);
			}
		});
		loadFriends();
		
		return fragView;
	} 
	
	private void loadFriends() {
		progressDialog = ProgressDialog.show(getActivity(), "Loading Friends", "Please wait...", true);
		new LoadFriendsDatabase().execute();
	}
	
	private void onItemClicked(int position) {
		MainActivity ma = (MainActivity) getActivity();
		ma.inviteNew();
		
		Friend friend = (Friend) friendsListViewAdapter.getItem(position);
		ma.inviteSetWho(friend.getName());
		
		ma.setWhenActive();	
	}
	
	
	/**
	 * Member class used for selecting all database records.
	 * @author based on: wsn
	 */
	private class LoadFriendsDatabase extends AsyncTask<Void, Void, List<Friend>> {
		protected List<Friend> doInBackground(Void... params) {			
			FriendsDbHelper databaseHelper = new FriendsDbHelper(getActivity());
			SQLiteDatabase database = databaseHelper.getReadableDatabase();
			List<Friend> friends = new LinkedList<Friend>();
			
			/** @author based on: wsn, http://stackoverflow.com/questions/1601151/how-do-i-check-in-sqlite-whether-a-table-exists (Stephen Quan)*/
			Cursor cEmpty = database.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", FriendsDbHelper.TABLE_NAME});
			boolean empty = true;
			if(cEmpty.moveToFirst()) {
				int entries = cEmpty.getInt(0);
				if(entries > 0) empty = false;
			}
			cEmpty.close();
			
			//check if table exists and table is not empty
			if(!empty) {
				// create and execute sql query
				//String[] columns = new String[] {  };
				String[] columns = null;
				Cursor cData = database.query(FriendsDbHelper.TABLE_NAME, columns, null, null, FriendsDbHelper.KEY_NAME, null, null);
	
				// iterate over returned values.
				cData.moveToFirst();
				for (int index = 0; index < cData.getCount(); index++) {
					Friend f = new Friend(cData.getInt(0), cData.getString(1));
					friends.add(f);
					cData.moveToNext();
				}
			}
			
			database.close();
			return friends;
		}

		@Override
		protected void onPostExecute(List<Friend> list) {
			//TODO: ADD TO LISTVIEW
			friendsListViewAdapter.setItems(list);
			progressDialog.dismiss();
			Toast.makeText(getActivity(), list.size() + " records loaded", Toast.LENGTH_SHORT).show();
		}
	}
}
