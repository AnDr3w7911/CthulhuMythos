package activity.cthulhu.com.cthulhumythos.Readers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import activity.cthulhu.com.cthulhumythos.R;

public class WebReaderActivity extends Activity {
    private int currentPage;
    String storyName;
    String contentString;
    SharedPreferences sharedPrefs;
    int savedScrollY;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_reader);
        sharedPrefs = getPreferences(MODE_PRIVATE);
        storyName = getIntent().getStringExtra(getResources().getString(R.string.story_name));
        savedScrollY = sharedPrefs.getInt(storyName, 0);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        InputStream is;

        try {
            is = getAssets().open("stories/" + storyName.replaceAll("[\\.]", "") + ".txt");
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
        String text = "<html><head>"
                + "<style type=\"text/css\">"
                + "@font-face { font-family: 'MyFont'; src: url('file:///android_asset/fonts/october_crow.ttf');}"
                + "header{font-family: 'MyFont';font-size: large;text-align: center;!important;}"
                + "h1, h3{font-weight:normal;}"
                + "body{color: #FFFFFF;}"
                + "</style></head>"
                + "<header><h1>"
                + storyName
                + "</h1><h3>By H.P. Lovecraft</h3></header>"
                + "<body>"
                + contentString
                + "</body></html>";
        webView = findViewById(R.id.webview);
//        webView.loadData(text, "text/html; charset=utf-8", "UTF-8");
        webView.loadDataWithBaseURL(null, text, "text/html", "utf-8", "about:blank");
        webView.setBackgroundColor(android.R.attr.editTextBackground);
        WebSettings settings = webView.getSettings();
        settings.setDefaultFontSize(20);
        webView.setScrollY(savedScrollY);
//        settings.setTextZoom(125);
//        final int h = ((height % 20)+10);
//        Log.d("WebReaderActivity", "H: " + h);
//        findViewById(R.id.webReaderLayout).setPadding(0,h/2,0, h/2);
//
//
//        webView.setOnTouchListener(new OnSwipeTouchListener(this) {
//            public void onSwipeRight() {
//
////                Toast.makeText(WebReaderActivity.this, "right", Toast.LENGTH_SHORT).show();
//                webView.scrollTo(0, webView.getScrollY() - (webView.getHeight()-h/2));
//            }
//
//            public void onSwipeLeft() {
////                Toast.makeText(WebReaderActivity.this, "left", Toast.LENGTH_SHORT).show();
//                webView.scrollTo(0, webView.getScrollY() + (webView.getHeight()-h/2));
//            }
//        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (webView != null) {
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putInt(storyName, webView.getScrollY());
            editor.commit();
        }
    }
}
