package activity.cthulhu.com.cthulhumythos.ListActivities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import activity.cthulhu.com.cthulhumythos.About.AboutActivity;
import activity.cthulhu.com.cthulhumythos.Util.CustomAdapter;
import activity.cthulhu.com.cthulhumythos.R;

public class MainActivity extends Activity {
    public static Typeface typeface;
    private String[] categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/october_crow.ttf");
        setActionBar();
        {// Set the font of the buttons
            ListView listView = findViewById(R.id.main_menu_listView);
            categories = getResources().getStringArray(R.array.main_activity_list);
            listView.setAdapter(new CustomAdapter(this, R.layout.single_row, categories));
            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = null;
                    String[] categories = getResources().getStringArray(R.array.categories);
                    if (i == 0) {
                        intent = new Intent(MainActivity.this, StoriesActivity.class);

                    } else if (i < categories.length + 1) {
                        intent = new Intent(MainActivity.this, WikiListActivity.class);
                        intent.putExtra(getResources().getString(R.string.category), categories[i - 1]);
                    } else {
                        intent = new Intent(MainActivity.this, AboutActivity.class);
                    }

                    if (intent != null) {
                        startActivity(intent);
                    }
                }
            };
            listView.setOnItemClickListener(itemClickListener);
        }

    }

    private void setActionBar() {
        // Set the ActionBar's font and text color
        // Get the ActionBar
        ActionBar ab = getActionBar();

        // Create a TextView programmatically.
        TextView tv = new TextView(this);

        // Create a LayoutParams for TextView
        LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, // Width of TextView
                LayoutParams.WRAP_CONTENT); // Height of TextView

        // Apply the layout parameters to TextView widget
        tv.setLayoutParams(lp);

        // Set text to display in TextView
        tv.setText(R.string.app_title); // ActionBar title text

        // Set the text color of TextView to black
//            tv.setTextColor(Color.GREEN);
        tv.setTextSize(46f);
        tv.setGravity(Gravity.CENTER);

        // Set the monospace font for TextView text
        // This will change ActionBar title text font
        tv.setTypeface(typeface);

        // Set the ActionBar display option
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        // Finally, set the newly created TextView as ActionBar custom view
        ab.setCustomView(tv);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
