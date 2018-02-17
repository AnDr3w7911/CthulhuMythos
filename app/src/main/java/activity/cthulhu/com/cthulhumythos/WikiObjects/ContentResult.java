package activity.cthulhu.com.cthulhumythos.WikiObjects;

/**
 * Created by Andrew on 1/29/2018.
 */

public class ContentResult {
    public Section[] sections;

    @Override
    public String toString() {
        return sections[0].title;
    }
}
