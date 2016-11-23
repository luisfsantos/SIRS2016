package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.CallsAsyncTask;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.SearchIMDB;

/**
 * Created by Catarina on 15/11/2016.
 */

public class PromptQrScanActivity extends BaseActivity implements CallsAsyncTask {

    private static boolean ASYNC_DONE = false;

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
            new SearchIMDB(this).execute(SlidingTabsAdapter.searchTopics);
        } else {
            Intent intent = new Intent(PromptQrScanActivity.this, NoNetworkConnectionActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.scan_qr_code)
    public void onScanQrCodeClicked(View view) {
        Intent i = new Intent(PromptQrScanActivity.this, QrCodeScanner.class);
        i.putExtra("ASYNC_DONE", ASYNC_DONE);
        startActivity(i);
    }


    @Override
    public boolean providesActivityToolbar() {
        return false;
    }


    @Override
    public void onRequestFinished() {
        ASYNC_DONE = true;
    }
}
