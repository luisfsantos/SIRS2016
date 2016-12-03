package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.BuildConfig;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu.ChoosePaymentMethodActivity;

/**
 * Created by Catarina on 03/12/2016.
 */
public class ConfirmPaymentSR extends AsyncTask<Object, Void, WebRequest.WebResult> {

    private static final String TAG = "ConfirmPayment";

    private static final String CONFIRM_BASE = BuildConfig.SERVER_URL
            + BuildConfig.ORDER_DIR; //FIXME

    private static CallsAsyncTask activity;
    private ProgressDialog pd;

    public ConfirmPaymentSR(CallsAsyncTask delegate) {
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
        String url = CONFIRM_BASE;

        int payment_method = (int) params[0];
        switch (payment_method) {
            case ChoosePaymentMethodActivity.CASH_CHOSEN:
                url += "cash"; //FIXME buildconfig
                search.put("identifier", params[1]);
                break;
            case ChoosePaymentMethodActivity.PAYPAL_CHOSEN:
                url += "paypal";
                search.put("paypal_confirm", params[1]);
                break;
        }
        return new WebRequest().makeWebServiceCall(url, WebRequest.POSTRequest, search);
    }

    @Override
    protected void onPostExecute(WebRequest.WebResult webResult) {
        super.onPostExecute(webResult);
        Log.i(TAG, webResult.result);
        Log.i(TAG, Integer.toString(webResult.code));

        activity.onRequestFinished(null);
        pd.dismiss();
    }
}
