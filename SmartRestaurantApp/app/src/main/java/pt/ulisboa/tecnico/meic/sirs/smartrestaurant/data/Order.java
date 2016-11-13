package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Catarina on 12/11/2016.
 */
public class Order {

    /**
     * Stores the IDs of the MenuItems added to order
     */
    public static List<String> ITEMS = new ArrayList<>();
    /**
     * Stores quantity for each MenuItem added to order.
     * Key: Item ID; Value: quantity
     */
    private static Map<String, Integer> QUANTITY = new HashMap<>();

    public static void addItem(RestaurantMenu.MenuItem item) {
        if (!ITEMS.contains(item.id)) {
            ITEMS.add(item.id);
            QUANTITY.put(item.id, 1);
        }
    }

    public static void increaseItemQuantity(int position) {
        String id = ITEMS.get(position);
        QUANTITY.put(id, QUANTITY.get(id) + 1);
    }

    public static void decreaseItemQuantity(int position) {
        String id = ITEMS.get(position);
        if (QUANTITY.get(id) > 1)
            QUANTITY.put(id, QUANTITY.get(id) - 1);
    }

    public static int getItemQuantity(int position) {
        return QUANTITY.get(ITEMS.get(position));
    }

    public static void deleteItem(int position) {
        QUANTITY.remove(ITEMS.get(position));
        ITEMS.remove(position);
    }
}
