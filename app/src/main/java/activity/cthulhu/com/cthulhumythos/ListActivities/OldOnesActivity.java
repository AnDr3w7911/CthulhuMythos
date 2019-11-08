package activity.cthulhu.com.cthulhumythos.ListActivities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import activity.cthulhu.com.cthulhumythos.Util.CustomAdapter;
import activity.cthulhu.com.cthulhumythos.Readers.OldOnesWikiReader;
import activity.cthulhu.com.cthulhumythos.R;

/**
 * Created by Andrew on 1/27/2018.
 */

public class OldOnesActivity extends ListActivity {
    String[] oldOnes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }

        {// Set the font of the buttons
            oldOnes = getResources().getStringArray(R.array.old_ones);
            getListView().setAdapter(new CustomAdapter(this, R.layout.single_row, oldOnes));
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(OldOnesActivity.this, "CLICKED: " + oldOnes[position], Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(OldOnesActivity.this, OldOnesWikiReader.class);
        intent.putExtra(getResources().getString(R.string.index), position);
        startActivity(intent);
    }
}
