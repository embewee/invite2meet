package de.tud.mobsen.invite2meet;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;

public class ChooseFragment extends DialogFragment {
	private final static String tag = "ChooseFragment";
	
	TimePicker time;
	Button btnOk;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.fragment_choose, container, false);
		
		getDialog().setTitle("Choose Time"); //TODO: id
		
		//EditText
		time = (TimePicker) fragView.findViewById(R.id.choose_picker);
		
		//Save button
		btnOk = (Button) fragView.findViewById(R.id.choose_ok);
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onOk();
			}
		});		
		return fragView;
	}
	
	private void onOk() {
		Log.i(tag, "OK");
		//TODO
		int hour = time.getCurrentHour();
		
		Log.i(tag, "HOUR: " + Integer.toString(hour));
		
		int min = time.getCurrentMinute();
		
		Log.i(tag, "MIN:" + Integer.toString(min));
		
		getDialog().dismiss();
	}
}