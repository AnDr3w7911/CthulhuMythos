package activity.cthulhu.com.cthulhumythos.About;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import activity.cthulhu.com.cthulhumythos.ListActivities.MainActivity;
import activity.cthulhu.com.cthulhumythos.R;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setActionBar();
        ((Button)findViewById(R.id.donateButton)).setTypeface(MainActivity.typeface);
//        WebView webView = findViewById(R.id.aboutWebView);
//        webView.setBackgroundColor(android.R.attr.editTextBackground);
//        WebSettings settings = webView.getSettings();
//        settings.setDefaultFontSize(20);
//        String text = "<html><head>"
//                + "<style type=\"text/css\">"
//                + "@font-face { font-family: 'MyFont'; src: url('file:///android_asset/fonts/october_crow.ttf');}"
//                + "header{font-family: 'MyFont';font-size: large;text-align: center;!important;}"
//                + "h1, h3{font-weight:normal;}"
//                + "body{color: #FFFFFF;}"
//                + "</style></head>"
//                + "<body>"
//                + getResources().getString(R.string.aboutString)
//                + "</body></html>";
//        webView.loadData(text, "text/html; charset=utf-8", "UTF-8");
    }

    private void setActionBar() {
        // Set the ActionBar's font and text color
        // Get the ActionBar
        ActionBar ab = getActionBar();

        // Create a TextView programmatically.
        TextView tv = new TextView(this);

        // Create a LayoutParams for TextView
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView

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
        tv.setTypeface(MainActivity.typeface);

        // Set the ActionBar display option
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        // Finally, set the newly created TextView as ActionBar custom view
        ab.setCustomView(tv);
    }

    public void donate(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://paypal.me/CthulhuMythos")));
    }
}
