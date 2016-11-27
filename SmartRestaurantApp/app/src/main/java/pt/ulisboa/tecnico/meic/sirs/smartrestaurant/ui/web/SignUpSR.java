package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu.SignUpActivity;

/**
 * Created by Catarina on 17/11/2016.
 */

public class SignUpSR extends AsyncTask<String, Void, WebRequest.WebResult> {

    private final String SIGNUP_BASE = "https://luissantos.me/register/?";
    CallsAsyncTask activity;

    public SignUpSR(CallsAsyncTask delegate) {
        this.activity = delegate;
    }

    /**
     *
     * @param params - email, username, password, first_name, last_name, nif
     * @return
     */
    @Override
    protected WebRequest.WebResult doInBackground(String... params) {

        HashMap<String, String> search = new HashMap<>();
        search.put("email", params[0]);
        search.put("username", params[1]);
        search.put("password", params[2]); //FIXME
        search.put("first_name", params[3]);
        search.put("last_name", params[4]);
        search.put("nif", params[5]);

        return new WebRequest().makeWebServiceCall(SIGNUP_BASE, WebRequest.POSTRequest, search);
    }

    @Override
    protected void onPostExecute(WebRequest.WebResult webResult) {
        Log.i("SIGN_UP:", webResult.result);

        try {
            JSONObject jsonResult = new JSONObject(webResult.result);
            if (webResult.code == HttpURLConnection.HTTP_CREATED) {
                saveUserData((Context) activity,
                        jsonResult.getString("email"),
                        jsonResult.getString("username"),
                        jsonResult.getInt("nif")
                        );
                activity.onRequestFinished();
            } else {
                Iterator<String> keys = jsonResult.keys();
                StringBuilder sb = new StringBuilder();

                while (keys.hasNext()) {
                    String key = keys.next();
                    JSONArray errorMsgs = jsonResult.getJSONArray(key);
                    for (int i = 0; i < errorMsgs.length(); i++) {
                        sb.append(errorMsgs.get(i));
                        sb.append("\n");
                    }
                }
                ((SignUpActivity) activity).updateErrorView(sb.toString());
            }

        } catch (JSONException e) {
            ((SignUpActivity) activity).updateErrorView("An error occurred.");
        }
    }

    private static void saveUserData(Context context, String email, String username, int nif) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.user_info_pref), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(context.getString(R.string.user_info_email), email);
        editor.putString(context.getString(R.string.user_info_username), username);
        editor.putInt(context.getString(R.string.user_info_nif), nif);

        editor.apply();
    }
}
