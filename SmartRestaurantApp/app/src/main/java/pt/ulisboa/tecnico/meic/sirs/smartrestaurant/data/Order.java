package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Catarina on 12/11/2016.
 */

public class Order {

    /**
     * Stores the IDs of the MenuItems added to order
     */
    public static List<String> ITEMS = new ArrayList<>();

    public static void addItem(RestaurantMenu.MenuItem item) {
        if (!ITEMS.contains(item.id))
            ITEMS.add(item.id);
    }

    public static void deleteItem(int position) {
        ITEMS.remove(position);
    }
}
