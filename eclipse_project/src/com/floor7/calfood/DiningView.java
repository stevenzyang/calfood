package com.floor7.calfood;

import android.view.*;
import android.widget.*;
import android.content.*;
import android.util.*;

class CustomDiningAdapter extends ArrayAdapter<Food> {

    private final Context context;
    private final Food[] values;

    public CustomDiningAdapter(Context context, Food[] values) {
        super(context, R.id.dining_list_row, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.dining_list_row, parent, false);

        TextView name = (TextView) rowView.findViewById(R.id.food_name);
        name.setText(values[position].name);
        name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);

        TextView ratingView = (TextView) rowView.findViewById(R.id.rating);
        ratingView.setText(values[position].rating+"");
        ratingView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);

        return rowView;
    }
}

public class DiningView extends ListView {
    public DiningView(Context ctx, String title, Food[] bestFoods, int totalRating) {
        super(ctx);
        TextView txt = new TextView(ctx);
        txt.setText(title);
        txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        txt.setGravity(Gravity.CENTER);
        addHeaderView(txt);

        setAdapter(new CustomDiningAdapter(ctx, bestFoods));
        setDivider(null);
        setHeaderDividersEnabled(true);

        TextView t = new TextView(ctx);
        t.setText("Rating: "+totalRating);
        t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        t.setGravity(Gravity.CENTER);
        addFooterView(t);

    }
}
