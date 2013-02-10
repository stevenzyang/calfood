package com.floor7.calfood;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.*;

import android.widget.*;
import android.util.*;

import android.widget.Button;
import android.widget.GridLayout.Spec;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
	private static final String TAG = "CalFood";

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current tab position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private static final String[] diningNames = { "Crossroads", "Cafe 3",
			"Foothill", "Clark Kerr" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		// setContentView(R.layout.diningmenu);
		// Make a button
		// Button myButton = (Button) findViewById(R.id.my_button);
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

		// setContentView(R.layout.diningmenu);
		//
		// ListView listView = (ListView) findViewById(R.id.menu_items);
		// ArrayAdapter menuAdapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, items);
		// listView.setAdapter(menuAdapter);
		//
		// for (int i = 0; i < menuAdapter.getCount(); i++){
		// ;
		// }
		// String[] diningNames = {"Crossroads", "Cafe 3", "Foothill",
		// "Clark Kerr"};

		placeDiningHalls();
	}

	private void placeDiningHalls() {

		Food[] testFoods = { new Food("waffle fries"), new Food("chocolate"), new Food("chocolate"), new Food("chocolate"), new Food("chocolate"), new Food("chocolate") };

		Point size = new Point();
		getWindowManager().getDefaultDisplay().getSize(size);
		int screenWidth = size.x;
		int screenHeight = size.y;
		int halfScreenHeight = (int) (screenWidth * 0.5);
		int halfScreenWidth = (int) (screenHeight * 0.5);

		// GridLayout gridLayout = new GridLayout(this);
		// gridLayout.setColumnCount(2);
		// gridLayout.setRowCount(2);
		LinearLayout bigL = new LinearLayout(this);
		bigL.setOrientation(LinearLayout.VERTICAL);
	
		for (int i = 0; i < 2; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					screenWidth, halfScreenHeight);
			params.weight = 1;
			params.gravity = Gravity.CENTER;
			LinearLayout l = new LinearLayout(this);
			l.setOrientation(LinearLayout.HORIZONTAL);
			l.setLayoutParams(params);
			
			for (int j = 0; j < 2; j++) {
				int index = i*2+j;
				// Spec row = GridLayout.spec(i/2);
				// Spec col = GridLayout.spec(i%2);
				int wrap = LinearLayout.LayoutParams.WRAP_CONTENT;
				LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
						halfScreenWidth, halfScreenHeight);
				p.weight = 1;
				p.gravity = Gravity.CENTER;
				DiningView d = new DiningView(this, diningNames[index], testFoods,
						index);
				d.setLayoutParams(p);
				l.addView(d);
			}
			bigL.addView(l);
		}

		ViewGroup parent = (ViewGroup) findViewById(R.id.container);
		parent.addView(bigL);

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
		// Fragment fragment = new DummySectionFragment();
		// Bundle args = new Bundle();
		// args.putInt(DummySectionFragment.ARG_SECTION_NUMBER,
		// tab.getPosition() + 1);
		// fragment.setArguments(args);
		// getSupportFragmentManager().beginTransaction()
		// .replace(R.id.container, fragment).commit();
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
