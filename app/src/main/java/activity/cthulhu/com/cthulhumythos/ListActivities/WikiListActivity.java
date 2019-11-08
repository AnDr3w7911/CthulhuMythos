package activity.cthulhu.com.cthulhumythos.ListActivities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;

import activity.cthulhu.com.cthulhumythos.R;
import activity.cthulhu.com.cthulhumythos.Readers.WikiReaderActivity;
import activity.cthulhu.com.cthulhumythos.Util.CustomAdapter;
import activity.cthulhu.com.cthulhumythos.Util.WikiUtil;
import activity.cthulhu.com.cthulhumythos.WikiObjects.UnexpandedArticle;
import activity.cthulhu.com.cthulhumythos.WikiObjects.UnexpandedListArticleResultSet;

/**
 * Created by Andrew on 1/28/2018.
 */

public class WikiListActivity extends ListActivity {

    String[] content;
    UnexpandedArticle[] items;
    String category;
    UnexpandedListArticleResultSet resultSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        getListView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        category = getIntent().getStringExtra(getResources().getString(R.string.category));
        Log.d("WikiListActivity", category);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String object = WikiUtil.getJSONObjectFromURL("https://lovecraft.fandom.com/api/v1/Articles/List?category=" + category + "&limit=250");
//                    String object = WikiUtil.getJSONObjectFromURL("http://lovecraft.wikia.com/api/v1/Articles/Top?category=" + category + "&limit=250");
                    Gson gson = new Gson();

                    resultSet = gson.fromJson(object, UnexpandedListArticleResultSet.class);
                    items = resultSet.items;
                    content = new String[resultSet.items.length];
                    for (int i = 0; i < resultSet.items.length; ++i) {
                        content[i] = resultSet.items[i].title;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getListView().setAdapter(new CustomAdapter(WikiListActivity.this, R.layout.single_row, content));
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(WikiListActivity.this, WikiReaderActivity.class);
        intent.putExtra(getResources().getString(R.string.base_path), resultSet.basepath);
        intent.putExtra("ITEM", items[position]);
        intent.putExtra(getResources().getString(R.string.index), position);
        intent.putExtra(getResources().getString(R.string.items), resultSet);
        intent.putExtra(getResources().getString(R.string.category), category);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(WikiListActivity.this, MainActivity.class));
        finish();
    }
}
