package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.CallsAsyncTask;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.FetchMenuSR;

/**
 * Created by Catarina on 15/11/2016.
 */

public class QrCodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler, CallsAsyncTask {

    private static boolean ASYNC_DONE = false;
    private static final String KEY_QR_CODE = "QR_CODE";
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ASYNC_DONE = getIntent().getExtras().getBoolean("ASYNC_DONE");
        FetchMenuSR.updateDelegate(this);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.d("Scanner", rawResult.getText());
        Log.d("Scanner", rawResult.getBarcodeFormat().toString());

        if (ASYNC_DONE) {
            Intent intent = new Intent(QrCodeScanner.this, MenuListActivity.class);
            //intent.putExtra(KEY_QR_CODE, rawResult.getText());
            //setResult(RESULT_OK, intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(QrCodeScanner.this, LoadingMenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onRequestFinished() {
        ASYNC_DONE = true;
    }
}
