package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web;

import android.os.AsyncTask;
import android.util.Log;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.BuildConfig;

/**
 * Created by Catarina on 26/11/2016.
 */

public class LogoutSR extends AsyncTask<String, Void, WebRequest.WebResult> {

    private final String LOGOUT_BASE = BuildConfig.SERVER_URL + BuildConfig.LOGOUT_DIR;
    private CallsAsyncTask activity;

    public LogoutSR(CallsAsyncTask delegate) {
        this.activity = delegate;
    }

    @Override
    protected WebRequest.WebResult doInBackground(String... params) {

        return new WebRequest().makeWebServiceCall(LOGOUT_BASE, WebRequest.GETRequest);
    }

    @Override
    protected void onPostExecute(WebRequest.WebResult webResult) {
        Log.i("LOGOUT:", webResult.result);
        activity.onRequestFinished();
    }
}
