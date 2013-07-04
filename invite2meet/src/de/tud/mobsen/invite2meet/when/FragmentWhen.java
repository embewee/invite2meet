package de.tud.mobsen.invite2meet.when;

import java.util.ArrayList;
import java.util.List;

import de.tud.mobsen.invite2meet.R;
import de.tud.mobsen.invite2meet.objects.When;
import de.tud.mobsen.invite2meet.objects.WhenListViewAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentWhen extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.fragment_when, container, false);
		
		ListView friendsListView = (ListView) fragView.findViewById(R.id.fragwhen_listview);
		WhenListViewAdapter whenListViewAdapter = new WhenListViewAdapter(getActivity(), friendsListView, getWhens()); 		
		friendsListView.setAdapter(whenListViewAdapter);
		
		return fragView;
	}
	
	private List<When> getWhens() {
		//TODO
		ArrayList<When> whens = new ArrayList<When>();
		whens.add(new When("now", "Now!"));
		whens.add(new When("five", "5 Min"));
		whens.add(new When("ten", "10 Min"));
		whens.add(new When("fifteen", "15 Min"));
		whens.add(new When("choose", "Choose..."));
		return whens;		
	}
	


	
	
	
	
	
	
}
