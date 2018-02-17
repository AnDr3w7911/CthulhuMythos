package activity.cthulhu.com.cthulhumythos.ListActivities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import activity.cthulhu.com.cthulhumythos.Readers.WebReaderActivity;
import activity.cthulhu.com.cthulhumythos.Util.CustomAdapter;
import activity.cthulhu.com.cthulhumythos.R;
import activity.cthulhu.com.cthulhumythos.Readers.ReaderActivity;

public class StoriesActivity extends ListActivity {
    private String[] stories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
//        {// Set the ActionBar's font and text color
//            // Get the ActionBar
//            ActionBar ab = getActionBar();
//
//            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/october_crow.ttf");
//            // Create a TextView programmatically.
//            TextView tv = new TextView(this);
//
//            // Create a LayoutParams for TextView
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
//                    RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
//
//            // Apply the layout parameters to TextView widget
//            tv.setLayoutParams(lp);
//
//            // Set text to display in TextView
//            tv.setText(R.string.works_button); // ActionBar title text
//
//            // Set the text color of TextView to black
////            tv.setTextColor(Color.GREEN);
//            tv.setTextSize(46f);
//            tv.setGravity(Gravity.CENTER);
//
//            // Set the monospace font for TextView text
//            // This will change ActionBar title text font
//            tv.setTypeface(typeface);
//
//            // Set the ActionBar display option
//            ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//
//            // Finally, set the newly created TextView as ActionBar custom view
//            ab.setCustomView(tv);
//        }
        {// Set the font of the buttons
            stories = getResources().getStringArray(R.array.lovecraft_stories);
            getListView().setAdapter(new CustomAdapter(this, R.layout.single_row, stories));
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(StoriesActivity.this, "CLICKED: " + stories[position], Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(StoriesActivity.this, WebReaderActivity.class);
        intent.putExtra(getResources().getString(R.string.story_name), stories[position]);
        startActivity(intent);
    }
}
