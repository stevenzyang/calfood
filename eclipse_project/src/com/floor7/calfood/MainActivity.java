package com.floor7.calfood;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
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
	private static final String[] diningHalls = { ScrapeTest.XR, ScrapeTest.C3, ScrapeTest.FH, ScrapeTest.CK };
	
	//temporary information stored here
	private static ArrayList<ArrayList<Food>> allFoods = new ArrayList<ArrayList<Food>>();
	private static Date today;
	
	/**
	 * Initializes UI, starts scraper thread
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Calendar cal = Calendar.getInstance();
		today = new Date((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
		setContentView(R.layout.activity_main);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// For each of the sections in the app, add a tab to the action bar.
		actionBar.addTab(actionBar.newTab().setText(R.string.title_section1)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_section2)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_section3)
				.setTabListener(this));
		
		new ScrapeFoods().execute(today);
	}
	
	
	protected void onReceivedFoods(){
		int[] featured = {R.id.crossroads_featured, R.id.cafe3_featured, R.id.foothill_featured, R.id.clark_kerr_featured};
		for (int i = 0; i < featured.length; i++){
			ListView lv = (ListView) findViewById(featured[i]);
			ArrayAdapter<String> adapter = new ArrayAdapter(this,R.layout.food_text,allFoods.get(i));
			lv.setAdapter(adapter);
		}
	}
	
	//Scraper thread
	private class ScrapeFoods extends AsyncTask<Date, Void, ArrayList<ArrayList<Food>>>{
		@Override
		protected ArrayList<ArrayList<Food>> doInBackground(Date... date) {
			ArrayList<ArrayList<Food>> foods = new ArrayList<ArrayList<Food>>();
			for (int i = 0; i < diningHalls.length; i++){
				try {
					foods.add(ScrapeTest.getFoods(diningHalls[i], date[0]));
				} catch (IOException e) {
					ArrayList<Food> error = new ArrayList<Food>();
					error.add(new Food("Network error"));
					foods.add(error);
				}
			}
			return foods;
		}
		
		@Override
		protected void onPostExecute(ArrayList<ArrayList<Food>> result) {
			allFoods = result;
			onReceivedFoods();
	     }
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
