package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.paypal.android.sdk.payments.PaymentConfirmation;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.CallsAsyncTask;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.ConfirmPaymentSR;

/**
 * Created by Catarina on 03/12/2016.
 */
public class AfterPaymentActivity extends BaseActivity implements CallsAsyncTask {

    private static final String TAG = "AfterPaymentActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int payment_method = intent.getIntExtra(OrderPaymentActivity.INTENT_PAYMENT_METHOD, 0);
        String identifier = intent.getStringExtra(OrderPaymentActivity.INTENT_ORDER_ID);

        if (payment_method == ChoosePaymentMethodActivity.PAYPAL_CHOSEN) {
            PaymentConfirmation confirm = intent.getParcelableExtra(OrderPaymentActivity.INTENT_CONFIRM);
            new ConfirmPaymentSR(this).execute(payment_method, identifier, confirm.toJSONObject());
        } else if (payment_method == ChoosePaymentMethodActivity.CASH_CHOSEN) {
            new ConfirmPaymentSR(this).execute(payment_method, identifier);
        }
        /**
         * send 'confirm' (and possibly confirm.getPayment() to your server for verification
         * or consent completion.
         * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
         * for more details.
         *
         * For sample mobile backend interactions, see
         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
         */
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    @Override
    public void onRequestFinished(Object object) {

        boolean payment_ok = (boolean) object;
        String title;
        if (payment_ok) {
            setContentView(R.layout.activity_after_payment_ok);
            title = "Order Registered";
        } else {
            setContentView(R.layout.activity_after_payment_fraud);
            title = "Order Cancelled";
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);
        getSupportActionBar().setTitle(title);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.go_to_menu)
    public void onGoToMenuClicked(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AfterPaymentActivity.this, MenuListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
