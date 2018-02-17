package activity.cthulhu.com.cthulhumythos.Readers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.QuoteSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.IOException;
import java.io.InputStream;

import activity.cthulhu.com.cthulhumythos.ListActivities.MainActivity;
import activity.cthulhu.com.cthulhumythos.Util.CustomQuoteSpan;
import activity.cthulhu.com.cthulhumythos.Util.OnSwipeTouchListener;
import activity.cthulhu.com.cthulhumythos.Util.Pagination;
import activity.cthulhu.com.cthulhumythos.R;

public class ReaderActivity extends Activity {
    private String contentString;
    private ViewFlipper flipper;
    private TextView pageNumberView;
    private float initialX;
    private TextView contentTextView;
    private int currentPage;
    String storyName;

    Pagination pagination;
    SharedPreferences sharedPrefs;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        sharedPrefs = getPreferences(MODE_PRIVATE);
        storyName = getIntent().getStringExtra(getResources().getString(R.string.story_name));

        pageNumberView = findViewById(R.id.page_number_view);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        currentPage = sharedPrefs.getInt(storyName, 0);
        InputStream is;
        try {
            is = getAssets().open("stories/" + storyName + ".txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            contentString = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Story not loaded", Toast.LENGTH_SHORT).show();
            return;
        }
//        BufferedReader reader = null;
//        try {
////            Cp1252
//            reader = new BufferedReader(new InputStreamReader(getAssets().open("stories/" + storyName + ".txt"), "Cp1252"));
//            contentString = "";
//            String line;
//            while((line = reader.readLine()) != null){
//                contentString += line;
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if(reader != null){
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        // retreiving the flipper
        flipper = findViewById(R.id.viewFlipper);
        flipper.post(new Runnable() {
            @Override
            public void run() {
                final int screenHeight = flipper.getHeight();
                final int screenWidth = flipper.getWidth();
                {// Create the title page
                    TextView titlePage = new TextView(ReaderActivity.this);
                    titlePage.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    titlePage.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                    titlePage.setMaxHeight(screenHeight);
                    titlePage.setMaxWidth(screenWidth);
                    titlePage.setGravity(Gravity.CENTER);
                    titlePage.setTextSize(48);
                    titlePage.setTypeface(MainActivity.typeface);
                    titlePage.setText(storyName + "\n\nBy H.P. Lovecraft");
                    flipper.addView(titlePage);
                }
                // creating new textviews for every page
                contentTextView = new TextView(ReaderActivity.this);
                contentTextView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                contentTextView.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                contentTextView.setMaxHeight(screenHeight);
                contentTextView.setMaxWidth(screenWidth);
                contentTextView.setTextSize(getResources().getDimension(R.dimen.text_size) / getResources().getDisplayMetrics().density);

                float textSize = contentTextView.getTextSize();
                Paint paint = new Paint();
                paint.setTextSize(textSize);
                flipper.addView(contentTextView);

                {
                    pagination = new Pagination(contentString,
                            flipper.getWidth(),
                            flipper.getHeight(),
                            contentTextView.getPaint(),
                            contentTextView.getLineSpacingMultiplier(),
                            contentTextView.getLineSpacingExtra(),
                            contentTextView.getIncludeFontPadding());


                }
                if (currentPage != 0) {
                    flipper.setDisplayedChild(1);
                    contentTextView.setText(pagination.get(currentPage));
                    pageNumberView.setText((currentPage + 1) + "/" + pagination.size());
                } else {
                    pageNumberView.setText("");
                }
            }
        });
        pageNumberView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Toast.makeText(ReaderActivity.this, "CLICKED", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        flipper.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                if (currentPage == 0) {
                    if (flipper.getDisplayedChild() != 0) {
                        flipper.showPrevious();
                        pageNumberView.setText("");
                    }
                    return;
                }
                --currentPage;
                contentTextView.setText(pagination.get(currentPage));
                pageNumberView.setText((currentPage + 1) + "/" + pagination.size());
//                Toast.makeText(ReaderActivity.this, "right", Toast.LENGTH_SHORT).show();

            }

            public void onSwipeLeft() {
//                Toast.makeText(ReaderActivity.this, "left", Toast.LENGTH_SHORT).show();
                if (flipper.getDisplayedChild() == 0) {
                    flipper.showNext();
                    currentPage = 0;
                } else {
                    if (currentPage == pagination.size() - 1) {
                        return;
                    }
                    currentPage++;
                }
//                Spannable span = new SpannableString(pagination.get(currentPage));
//                replaceQuoteSpans(span);
//                contentTextView.setText(span);
                contentTextView.setText(pagination.get(currentPage));
                pageNumberView.setText((currentPage + 1) + "/" + pagination.size());
            }
        });

    }
    private void replaceQuoteSpans(Spannable spannable) {
        QuoteSpan[] quoteSpans = spannable.getSpans(0, spannable.length(), QuoteSpan.class);
        for (QuoteSpan quoteSpan : quoteSpans) {
            int start = spannable.getSpanStart(quoteSpan);
            int end = spannable.getSpanEnd(quoteSpan);
            int flags = spannable.getSpanFlags(quoteSpan);
            spannable.removeSpan(quoteSpan);
            spannable.setSpan(new CustomQuoteSpan(
                           android.R.attr.editTextColor,
                            Color.TRANSPARENT,
                            15,
                            0),
                    start,
                    end,
                    flags);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(storyName, currentPage);
        editor.commit();
    }
}
