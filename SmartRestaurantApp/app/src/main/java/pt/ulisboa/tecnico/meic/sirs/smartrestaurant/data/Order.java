package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Catarina on 12/11/2016.
 */
public class Order {

    public static String TABLE_ID;

    /**
     * Stores the IDs of the MenuItems added to order
     */
    public static List<String> ITEMS = new ArrayList<>();
    /**
     * Stores quantity for each MenuItem added to order.
     * Key: Item ID; Value: quantity
     */

    public Order() {}

    private static Map<String, Integer> QUANTITY = new HashMap<>();

    public static void clear() {
        ITEMS.clear();
        QUANTITY.clear();
    }

    public static boolean isEmpty() {
        return ITEMS.isEmpty();
    }

    public static boolean addItem(RestaurantMenu.MenuItem item) {
        if (!ITEMS.contains(item.id)) {
            ITEMS.add(item.id);
            QUANTITY.put(item.id, 1);
            return true;
        }
        return false;
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

    public static float getTotalPrice() {
        float total = 0;
        for (String id : ITEMS) {
            total += QUANTITY.get(id) * RestaurantMenu.ITEM_MAP.get(id).price;
        }
        return total;
    }

    public static JSONArray toJSONArray() {
        JSONArray json = new JSONArray();
        JSONObject item;
        try {
            for (String id : ITEMS) {
                item = new JSONObject();
                item.put("menu_item_id", Integer.parseInt(id));
                item.put("quantity", QUANTITY.get(id));
                json.put(item);
            }
        } catch (JSONException e) {
            //FIXME idk?
        }
        return json;
    }
}
