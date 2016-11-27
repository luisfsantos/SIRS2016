package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Catarina on 12/11/2016.
 */
public class WebRequest {
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
                                        HashMap<String, String> params) {

        //FIXME idea: change this to return both the responseCode (200, 201, 400, ...) and the String
        URL url;
        String response = "";
        try {
            url = new URL(urladdress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15001);
            conn.setConnectTimeout(15001);
            conn.setDoInput(true);
            if (requestmethod == POSTRequest) {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            } else if (requestmethod == GETRequest) {
                conn.setRequestMethod("GET");
            }

            if (params != null) {
                OutputStream ostream = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(ostream, "UTF-8"));
                StringBuilder requestresult = new StringBuilder();
                boolean first = true;
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (first)
                        first = false;
                    else
                        requestresult.append("&");
                    requestresult.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    requestresult.append("=");
                    requestresult.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                }
                writer.write(requestresult.toString());

                writer.flush();
                writer.close();
                ostream.close();
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
            Log.d("WebRequest", e.getMessage());
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
