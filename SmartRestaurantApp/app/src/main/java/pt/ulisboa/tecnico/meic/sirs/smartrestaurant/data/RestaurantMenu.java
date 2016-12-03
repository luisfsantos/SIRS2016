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
     * A map of menu items. Key: item ID; Value: Menu Item.
     */
    public static final Map<String, MenuItem> ITEM_MAP = new HashMap<>();
    /**
     * A map of course menus. Key: course name; Value: Course Menu.
     */
    public static final Map<String, CourseMenu> COURSE_MAP = new HashMap<>();

    public static void clear() {
        ITEM_MAP.clear();
        COURSE_MAP.clear();
    }

    public static void addItem(String course, String id, String name, float price, String description, int calories, String imageURL, String[] ingredients) {
        addItem(course, new MenuItem(id, name, price, description, calories, imageURL, ingredients));
    }
    private static void addItem(String courseName, MenuItem item) {
        CourseMenu courseMenu = COURSE_MAP.get(courseName);
        if (courseMenu == null) {
            courseMenu = new CourseMenu();
            COURSE_MAP.put(courseName, courseMenu);
        }
        if (!ITEM_MAP.containsKey(item.id)) {
            courseMenu.addItem(item);
            ITEM_MAP.put(item.id, item);
        }
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
        public final String imageURL;
        public final String name;
        public final float price;
        public final String description;
        public final int calories;
        public final String[] ingredients;


        public MenuItem(String id, String name, float price, String description, int calories, String imageURL, String[] ingredients) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.description = description;
            this.calories = calories;
            this.imageURL = imageURL;
            this.ingredients = ingredients;
        }
    }
}
