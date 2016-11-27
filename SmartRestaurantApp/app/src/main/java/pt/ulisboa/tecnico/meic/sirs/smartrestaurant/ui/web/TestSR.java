package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Catarina on 26/11/2016.
 */

public class TestSR extends AsyncTask<String, Void, WebRequest.WebResult> {

    private final String TEST_BASE = "https://luissantos.me/test";
    CallsAsyncTask activity;

    public TestSR(CallsAsyncTask delegate) {
        this.activity = delegate;
    }

    @Override
    protected WebRequest.WebResult doInBackground(String... params) {
        return new WebRequest().makeWebServiceCall(TEST_BASE, WebRequest.GETRequest);
    }

    @Override
    protected void onPostExecute(WebRequest.WebResult webResult) {
        Log.i("TEST", webResult.result);
        Log.i("TEST", Integer.toString(webResult.code));
    }
}
