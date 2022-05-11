package activity.cthulhu.com.cthulhumythos.Util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum Story implements Comparable<Story> {
    SHADOW_OUT_OF_TIME("The Shadow Out of Time", 1936, 1934);
    String title;
    int publicationYear;
    int year;

    private Story(String friendlyName, int publicationYear, int year) {
        this.title = friendlyName;
        this.publicationYear = publicationYear;
        this.year = year;
    }

    public static List<Story> sortByPublication(List<Story> stories) {
        Collections.sort(stories, new Comparator<Story>() {
            @Override
            public int compare(Story story1, Story story2) {
                return story1.publicationYear - story2.publicationYear;
            }
        });
        return stories;
    }

    public static List<Story> sortByYear(List<Story> stories) {
        Collections.sort(stories, new Comparator<Story>() {
            @Override
            public int compare(Story story1, Story story2) {
                return story1.year - story2.year;
            }
        });
        return stories;
    }

}
