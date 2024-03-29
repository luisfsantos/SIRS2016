package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.Order;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.CallsAsyncTask;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.FetchMenuSR;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.SendQRTableSR;

/**
 * Created by Catarina on 15/11/2016.
 */

public class PromptQrScanActivity extends BaseActivity implements CallsAsyncTask {

    private static boolean ASYNC_DONE = false;
    static final int SCAN_QR_REQUEST = 1;
    static final int CAMERA_REQUEST_PERMISSION = 200;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_code);
        downloadMenu();
        ButterKnife.bind(this);
    }

    private void downloadMenu() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new FetchMenuSR(this).execute(SlidingTabsAdapter.mealTypes);
        } else {
            Intent intent = new Intent(PromptQrScanActivity.this, NoNetworkConnectionActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.scan_qr_code)
    public void onScanQrCodeClicked(View view) {
        if (hasPermission(Manifest.permission.CAMERA)) {
            startScan();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_PERMISSION);
        }
    }

    private void startScan() {
        Intent i = new Intent(PromptQrScanActivity.this, QrCodeScanner.class);
        i.putExtra("ASYNC_DONE", ASYNC_DONE);
        startActivityForResult(i, SCAN_QR_REQUEST);
    }

    private boolean hasPermission(String permission) {
        return (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startScan();
                } else {
                    ((TextView) findViewById(R.id.oops_text)).setText(R.string.camera_permission);
                }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FetchMenuSR.updateDelegate(this);
        if (requestCode == SCAN_QR_REQUEST) {
            if (resultCode == RESULT_OK) {
                String qrcode = data.getStringExtra(QrCodeScanner.KEY_QR_CODE);
                ASYNC_DONE = data.getExtras().getBoolean("ASYNC_DONE");
                new SendQRTableSR(this).execute(qrcode);
            }
        }
    }

    public void onQrSendFinished(boolean table_ok, String message) {
        if (table_ok) {
            Order.TABLE_ID = message;
            if (ASYNC_DONE) {
                Intent intent = new Intent(PromptQrScanActivity.this, MenuListActivity.class);
                ;
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(PromptQrScanActivity.this, LoadingMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        } else {
            ((TextView) findViewById(R.id.oops_text)).setText(message);
        }
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }


    @Override
    public void onRequestFinished(Object object) {
        ASYNC_DONE = true;
    }
}
