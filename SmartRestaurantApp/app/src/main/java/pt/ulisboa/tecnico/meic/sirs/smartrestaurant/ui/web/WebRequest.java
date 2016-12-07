package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;

/**
 * Created by Catarina on 12/11/2016.
 */
public class WebRequest {
    private static final String TAG = "WebRequest";
    final static int GETRequest = 1;
    final static int POSTRequest = 2;
    private final static int TIMEOUT = 45;
    private static final CookieManager cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
    private static SSLContext sslContext = null;

    public static void clearCookies() {
        cookieManager.getCookieStore().removeAll();
    }

    public WebRequest(Context context) {
        CookieHandler.setDefault(cookieManager);
        if (sslContext == null) {
            try {
                generateSSLContext(context);
            } catch (CertificateException | KeyManagementException
                    | NoSuchAlgorithmException | KeyStoreException
                    | IOException e) {
                Log.e(TAG, "Unable to initialize SSL Context: " + e.getMessage());
            }
        }
    }

    private void generateSSLContext(Context context) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        // Load CAs from an InputStream
        // (could be from a resource or ByteArrayInputStream or ...)
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        InputStream caInput = new BufferedInputStream(context.getResources().openRawResource(R.raw.cert));
        Certificate ca;
        try {
            ca = cf.generateCertificate(caInput);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
        } finally {
            caInput.close();
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Create an SSLContext that uses our TrustManager
        sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, tmf.getTrustManagers(), null);
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
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(sslContext.getSocketFactory());

            conn.setRequestProperty("Referer", urladdress);
            conn.setReadTimeout(TIMEOUT * 1000);
            conn.setConnectTimeout(TIMEOUT * 1000);
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
                    jsonObject.put(entry.getKey(), entry.getValue());
                }

                Log.d(TAG, jsonObject.toString());
                out.write(jsonObject.toString().getBytes());
                out.close();
            }
            int reqResponseCode = conn.getResponseCode();
            InputStream inputStream;

            if (reqResponseCode == HttpURLConnection.HTTP_OK ||
                    reqResponseCode == HttpURLConnection.HTTP_CREATED ||
                    reqResponseCode == HttpURLConnection.HTTP_ACCEPTED) {
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

    class WebResult {
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
