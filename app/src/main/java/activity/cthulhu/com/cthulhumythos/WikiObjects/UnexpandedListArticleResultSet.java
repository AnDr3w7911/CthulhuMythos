package activity.cthulhu.com.cthulhumythos.WikiObjects;

import java.io.Serializable;

/**
 * Created by Andrew on 1/29/2018.
 */

public class UnexpandedListArticleResultSet  implements Serializable {
    public UnexpandedArticle[] items;
    public String offset;
    public String basepath;
}
