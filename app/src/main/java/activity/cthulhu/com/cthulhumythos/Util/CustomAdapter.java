package activity.cthulhu.com.cthulhumythos.Util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import activity.cthulhu.com.cthulhumythos.ListActivities.MainActivity;
import activity.cthulhu.com.cthulhumythos.R;

/**
 * Created by Andrew on 1/21/2018.
 */
public class CustomAdapter extends ArrayAdapter<String> {
    private Typeface typeface;
    private int resID;
    private Context context;
    private String[] items;

    public CustomAdapter(@NonNull Context context, int resource, String[] items) {
        super(context, resource, items);
        this.resID = resource;
        this.context = context;
        this.items = items;
        typeface = MainActivity.typeface;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        View view = v;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(resID, null);
        }
        TextView textView = view.findViewById(R.id.singleRowTextView);
        textView.setTypeface(typeface);
        textView.setText(items[position]);


        return view;
    }
}
