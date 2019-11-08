package activity.cthulhu.com.cthulhumythos.ListActivities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import activity.cthulhu.com.cthulhumythos.About.AboutActivity;
import activity.cthulhu.com.cthulhumythos.R;
import activity.cthulhu.com.cthulhumythos.Util.MyAdapter;
import activity.cthulhu.com.cthulhumythos.Util.RecyclerItemClickListener;

public class MainActivity extends AppCompatActivity {
    public static Typeface typeface;
    private String[] categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/october_crow.ttf");


        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("CTHULHU MYTHOS");
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.colorText));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.colorText));
//        collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER);
        collapsingToolbar.setExpandedTitleGravity(Gravity.CENTER|Gravity.BOTTOM);
        collapsingToolbar.setCollapsedTitleTypeface(typeface);
        collapsingToolbar.setExpandedTitleTypeface(typeface);
//        setActionBar();
        {// Set the font of the buttons
            RecyclerView listView = findViewById(R.id.main_menu_listView);
            listView.setHasFixedSize(true);

            categories = getResources().getStringArray(R.array.main_activity_list);
//            listView.setAdapter(new CustomAdapter(this, R.layout.single_row, categories));
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(new MyAdapter(categories));

            listView.addOnItemTouchListener(new RecyclerItemClickListener(this, listView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = null;
                    String[] categories = getResources().getStringArray(R.array.categories);
                    if (position == 0) {
                        intent = new Intent(MainActivity.this, StoriesActivity.class);

                    } else if (position < categories.length + 1) {
                        intent = new Intent(MainActivity.this, WikiListActivity.class);
                        intent.putExtra(getResources().getString(R.string.category), categories[position - 1]);
                    } else {
                        intent = new Intent(MainActivity.this, AboutActivity.class);
                    }

                    if (intent != null) {
                        startActivity(intent);
                    }
                }

                @Override
                public void onLongItemClick(View view, int position) {

                }
            }));
        }
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
