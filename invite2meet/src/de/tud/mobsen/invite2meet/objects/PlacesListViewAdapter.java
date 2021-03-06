package de.tud.mobsen.invite2meet.objects;

import java.util.LinkedList;
import java.util.List;

import de.tud.mobsen.invite2meet.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author based on: wsn, WiFun5
 *
 */
public class PlacesListViewAdapter extends BaseAdapter {

	private Context context;
	private ListView listView;
	private List<Place> places;

	public PlacesListViewAdapter(Context context, ListView listView) {
		this(context, listView, new LinkedList<Place>());
	}

	public PlacesListViewAdapter(Context context, ListView listView, List<Place> places) {
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

		Place place = places.get(position);
		((TextView) view.findViewById(R.id.listItemImage_text)).setText(place.getDisplayText());
		Bitmap bitmap = BitmapFactory.decodeFile(place.getPhotoUri());
		Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
		ImageView imageView = (ImageView) view.findViewById(R.id.listItemImage_image);
		imageView.setImageBitmap(resized);

		return view;
	}

	public void setItems(List<Place> places) {
		this.places = places;
		notifyDataSetChanged();
	}

}
