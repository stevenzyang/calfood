package com.floor7.calfood;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.*;

import android.widget.*;
import android.util.*;

import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
	private static final String TAG = "CalFood";
	
	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current tab position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
//		setContentView(R.layout.diningmenu);
		//Make a button
//		Button myButton = (Button) findViewById(R.id.my_button);
		// Set up the action bar to show tabs.

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// For each of the sections in the app, add a tab to the action bar.
		actionBar.addTab(actionBar.newTab().setText(R.string.title_section1)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_section2)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_section3)
				.setTabListener(this));
		
		String[] items = {"test1", "test2", "test3"};

//		setContentView(R.layout.diningmenu);
//		
//		ListView listView = (ListView) findViewById(R.id.menu_items);
//		ArrayAdapter menuAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//		listView.setAdapter(menuAdapter);
//		
//		for (int i = 0; i < menuAdapter.getCount(); i++){
//			;
//		}
		String[] diningNames = {"Crossroads", "Cafe 3", "Foothill", "Clark Kerr"};
		Food[] testFoods = {new Food("waffle fries"), new Food("chocolate")};
		
		DiningView x = new DiningView(this, "Foothill", testFoods, 5);
		ViewGroup parent = (ViewGroup)findViewById(R.id.container);
		parent.addView(x);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current tab position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current tab position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, show the tab contents in the
		// container view.
		Log.d(TAG, "onTabSelected");
//		Fragment fragment = new DummySectionFragment();
//		Bundle args = new Bundle();
//		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER,
//				tab.getPosition() + 1);
//		fragment.setArguments(args);
//		getSupportFragmentManager().beginTransaction()
//				.replace(R.id.container, fragment).commit();
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return textView;
		}
	}

}
