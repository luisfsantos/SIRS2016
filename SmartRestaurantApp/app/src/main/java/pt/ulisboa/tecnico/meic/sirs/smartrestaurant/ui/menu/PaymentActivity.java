package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;

/**
 * Created by Catarina on 14/11/2016.
 */

public class PaymentActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.scan_qr_code)
    public void onScanQrCodeClicked(View view) {
        Toast.makeText(this, "Clicky click", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean providesActivityToolbar() { return true; }
}
