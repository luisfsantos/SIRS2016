package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.RestaurantMenu;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.WebRequest;

/**
 * Created by Catarina on 15/11/2016.
 */

public class QrScanScreenActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_code);
        downloadMenu();
        ButterKnife.bind(this);
    }

    private void downloadMenu() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new SearchIMDB().execute(SlidingTabsAdapter.searchTopics);
        } else {
            Intent intent = new Intent(QrScanScreenActivity.this, NoNetworkConnectionActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.scan_qr_code)
    public void onScanQrCodeClicked(View view) {
        Intent i = new Intent(QrScanScreenActivity.this, QrCodeScanner.class);
        startActivity(i);
        finish();
    }


    @Override
    public boolean providesActivityToolbar() {
        return false;
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
                Toast toast = Toast.makeText(QrScanScreenActivity.this, "A problem occurred.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
