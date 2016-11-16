package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.zxing.Result;

import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.util.LogUtil;

/**
 * Created by Catarina on 15/11/2016.
 */

public class QrCodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final String KEY_QR_CODE = "QR_CODE";
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        Intent intent = new Intent(QrCodeScanner.this, MenuListActivity.class);
        intent.putExtra(KEY_QR_CODE, rawResult.getText());
        setResult(RESULT_OK, intent);
        startActivity(intent);
        finish();
    }
}
