package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web;

/**
 * Created by Catarina on 12/11/2016.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.RestaurantMenu;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu.MenuListActivity;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu.SlidingTabsAdapter;

/**
 * Created by Catarina on 12/11/2016.
 */
public class SearchIMDB extends AsyncTask<String, Void, Map<String, String>> {
    private final String IMDB_BASE = "http://www.omdbapi.com/?s=";
    private Context context;


    public SearchIMDB(Context context) {
        this.context = context;
    }



    @Override
    protected Map<String, String> doInBackground(String... search) {
        Map<String, String> searchResults = new HashMap<>();

        for (String s : search) {
            String url = IMDB_BASE + s.replace(" ", "+");
            Log.w("url", url);
            searchResults.put(s, new WebRequest().makeWebServiceCall(url, WebRequest.POSTRequest));
        }
        return searchResults;
    }

    @Override
    protected void onPostExecute(Map<String, String> result) {
        super.onPostExecute(result);


            try {
                for (String key : result.keySet()) {
                    JSONObject searchResult = new JSONObject(result.get(key));
                    JSONArray results = searchResult.getJSONArray("Search");
                    Log.i("results", results.toString());

                    for (int i = 0; i < results.length(); i++) {
                        //FIXME
                        RestaurantMenu.addItem(key,
                                results.getJSONObject(i).getString("Poster"),
                                results.getJSONObject(i).getString("Title"),
                                results.getJSONObject(i).getString("Year"),
                                "hello this is a plot");
                    }
                }

                ((MenuListActivity) context).loadTabs();

            } catch (JSONException e) {
                Toast toast = Toast.makeText(context, "A problem occurred.", Toast.LENGTH_SHORT);
                toast.show();
            }



    }
}