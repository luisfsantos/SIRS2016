package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.BuildConfig;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.RestaurantMenu;

public class FetchMenuSR extends AsyncTask<String, Void, Map<String, String>> {

    private final String MENU_BASE = BuildConfig.SERVER_URL + BuildConfig.MENU_DIR;


    private static CallsAsyncTask activity = null;

    public FetchMenuSR(CallsAsyncTask delegate) {
        activity = delegate;
    }
    
    public static void updateDelegate(CallsAsyncTask delegate) {
        activity = delegate;
    }

    @Override
    protected Map<String, String> doInBackground(String... search) {
        Map<String, String> searchResults = new HashMap<>();

        for (String s : search) {
            String url = MENU_BASE + s.replace(" ", "+");
            Log.i("url", url);
            searchResults.put(s, new WebRequest().makeWebServiceCall(url, WebRequest.GETRequest).result);
        }
        return searchResults;
    }

    @Override
    protected void onPostExecute(Map<String, String> result) {
        super.onPostExecute(result);

        try {
            for (String key : result.keySet()) {
                JSONArray results = new JSONArray(result.get(key));
                Log.i("results", results.toString());

                for (int i = 0; i < results.length(); i++) {
                    JSONArray jsonIngredients = results.getJSONObject(i).getJSONArray("ingredients");
                    String [] ingredients = new String[jsonIngredients.length()];
                    for (int j = 0; j < jsonIngredients.length(); j++) {
                        ingredients[j] = jsonIngredients.getJSONObject(j).getString("name");
                    }

                    //FIXME ingredients
                    RestaurantMenu.addItem(key,
                            results.getJSONObject(i).getString("id"),
                            results.getJSONObject(i).getString("name"),
                            Float.parseFloat(results.getJSONObject(i).getString("price")),
                            results.getJSONObject(i).getString("description"),
                            results.getJSONObject(i).getInt("calories"),
                            results.getJSONObject(i).getString("image_url"),
                            ingredients);

                }
            }
            activity.onRequestFinished();

        } catch (JSONException e) {
            Toast toast = Toast.makeText((Context) activity, "A problem occurred.", Toast.LENGTH_SHORT);
            toast.show();

        }
    }
}