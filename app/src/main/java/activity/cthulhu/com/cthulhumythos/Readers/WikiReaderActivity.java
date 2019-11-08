package activity.cthulhu.com.cthulhumythos.Readers;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import activity.cthulhu.com.cthulhumythos.ListActivities.MainActivity;
import activity.cthulhu.com.cthulhumythos.ListActivities.WikiListActivity;
import activity.cthulhu.com.cthulhumythos.R;
import activity.cthulhu.com.cthulhumythos.Util.OnSwipeTouchListener;
import activity.cthulhu.com.cthulhumythos.Util.WikiUtil;
import activity.cthulhu.com.cthulhumythos.WikiObjects.ContentResult;
import activity.cthulhu.com.cthulhumythos.WikiObjects.ListElement;
import activity.cthulhu.com.cthulhumythos.WikiObjects.Section;
import activity.cthulhu.com.cthulhumythos.WikiObjects.SectionContent;
import activity.cthulhu.com.cthulhumythos.WikiObjects.SectionImages;
import activity.cthulhu.com.cthulhumythos.WikiObjects.UnexpandedArticle;
import activity.cthulhu.com.cthulhumythos.WikiObjects.UnexpandedListArticleResultSet;

public class WikiReaderActivity extends AppCompatActivity {

    protected TextView wikiDesc;
    protected ImageView imageView;
    protected int index;
    private TextView wikiURL;
    //    LinearLayout wikiContentLayout;
    UnexpandedArticle item;
    Section[] sections;
    private String url;
    private List<String> imageURLS;
    private StringBuilder wikiText;
    UnexpandedListArticleResultSet resultSet;
    String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_reader);


        imageURLS = new ArrayList<String>();
        wikiDesc = findViewById(R.id.wikiDescTextView);
//        wikiContentLayout = findViewById(R.id.wikiContent);
        imageView = findViewById(R.id.wikiImage);
        wikiURL = findViewById(R.id.wikiURL);
        item = (UnexpandedArticle) getIntent().getSerializableExtra("ITEM");
        resultSet = (UnexpandedListArticleResultSet) getIntent().getSerializableExtra(getResources().getString(R.string.items));
        index = getIntent().getIntExtra(getResources().getString(R.string.index), 0);
        category = getIntent().getStringExtra(getResources().getString(R.string.category));
//        setTitleBar(item.title);
        {
            CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.wiki_reader_collapsing_toolbar);
            collapsingToolbar.setTitle(item.title);

            collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.colorText));
            collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.colorText));
//        collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER);
            collapsingToolbar.setExpandedTitleGravity(Gravity.CENTER | Gravity.BOTTOM);
            collapsingToolbar.setCollapsedTitleTypeface(MainActivity.typeface);
            collapsingToolbar.setExpandedTitleTypeface(MainActivity.typeface);
        }
        url = getIntent().getStringExtra(getResources().getString(R.string.base_path)) + item.url;
        wikiURL.setText(url);
        getWikiContent();

        OnSwipeTouchListener listener = new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                goToItem(index - 1);
//                Toast.makeText(WikiReaderActivity.this, "right", Toast.LENGTH_SHORT).show();

            }

            public void onSwipeLeft() {
//                Toast.makeText(WikiReaderActivity.this, "left", Toast.LENGTH_SHORT).show();
                goToItem(index + 1);
            }
        };
        findViewById(R.id.wiki_scroll_view).setOnTouchListener(listener);
        findViewById(R.id.wiki_reader_Layout).setOnTouchListener(listener);
    }

    private void goToItem(int index) {
        Intent intent = new Intent(WikiReaderActivity.this, WikiReaderActivity.class);
        intent.putExtra(getResources().getString(R.string.base_path), resultSet.basepath);
        intent.putExtra("ITEM", resultSet.items[index]);
        intent.putExtra(getResources().getString(R.string.index), index);
        intent.putExtra(getResources().getString(R.string.items), resultSet);
        intent.putExtra(getResources().getString(R.string.category), category);
        startActivity(intent);
    }

    public void openWebPage(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void getWikiContent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonStr = WikiUtil.getJSONObjectFromURL("https://lovecraft.fandom.com/api/v1/Articles/AsSimpleJson?id=" + item.id);
                    Gson gson = new Gson();
                    ContentResult contentResult = gson.fromJson(jsonStr, ContentResult.class);
                    sections = contentResult.sections;
                    parseContent();
                    String jsonStr2 = WikiUtil.getJSONObjectFromURL("https://lovecraft.fandom.com/api/v1/Articles/Details?ids=" + item.id + "&abstract=10&width=500&height=500");
                    String subStr = jsonStr2.substring(jsonStr2.indexOf("thumbnail\":\"") + 12);

                    final String thumbnail = subStr.substring(0, subStr.indexOf("\"")).replace("\\", "");


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!thumbnail.contains("https:")) {
                                imageView.setImageResource(R.drawable.elder_sign);
                            } else {
                                Picasso.get().load(thumbnail).into(imageView);
                            }
                            wikiDesc.setText(wikiText);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseContent() {
        // parse content
        wikiText = new StringBuilder();
        for (Section section : sections) {
            for (SectionImages images : section.images) {
                imageURLS.add(images.src);
            }
            wikiText.append("\t\t" + section.title + "\n\n");
            for (SectionContent sectionContent : section.content) {
                if (sectionContent.text != null && !sectionContent.text.isEmpty()) {
                    wikiText.append("\t" + sectionContent.text + "\n\n");
                }
                if (sectionContent.elements != null) {
                    for (ListElement element : sectionContent.elements) {
                        wikiText.append("\t*\t\t" + element.text + "\n\n");
                    }
                }
            }
        }

    }

    protected void setTitleBar(String title) {

        ActionBar ab = getActionBar();
        if (ab != null) {// Set the ActionBar's font and text color
            // Get the ActionBar

            Typeface typeface = MainActivity.typeface;
            // Create a TextView programmatically.
            TextView tv = new TextView(this);
            tv.setMaxLines(1);

            // Create a LayoutParams for TextView
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                    RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView

            // Apply the layout parameters to TextView widget
            tv.setLayoutParams(lp);

            // Set text to display in TextView
            tv.setText(title); // ActionBar title text

            // Set the text color of TextView to black
//            tv.setTextColor(Color.GREEN);
            tv.setTextSize(getResources().getDimension(R.dimen.title_size) / getResources().getDisplayMetrics().density);
            tv.setGravity(Gravity.CENTER);

            // Set the monospace font for TextView text
            // This will change ActionBar title text font
            tv.setTypeface(typeface);

            // Set the ActionBar display option
            ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

            // Finally, set the newly created TextView as ActionBar custom view
            ab.setCustomView(tv);
        }
    }

    /*
            final AsyncTask<Params, Progress, Result>
                execute(Params... params)
                    Executes the task with the specified parameters.
         */
    private class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String... urls) {
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try {
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            } catch (Exception e) { // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WikiReaderActivity.this, WikiListActivity.class);
        intent.putExtra(getResources().getString(R.string.category), category);
        startActivity(intent);
        finish();
    }
}
