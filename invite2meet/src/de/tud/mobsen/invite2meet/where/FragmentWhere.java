package de.tud.mobsen.invite2meet.where;

import java.util.ArrayList;
import java.util.List;

import de.tud.mobsen.invite2meet.R;
import de.tud.mobsen.invite2meet.objects.Place;
import de.tud.mobsen.invite2meet.objects.WhereListViewAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentWhere extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.fragment_where, container, false);
		
		ListView friendsListView = (ListView) fragView.findViewById(R.id.fragwhere_listview);
		WhereListViewAdapter whereListViewAdapter = new WhereListViewAdapter(getActivity(), friendsListView, getPlaces()); 		
		friendsListView.setAdapter(whereListViewAdapter);
		
		return fragView;
	}
	
	//TODO!
	private List<Place> getPlaces() {
		ArrayList<Place> places = new ArrayList<Place>();
		
		Place place1 = new Place(-1, "Here!");
		Place place2 = new Place(1, "Mensaecke");
		Place place3 = new Place(2, "Herrngartenbank");
		Place place4 = new Place(3, "Geheimplatz");
		
		places.add(place1);
		places.add(place2);
		places.add(place3);
		places.add(place4);
		
		return places;
	}
	
}
