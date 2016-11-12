package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Just dummy content. Nothing special.
 *
 * Created by Andreas Schrade on 14.12.2015.
 */
public class RestaurantMenu {

    // FIXME: make id come from web server instead of having a counter
    private static int id = 0;

    /**
     * A map of menu items. Key: sample ID; Value: Item.
     */
    public static final Map<String, MenuItem> ITEM_MAP = new HashMap<>();
    /**
     * A map of course menus. Key: course name; Value: Course Menu.
     */
    public static final Map<String, CourseMenu> COURSE_MAP = new HashMap<>();


    public static void addItem(String course, String posterURL, String title, String year, String plot) {
        addItem(course, new MenuItem(Integer.toString(++id), posterURL, title, year, plot));
    }
    private static void addItem(String courseName, MenuItem item) {
        CourseMenu courseMenu = COURSE_MAP.get(courseName);
        if (courseMenu == null) {
            courseMenu = new CourseMenu();
            COURSE_MAP.put(courseName, courseMenu);
        }
        courseMenu.addItem(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class CourseMenu {
        public List<MenuItem> ITEMS;
        public Map<String, MenuItem> ITEMS_MAP;

        public CourseMenu() {
            ITEMS = new ArrayList<>();
            ITEMS_MAP = new HashMap<>();
        }

        public void addItem(MenuItem item) {
            ITEMS.add(item);
            ITEMS_MAP.put(item.id, item);
        }


    }

    public static class MenuItem {
        public final String id;
        public final String posterURL;
        public final String title;
        public final String year;
        public final String plot;

        public MenuItem(String id, String posterURL, String title, String year, String plot) {
            this.id = id;
            this.posterURL = posterURL;
            this.title = title;
            this.year = year;
            this.plot = plot;
        }
    }
}
