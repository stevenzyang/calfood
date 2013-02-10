package com.floor7.calfood;

import android.view.*;
import android.widget.*;
import android.content.*;

public class DiningView extends ListView {
	public DiningView(Context ctx, String title, Food[] bestFoods) {
		super(ctx);
		TextView txt = new TextView(ctx);
		txt.setText(title);
		addHeaderView(txt);
		for(int i=0; i<bestFoods.length; i++) {
			Food f = bestFoods[i];
			TextView t = new TextView(ctx);
			t.setText(f.name);
			
		}
	}
	
}
