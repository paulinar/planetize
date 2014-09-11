package paulinaramos.cs160.berkeley.edu.planetize;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by paulina on 9/1/14.
 */
public class MyArrayAdapter extends ArrayAdapter {

    public MyArrayAdapter(Context context, int textViewResourceId, String[] units) {
        super(context, textViewResourceId, units);
    }

    @Override
    public TextView getView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);
        Typeface customFont = Typeface.createFromAsset (getContext().getAssets(),
                "codelight.otf");
        v.setTypeface(customFont);
        return v;
    }

    @Override
    public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);
        Typeface customFont = Typeface.createFromAsset(getContext().getAssets(),
                "codelight.otf");
        v.setTypeface(customFont);
        return v;
    }

}