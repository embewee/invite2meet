package de.tud.mobsen.invite2meet;

import java.util.LinkedList;
import java.util.List;

import de.tud.mobsen.invite2meet.db.FriendsDbHelper;
import de.tud.mobsen.invite2meet.objects.Friend;
import de.tud.mobsen.invite2meet.objects.FriendsListViewAdapter;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class FriendsFragment extends Fragment {
	
	private final static String SHOW_ADD_FRIEND_DIALOG = "SHOW_ADD_FRIEND_DIALOG";
	
	ProgressDialog progressDialog;
	
	ListView listView;
	FriendsListViewAdapter friendsListViewAdapter; 
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.fragment_friends, container, false);
		
		listView = (ListView) fragView.findViewById(R.id.friends_listview);
		friendsListViewAdapter = new FriendsListViewAdapter(getActivity(), listView, new LinkedList<Friend>()); 		
		listView.setAdapter(friendsListViewAdapter);
		loadFriends();
		
		Button btnAdd = (Button) fragView.findViewById(R.id.friends_add);
		btnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onAdd();
			}
		});
		
		Button btnRemove = (Button) fragView.findViewById(R.id.friends_delete);
		btnRemove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onRemove();
			}
		});
		
		return fragView;
	}
	
	private void loadFriends() {
		progressDialog = ProgressDialog.show(getActivity(), "Loading Friends", "Please wait...", true);
		new LoadFriendsDatabase().execute();
	}
	
	private void onAdd() {
		AddFriendFragment fragNewFriend = new AddFriendFragment();
		fragNewFriend.show(getActivity().getFragmentManager(), SHOW_ADD_FRIEND_DIALOG);
	}
	
	private void onRemove() {
		
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
			
			if(!empty) {
				// create and execute sql query
				//String[] columns = new String[] {  };
				String[] columns = null;
				Cursor c = database.query(FriendsDbHelper.TABLE_NAME, columns, null, null, FriendsDbHelper.KEY_NAME, null, null);
	
				// iterate over returned values.				
				c.moveToFirst();
				for (int index = 0; index < c.getCount(); index++) {
					Friend f = new Friend(c.getInt(0), c.getString(1));
					friends.add(f);
					c.moveToNext();
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
