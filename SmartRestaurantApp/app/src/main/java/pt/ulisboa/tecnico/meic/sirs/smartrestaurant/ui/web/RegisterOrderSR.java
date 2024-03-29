package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.BuildConfig;

/**
 * Created by Catarina on 30/11/2016.
 */

public class RegisterOrderSR extends AsyncTask<Object, Void, WebRequest.WebResult> {

    private static final String TAG = "RegisterOrder";

    private static final String REGISTER_BASE = BuildConfig.SERVER_URL
            + BuildConfig.ORDER_DIR
            + BuildConfig.REQUEST_DIR
            + BuildConfig.POST_PROMPT;

    private static CallsAsyncTask activity;
    private ProgressDialog pd;

    public RegisterOrderSR(CallsAsyncTask delegate) {
        activity = delegate;
        pd = new ProgressDialog((Activity) activity);
    }

    @Override
    protected void onPreExecute() {
        pd.setMessage("Fetching your order from our servers. Please wait.");
        pd.show();
    }

    @Override
    protected WebRequest.WebResult doInBackground(Object... params) {
        HashMap<String, Object> search = new HashMap<>();
        search.put("table_id", params[0]);
        search.put("order_items", params[1]);
        search.put("payment_method", params[2]);
        return new WebRequest((Context)activity).makeWebServiceCall(REGISTER_BASE, WebRequest.POSTRequest, search);
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
