package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.util.HashMap;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.BuildConfig;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu.ChoosePaymentMethodActivity;

/**
 * Created by Catarina on 03/12/2016.
 */
public class ConfirmPayPalPaymentSR extends AsyncTask<Object, Void, WebRequest.WebResult> {

    private static final String TAG = "ConfirmPayment";

    private static final String CONFIRM_BASE = BuildConfig.SERVER_URL
            + BuildConfig.PAYMENTS_DIR
            + BuildConfig.PAY_DIR
            + BuildConfig.PAYPAL_DIR
            + BuildConfig.POST_PROMPT;

    private static CallsAsyncTask activity;
    private ProgressDialog pd;

    public ConfirmPayPalPaymentSR(CallsAsyncTask delegate) {
        activity = delegate;
        pd = new ProgressDialog((Activity) activity);
    }

    @Override
    protected void onPreExecute() {
        pd.setMessage("Sending confirmation to our servers. Please wait.");
        pd.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    @Override
    protected WebRequest.WebResult doInBackground(Object... params) {
        HashMap<String, Object> search = new HashMap<>();

        search.put("identifier", params[0]);
        search.put("paypal_confirm", params[1]);

        return new WebRequest().makeWebServiceCall(CONFIRM_BASE, WebRequest.POSTRequest, search);
    }

    @Override
    protected void onPostExecute(WebRequest.WebResult webResult) {
        super.onPostExecute(webResult);
        Log.i(TAG, webResult.result);
        Log.i(TAG, Integer.toString(webResult.code));

        activity.onRequestFinished(webResult.code == HttpURLConnection.HTTP_OK);
        pd.dismiss();
    }
}