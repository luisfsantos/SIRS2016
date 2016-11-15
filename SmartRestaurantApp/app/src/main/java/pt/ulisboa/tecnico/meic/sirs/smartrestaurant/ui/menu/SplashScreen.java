package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.RestaurantMenu;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.WebRequest;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.util.LogUtil;

/**
 * Created by Catarina on 15/11/2016.
 */

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new SearchIMDB().execute(SlidingTabsAdapter.searchTopics);
        } else {
            Toast toast = Toast.makeText(this, getText(R.string.mobile_data), Toast.LENGTH_SHORT);
            toast.show();
        }
    }



    private class SearchIMDB extends AsyncTask<String, Void, Map<String, String>> {
        private final String IMDB_BASE = "http://www.omdbapi.com/?s=";

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

            } catch (JSONException e) {
                Toast toast = Toast.makeText(SplashScreen.this, "A problem occurred.", Toast.LENGTH_SHORT);
                toast.show();
            }

            Intent i = new Intent(SplashScreen.this, MenuListActivity.class);
            startActivity(i);
            finish();


        }
    }
}
