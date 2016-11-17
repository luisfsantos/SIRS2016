package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.RestaurantMenu;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.WebRequest;

/**
 * Created by Catarina on 17/11/2016.
 */

public class SearchIMDB extends AsyncTask<String, Void, Map<String, String>> {

    public interface AsyncResponse {
        void downloadFinished();
    }

    private final String IMDB_BASE = "http://www.omdbapi.com/?s=";

    public static AsyncResponse activity = null;

    public SearchIMDB(AsyncResponse delegate) {
        this.activity = delegate;
    }
    
    protected static void updateDelegate(AsyncResponse delegate) {
        activity = delegate;
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

            activity.downloadFinished();
//                Intent intent = new Intent(PromptQrScanActivity.this, MenuListActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                getApplicationContext().startActivity(intent);


        } catch (JSONException e) {
            Toast toast = Toast.makeText((Context) activity, "A problem occurred.", Toast.LENGTH_SHORT);
            toast.show();

        }
    }
}