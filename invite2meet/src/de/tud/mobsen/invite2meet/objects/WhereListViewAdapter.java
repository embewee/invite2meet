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
public class WhereListViewAdapter extends BaseAdapter {

	private Context context;
	private ListView listView;
	private List<Place> places;

	public WhereListViewAdapter(Context context, ListView listView) {
		this(context, listView, new LinkedList<Place>());
	}

	public WhereListViewAdapter(Context context, ListView listView, List<Place> places) {
		this.context = context;
		this.listView = listView;
		this.places = places;
	}

	@Override
	public int getCount() {
		return places.size();
	}

	@Override
	public Object getItem(int position) {
		return places.get(position);
	}

	@Override
	public long getItemId(int position) {
		return places.get(position).hashCode();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			LayoutInflater vi = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.list_item_image, null);
		}

//TODO: IMAGE



		Place place = places.get(position);
		((TextView) view.findViewById(R.id.timestamp)).setText(place.getDisplayText());
		//friend.setImage(view);

		return view;

	}

	public void setItems(List<Place> places) {
		this.places = places;
		notifyDataSetChanged();
	}

}
