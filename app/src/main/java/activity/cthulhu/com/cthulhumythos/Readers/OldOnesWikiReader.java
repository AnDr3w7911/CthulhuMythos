package activity.cthulhu.com.cthulhumythos.Readers;

import android.os.Bundle;

import activity.cthulhu.com.cthulhumythos.R;

/**
 * Created by Andrew on 1/27/2018.
 */

public class OldOnesWikiReader extends WikiReaderActivity {
    String[] oldOnes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oldOnes = getResources().getStringArray(R.array.old_ones);

        setTitleBar(oldOnes[index]);
        wikiDesc.setText(getResources().getTextArray(R.array.oldOnes_descriptions)[index]);
        setOldOnesPic(index);
    }

    void setOldOnesPic(int position) {
        switch (position) {
            case 0:
                imageView.setImageResource(R.drawable.azathoth);
                break;
            case 1:
                imageView.setImageResource(R.drawable.bokrug);
                break;
            case 2:
                imageView.setImageResource(R.drawable.cthulhu);
                break;
            case 3:
                imageView.setImageResource(R.drawable.crom);
                break;
            case 4:
                imageView.setImageResource(R.drawable.dagon);
                break;
            case 5:
                imageView.setImageResource(R.drawable.ghatanothoa);
                break;
            case 6:
                imageView.setImageResource(R.drawable.hastur);
                break;
            case 7:
                imageView.setImageResource(R.drawable.motherhydra);
                break;
            case 8:
                imageView.setImageResource(R.drawable.nug_and_yeb);
                break;
            case 9:
                imageView.setImageResource(R.drawable.nyarlathotep);
                break;
            case 10:
                imageView.setImageResource(R.drawable.shubniggurath);
                break;
            case 11:
                imageView.setImageResource(R.drawable.thog);
                break;
            case 12:
                imageView.setImageResource(R.drawable.umr_attawil);
                break;
            case 13:
                imageView.setImageResource(R.drawable.ygolonac);
                break;
            case 14:
                imageView.setImageResource(R.drawable.yig);
                break;
            case 15:
                imageView.setImageResource(R.drawable.yog_sothoth);
                break;
        }

    }
}
