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
public class WhenListViewAdapter extends BaseAdapter {

	private Context context;
	private ListView listView;
	private List<When> whens;

	public WhenListViewAdapter(Context context, ListView listView) {
		this(context, listView, new LinkedList<When>());
	}

	public WhenListViewAdapter(Context context, ListView listView, List<When> whens) {
		this.context = context;
		this.listView = listView;
		this.whens = whens;
	}

	@Override
	public int getCount() {
		return whens.size();
	}

	@Override
	public Object getItem(int position) {
		return whens.get(position);
	}

	@Override
	public long getItemId(int position) {
		return whens.get(position).hashCode();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			LayoutInflater vi = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.list_item, null);
		}

		When when = whens.get(position);
		((TextView) view.findViewById(R.id.listItem_text)).setText(when.getDisplayText());
		//friend.setImage(view);

		return view;

	}


	public void setItems(List<When> whens) {
		this.whens = whens;
		notifyDataSetChanged();
	}

}
