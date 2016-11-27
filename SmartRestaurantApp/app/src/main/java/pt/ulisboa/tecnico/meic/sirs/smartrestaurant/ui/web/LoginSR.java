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
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu.LoginActivity;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu.SignUpActivity;

/**
 * Created by Catarina on 17/11/2016.
 */

public class LoginSR extends AsyncTask<String, Void, WebRequest.WebResult> {

    private final String LOGIN_BASE = "https://luissantos.me/login/?";
    CallsAsyncTask activity;

    public LoginSR(CallsAsyncTask delegate) {
        this.activity = delegate;
    }

    /**
     *
     * @param params - username, password
     * @return
     */
    @Override
    protected WebRequest.WebResult doInBackground(String... params) {

        HashMap<String, String> search = new HashMap<>();
        search.put("username", params[0]);
        search.put("password", params[1]);

        return new WebRequest().makeWebServiceCall(LOGIN_BASE, WebRequest.POSTRequest, search);
    }

    @Override
    protected void onPostExecute(WebRequest.WebResult webResult) {
        Log.i("LOGIN:", webResult.result);

        try {
            JSONObject jsonResult = new JSONObject(webResult.result);
            if (webResult.code == HttpURLConnection.HTTP_OK) {
                activity.onRequestFinished();
            } else {
                Iterator<String> keys = jsonResult.keys();
                StringBuilder sb = new StringBuilder();

                while (keys.hasNext()) {
                    String key = keys.next();
                    JSONArray errorMsgs = jsonResult.getJSONArray(key);
                    for (int i = 0; i < errorMsgs.length(); i++) {
                        sb.append(errorMsgs.get(i) + "\n");
                    }
                }
                ((LoginActivity) activity).updateErrorView(sb.toString());
            }

        } catch (JSONException e) {
            ((LoginActivity) activity).updateErrorView("An error occurred.");
        }
    }

//    protected static void saveUserData(Context context /* + cookie*/) {
//        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.user_info_pref), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//
////        editor.putString(context.getString(R.string.user_info_email), email);
////        editor.putString(context.getString(R.string.user_info_username), username);
////        editor.putInt(context.getString(R.string.user_info_nif), nif);
//
//        editor.commit();
//    }
}
