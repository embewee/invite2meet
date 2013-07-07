package de.tud.mobsen.invite2meet;

import de.tud.mobsen.invite2meet.db.FriendsDbHelper;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFriendFragment extends DialogFragment {

	private final static String tag = "AddFriendFragment";
	
	private ProgressDialog progressDialog;
	
	private EditText nameTextField;
	private Button btnOk;
	
	private String friendsName;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.fragment_add_friend, container, false);
		
		getDialog().setTitle("Add Friend"); //TODO: id
		
		//EditText
		nameTextField = (EditText) fragView.findViewById(R.id.addfriend_name);
		
		//Save button
		btnOk = (Button) fragView.findViewById(R.id.addfriend_ok);
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onOk();
			}
		});		
		return fragView;
	}
	
	private void onOk() {
		progressDialog = ProgressDialog.show(getActivity(), "Looking for friend", "Please wait...", true);
		friendsName = nameTextField.getEditableText().toString();
		
		//TODO: check for friend, perform remote steps, and retrieve information 
		
		
		
		
		
		
		//@author based on: wsn
		new SaveFriendToDatabase().execute();
		
		
		
		
		
		
		
	}
	
	//####################### SAVE TO DB #################################
	
	/**
	 * Member class which is used for a background database save operation of
	 * the current Wi-Fi scan results.
	 * @author based on: wsn
	 */
	private class SaveFriendToDatabase extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... arg0) {
			FriendsDbHelper databaseHelper = new FriendsDbHelper(getActivity());
			SQLiteDatabase database = databaseHelper.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(FriendsDbHelper.KEY_NAME, friendsName);
			long rowId = database.insert(FriendsDbHelper.TABLE_NAME, null, values);
			database.close();
			
			if(rowId < 0) {
				return "Could not add friend";
			} else {
				return "Friend successfully added";
			}
		}
		
		@Override
		protected void onPostExecute(String message) {
			progressDialog.dismiss();
			getDialog().dismiss();
			Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
		}
	}
}
