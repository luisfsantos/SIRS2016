package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.CallsAsyncTask;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.ConfirmPaymentSR;

/**
 * Created by Catarina on 03/12/2016.
 */
public class AfterPaymentActivity extends BaseActivity implements CallsAsyncTask{

    private static final String TAG = "AfterPaymentActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout. ...);
        Intent intent = getIntent();
        int payment_method = intent.getIntExtra(OrderPaymentActivity.INTENT_PAYMENT_METHOD, 0);

        if (payment_method == ChoosePaymentMethodActivity.PAYPAL_CHOSEN) {
            PaymentConfirmation confirm = intent.getParcelableExtra(OrderPaymentActivity.INTENT_CONFIRM);
            new ConfirmPaymentSR(this).execute(payment_method, confirm.toJSONObject());
        } else if ( payment_method == ChoosePaymentMethodActivity.CASH_CHOSEN) {
            String identifier = intent.getStringExtra(OrderPaymentActivity.INTENT_ORDER_ID);
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
        return false;
    }

    @Override
    public void onRequestFinished(Object object) {
        //TODO display hooray screen
    }

    public void onGoToMenuClicked(View view){
        Intent intent = new Intent(AfterPaymentActivity.this, MenuListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
