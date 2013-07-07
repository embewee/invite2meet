package de.tud.mobsen.invite2meet.objects;

import java.util.LinkedList;
import java.util.List;

import de.tud.mobsen.invite2meet.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author based on: wsn, WiFun5
 *
 */
public class FriendsListViewAdapter extends BaseAdapter {

	private Context context;
	private ListView listView;
	private List<Friend> friends;

	public FriendsListViewAdapter(Context context, ListView listView) {
		this(context, listView, new LinkedList<Friend>());
	}

	public FriendsListViewAdapter(Context context, ListView listView, List<Friend> friends) {
		this.context = context;
		this.listView = listView;
		this.friends = friends;
	}

	@Override
	public int getCount() {
		return friends.size();
	}

	@Override
	public Object getItem(int position) {
		return friends.get(position);
	}

	@Override
	public long getItemId(int position) {
		return friends.get(position).hashCode();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			LayoutInflater vi = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.list_item_check, null);
		}

		final CheckBox checkBox = ((CheckBox) view.findViewById(R.id.listItemCheck_checkbox));
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				listView.setItemChecked(position, checkBox.isChecked());
			}
		});

		Friend friend = friends.get(position);
		((TextView) view.findViewById(R.id.listItemCheck_text)).setText(friend.toString());
		//friend.setImage(view);

		return view;

	}

	public void setItems(List<Friend> friends) {
		this.friends = friends;
		notifyDataSetChanged();
	}

}
