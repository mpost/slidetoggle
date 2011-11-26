package com.moritzpost.slidetoggle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SlideToggleListAdapter extends BaseAdapter {

	private static final String[] content = new String[] { "Episode 1", "Episode 2", "Episode 3",
			"Episode 4", "Episode 5", "Episode 6", "Episode 7", "Episode 8", "Episode 9",
			"Episode 10", "Episode 11", "Episode 12", "Episode 13", };

	private LayoutInflater inflater;

	static class ViewHolder {

		public SlideToggle slideToggle;
	}

	public SlideToggleListAdapter(Activity activity) {
		inflater = activity.getLayoutInflater();
	}

	@Override
	public int getCount() {
		return content.length;
	}

	@Override
	public Object getItem(int position) {
		return content[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View rowView = convertView;
		if (rowView == null) {
			rowView = inflater.inflate(R.layout.slidetoggle, null, true);
			holder = createHolder(rowView);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		TextView textView = (TextView) holder.slideToggle.findViewById(R.id.text_content);
		textView.setText(content[position]);
		// holder.slideToggle.scrollTo(SlideToggle.TOGGLE_ITEM_WIDTH, 0);
		return rowView;
	}

	private ViewHolder createHolder(View rowView) {
		ViewHolder holder;
		holder = new ViewHolder();
		holder.slideToggle = (SlideToggle) rowView.findViewById(R.id.slidetoggle_scrollview);
		rowView.setTag(holder);
		return holder;
	}

}
