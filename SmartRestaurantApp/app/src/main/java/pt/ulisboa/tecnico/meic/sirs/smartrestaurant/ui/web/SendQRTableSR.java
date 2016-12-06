package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.BuildConfig;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.Order;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu.PromptQrScanActivity;

/**
 * Created by Catarina on 05/12/2016.
 */

public class SendQRTableSR extends AsyncTask<String, Void, WebRequest.WebResult> {

    private final String QR_BASE = BuildConfig.SERVER_URL + BuildConfig.TABLE_VERIFY_DIR + BuildConfig.POST_PROMPT;
    private CallsAsyncTask activity;

    public SendQRTableSR(CallsAsyncTask delegate) {
        this.activity = delegate;
    }

    /**
     * @param params - qrcode scanned
     * @return
     */
    @Override
    protected WebRequest.WebResult doInBackground(String... params) {

        HashMap<String, Object> search = new HashMap<>();
        search.put("table_id", params[0]);

        return new WebRequest().makeWebServiceCall(QR_BASE, WebRequest.POSTRequest, search);
    }

    @Override
    protected void onPostExecute(WebRequest.WebResult webResult) {
        Log.i("SendQR", webResult.result);
        boolean table_ok = webResult.code == HttpURLConnection.HTTP_OK;

        try {
            JSONObject jsonResult = new JSONObject(webResult.result);
            String res;
            if (table_ok) {
                res = jsonResult.getJSONArray("Table").getJSONObject(0).getString("table_id");
            } else {
                res = jsonResult.getJSONArray("Table").getString(0);
            }

            ((PromptQrScanActivity) activity).onQrSendFinished(table_ok, res);

        } catch (JSONException e) {
            Log.e("SendQR", "Error parsing json");
        }
    }
}
