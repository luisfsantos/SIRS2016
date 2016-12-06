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

public class CancelOrderSR extends AsyncTask<Object, Void, WebRequest.WebResult> {

    private static final String TAG = "CancelOrder";

    private static final String ORDER_BASE = BuildConfig.SERVER_URL
            + BuildConfig.ORDER_DIR
            + BuildConfig.CANCEL_DIR
            + BuildConfig.POST_PROMPT;

    private static CallsAsyncTask activity;

    public CancelOrderSR(CallsAsyncTask delegate) {
        activity = delegate;
    }


    @Override
    protected WebRequest.WebResult doInBackground(Object... params) {
        HashMap<String, Object> search = new HashMap<>();
        search.put("identifier", params[0]);
        return new WebRequest().makeWebServiceCall(ORDER_BASE, WebRequest.POSTRequest, search);
    }

    @Override
    protected void onPostExecute(WebRequest.WebResult webResult) {
        super.onPostExecute(webResult);
        Log.i(TAG, webResult.result);
        Log.i(TAG, Integer.toString(webResult.code));
    }
}
