package de.tud.mobsen.invite2meet;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
	}
}
