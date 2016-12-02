package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.BuildConfig;

/**
 * Created by Catarina on 30/11/2016.
 */

public class GetOrderInfoSR extends AsyncTask<JSONArray, Void, WebRequest.WebResult> {

    private static final String TAG = "GetOrderInfo";

    private static final String ORDER_BASE = BuildConfig.SERVER_URL
            + BuildConfig.ORDER_DIR
            + BuildConfig.REQUEST_DIR
            + BuildConfig.POST_PROMPT;

    private static CallsAsyncTask activity;
    private ProgressDialog pd;

    public GetOrderInfoSR(CallsAsyncTask delegate) {
        activity = delegate;
        pd = new ProgressDialog((Activity) activity);
    }

    @Override
    protected void onPreExecute() {
        pd.setMessage("Fetching your order from our servers. Please wait.");
        pd.show();
    }

    @Override
    protected WebRequest.WebResult doInBackground(JSONArray... params) {
        HashMap<String, Object> search = new HashMap<>();
        search.put("order_items", params[0]);
        return new WebRequest().makeWebServiceCall(ORDER_BASE, WebRequest.POSTRequest, search);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(WebRequest.WebResult webResult) {
        super.onPostExecute(webResult);
        Log.i(TAG, webResult.result);
        Log.i(TAG, Integer.toString(webResult.code));

        if (webResult.code == HttpURLConnection.HTTP_CREATED)
            try {
                JSONObject jsonResult = new JSONObject(webResult.result);
                JSONObject order = jsonResult.getJSONArray("Order").getJSONObject(0);
                activity.onRequestFinished(order);

            } catch (JSONException e) {
                Toast toast = Toast.makeText((Context) activity, "A problem occurred.", Toast.LENGTH_SHORT);
                toast.show();
            }

        pd.dismiss();

    }
}
