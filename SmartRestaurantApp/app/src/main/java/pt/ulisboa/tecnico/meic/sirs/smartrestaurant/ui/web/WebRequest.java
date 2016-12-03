package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Catarina on 12/11/2016.
 */
public class WebRequest {
    private static final String TAG = "WebRequest";
    public final static int GETRequest = 1;
    public final static int POSTRequest = 2;
    private static final CookieManager cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);

    public static void clearCookies() {
        cookieManager.getCookieStore().removeAll();
    }

    //Constructor with no parameter
    public WebRequest() {
        CookieHandler.setDefault(cookieManager);
    }

    /**
     * Making web service call
     *
     * @url - url to make web request
     * @requestmethod - http request method
     */
    public WebResult makeWebServiceCall(String url, int requestMethod) {
        return this.makeWebServiceCall(url, requestMethod, null);
    }

    /**
     * Making web service call
     *
     * @url - url to make web request
     * @requestmethod - http request method
     * @params - http request params
     */
    public WebResult makeWebServiceCall(String urladdress, int requestmethod,
                                        HashMap<String, Object> params) {

        URL url;
        String response = "";
        try {
            Log.d(TAG, urladdress);
            url = new URL(urladdress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Referer", urladdress);
            conn.setReadTimeout(15001);
            conn.setConnectTimeout(15001);
            conn.setDoInput(true);

            conn.setRequestProperty("Content-Type", "application/json");
            if (requestmethod == POSTRequest) {
                conn.setRequestMethod("POST");
                for (HttpCookie cookie : cookieManager.getCookieStore().getCookies()) {
                    if (cookie.getName().equals("csrftoken"))
                        conn.setRequestProperty("X-CSRFToken", cookie.getValue());
                }
                conn.setDoOutput(true);
            } else if (requestmethod == GETRequest) {
                conn.setRequestMethod("GET");
            }

            if (requestmethod == POSTRequest && params != null) {
                OutputStream out = conn.getOutputStream();
                JSONObject jsonObject = new JSONObject();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                        jsonObject.put(entry.getKey(),entry.getValue());
                }

                Log.d(TAG, jsonObject.toString());
                out.write(jsonObject.toString().getBytes());
                out.close();
            }
            int reqResponseCode = conn.getResponseCode();
            InputStream inputStream;

            if (reqResponseCode == HttpURLConnection.HTTP_OK ||
                    reqResponseCode == HttpURLConnection.HTTP_CREATED) {
                inputStream = conn.getInputStream();
            } else {
                inputStream = conn.getErrorStream();
            }

            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                response += line;
            }

            return new WebResult(reqResponseCode, response);


        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return new WebResult("Unable to retrieve web page. URL may be invalid.");
        }
    }

    public class WebResult {
        public final int code;
        public final String result;

        WebResult(String result) {
            this.code = 0;
            this.result = result;
        }

        WebResult(int code, String result) {
            this.code = code;
            this.result = result;
        }
    }
}
