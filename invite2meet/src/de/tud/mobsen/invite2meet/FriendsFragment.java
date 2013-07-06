package de.tud.mobsen.invite2meet;

import java.util.LinkedList;

import de.tud.mobsen.invite2meet.objects.Friend;
import de.tud.mobsen.invite2meet.objects.FriendsListViewAdapter;
import android.app.Fragment;
import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class FriendsFragment extends Fragment {
	
	private final static String SHOW_ADD_FRIEND_DIALOG = "SHOW_ADD_FRIEND_DIALOG";
	
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
		
	}
	
	private void onAdd() {
		AddFriendFragment fragNewFriend = new AddFriendFragment();
		fragNewFriend.show(getActivity().getFragmentManager(), SHOW_ADD_FRIEND_DIALOG);
	}
	
	private void onRemove() {
		
	}


}
