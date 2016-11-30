package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.BuildConfig;

/**
 * Created by Catarina on 30/11/2016.
 */

public class GetOrderInfoSR extends AsyncTask<String, Void, WebRequest.WebResult> {

    private static final String TEST_BASE = BuildConfig.SERVER_URL + "test";

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
    protected WebRequest.WebResult doInBackground(String... search) {
        return new WebRequest().makeWebServiceCall(TEST_BASE, WebRequest.GETRequest);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(WebRequest.WebResult webResult) {
        super.onPostExecute(webResult);
        Log.i("TEST", webResult.result);
        Log.i("TEST", Integer.toString(webResult.code));

        activity.onRequestFinished();
        pd.dismiss();

    }
}
