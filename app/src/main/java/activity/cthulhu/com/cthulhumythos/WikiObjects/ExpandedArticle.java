package activity.cthulhu.com.cthulhumythos.WikiObjects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrew on 1/30/2018.
 */

public class ExpandedArticle {
    public OriginalDimension original_dimensions;
    public String url;
    public int ns;
    @SerializedName("abstract")
    public String abstractText;
    public String thumbnail;
    public Revision revision;
    public int id;
    public String title;
    public String type;
    public int comments;
}
