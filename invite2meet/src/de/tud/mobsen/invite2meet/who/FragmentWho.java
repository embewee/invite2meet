package de.tud.mobsen.invite2meet.who;

import java.util.ArrayList;
import java.util.List;

import de.tud.mobsen.invite2meet.R;
import de.tud.mobsen.invite2meet.objects.Friend;
import de.tud.mobsen.invite2meet.objects.FriendsListViewAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentWho extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.fragment_who, container, false);
		
		ListView friendsListView = (ListView) fragView.findViewById(R.id.fragwho_listview);
		FriendsListViewAdapter friendListViewAdapter = new FriendsListViewAdapter(getActivity(), friendsListView, getFriends()); 		
		friendsListView.setAdapter(friendListViewAdapter);
		
		return fragView;
	} 
	
	private List<Friend> getFriends() {
		//TODO
		ArrayList<Friend> friends = new ArrayList<Friend>();
		friends.add(new Friend(1, "Martin"));
		friends.add(new Friend(2, "Paulius"));
		friends.add(new Friend(3, "Michael"));
		return friends;		
	}
	
}
