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
        getListView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        {// Set the font of the buttons
            stories = getResources().getStringArray(R.array.lovecraft_stories);
            getListView().setAdapter(new CustomAdapter(this, R.layout.single_row, stories));
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
//        Toast.makeText(StoriesActivity.this, "CLICKED: " + stories[position], Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(StoriesActivity.this, WebReaderActivity.class);
        intent.putExtra(getResources().getString(R.string.story_name), stories[position]);
        startActivity(intent);
    }
}
