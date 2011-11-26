package com.moritzpost.slidetoggle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class SlideToggleActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_list);

		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(new SlideToggleListAdapter(this));
	}

}