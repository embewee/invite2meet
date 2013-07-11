package de.tud.mobsen.invite2meet.when;

import java.util.ArrayList;
import java.util.List;

import de.tud.mobsen.invite2meet.ChooseFragment;
import de.tud.mobsen.invite2meet.MainActivity;
import de.tud.mobsen.invite2meet.R;
import de.tud.mobsen.invite2meet.objects.When;
import de.tud.mobsen.invite2meet.objects.WhenListViewAdapter;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FragmentWhen extends Fragment {
	private final static String tag = "FragmentWhen";
	private final static String SHOW_CHOOSE_DIALOG = "SHOW_CHOOSE_DIALOG";
	
	private ListView listView;
	private WhenListViewAdapter whenListViewAdapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.fragment_when, container, false);
		
		listView = (ListView) fragView.findViewById(R.id.fragwhen_listview);
		whenListViewAdapter = new WhenListViewAdapter(getActivity(), listView, getWhens()); 		
		listView.setAdapter(whenListViewAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
				Log.i(tag, "POS:" + Integer.toString(position));
				onItemClicked(position);
			}
		});
		return fragView;
	}
	
	private List<When> getWhens() {
		//TODO
		ArrayList<When> whens = new ArrayList<When>();
		whens.add(new When("now", "Now!", 0));
		whens.add(new When("five", "5 Min", 5));
		whens.add(new When("ten", "10 Min", 10));
		whens.add(new When("fifteen", "15 Min", 15));
		whens.add(new When("twenty", "20 Min", 20));
		whens.add(new When("thirty", "30 Min", 30));
		//TODO:
		//whens.add(new When("choose", "Choose..."));
		return whens;		
	}
	
	private void onItemClicked(int position) {
		MainActivity ma = (MainActivity) getActivity();
		
		When w = (When) whenListViewAdapter.getItem(position);
		ma.inviteSetWhen(w.getOffsetInMinutes());
		
		//TODO:
//		if(w.getKey().equals("choose")) {
//			ChooseFragment fragChoose = new ChooseFragment();
//			fragChoose.show(getActivity().getFragmentManager(), SHOW_CHOOSE_DIALOG);
//		}
		
		ma.setWhereActive();
	}
}
