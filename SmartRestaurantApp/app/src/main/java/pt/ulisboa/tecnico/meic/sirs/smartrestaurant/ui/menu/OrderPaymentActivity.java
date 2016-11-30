package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.Order;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.RestaurantMenu;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;

/**
 * Created by Catarina on 14/11/2016.
 */

public class OrderPaymentActivity extends BaseActivity {

    private static final String TAG = "OrderPaymentActivity";
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "credentials from developer.paypal.com";

    private static final int REQUEST_CODE_PAYMENT = 1;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    private int payment_chosen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);
        fillOrderCard();
        payment_chosen = getIntent().getExtras().getInt(ChoosePaymentMethodActivity.PAYMENT_METHOD);
        if (payment_chosen == ChoosePaymentMethodActivity.PAYPAL_CHOSEN) {
            Intent intent = new Intent(this, PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            startService(intent);
        }
        updateButtonText();
        ButterKnife.bind(this);
    }

    private void updateButtonText() {
        switch (payment_chosen) {
            case ChoosePaymentMethodActivity.PAYPAL_CHOSEN:
                ((AppCompatButton) findViewById(R.id.submit_order)).setText(getString(R.string.pay_with_paypal));
                break;
            case ChoosePaymentMethodActivity.CASH_CHOSEN:
                ((AppCompatButton) findViewById(R.id.submit_order)).setText(getString(R.string.pay_with_cash));
                break;
        }
    }

    @OnClick(R.id.submit_order)
    public void onSubmitOrderClicked(View view) {
        Toast.makeText(this, "Order submitted", Toast.LENGTH_SHORT).show();
        switch (payment_chosen) {
            case ChoosePaymentMethodActivity.PAYPAL_CHOSEN:
                handlePayPalPayment();
                break;
            case ChoosePaymentMethodActivity.CASH_CHOSEN:
                //TODO launch confirm activity?
                break;
        }
    }

    @Override
    public boolean providesActivityToolbar() { return true; }

    public void fillOrderCard() {
         LinearLayout orderList = ((LinearLayout) findViewById(R.id.order_payment_list));
        for (int pos = 0; pos < Order.ITEMS.size(); pos++) {
            RestaurantMenu.MenuItem item = RestaurantMenu.ITEM_MAP.get(Order.ITEMS.get(pos));
            View view = getLayoutInflater().inflate(R.layout.list_item_order_payment, null);
            ((TextView) view.findViewById(R.id.menu_item_name)).setText(item.name);
            ((TextView) view.findViewById(R.id.menu_item_price)).setText(Order.getItemQuantity(pos) + " x " + item.price + getString(R.string.currency));
            orderList.addView(view);
        }
        ((TextView) findViewById(R.id.order_price)).setText(String.format("%.2f", Order.getTotalPrice()) + getString(R.string.currency));
    }

    /**
    * PAYPAL STUFF BELOW
    * */
    private void handlePayPalPayment() {

        PayPalPayment stuffToBuy = getStuffToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(OrderPaymentActivity.this, PaymentActivity.class);
        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, stuffToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    /*
     * This method shows use of optional payment details and item list.
     */
    private PayPalPayment getStuffToBuy(String paymentIntent) {
        //--- include an item list, payment amount details
        PayPalItem[] items =
                {
                        new PayPalItem("sample item #1", 2, new BigDecimal("87.50"), "USD",
                                "sku-12345678"),
                        new PayPalItem("free sample item #2", 1, new BigDecimal("0.00"),
                                "USD", "sku-zero-price"),
                        new PayPalItem("sample item #3 with a longer name", 6, new BigDecimal("37.99"),
                                "USD", "sku-33333")
                };
        BigDecimal subtotal = PayPalItem.getItemTotal(items);
        BigDecimal shipping = new BigDecimal("7.21");
        BigDecimal tax = new BigDecimal("4.67");
        PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(shipping, subtotal, tax);
        BigDecimal amount = subtotal.add(shipping).add(tax);
        PayPalPayment payment = new PayPalPayment(amount, "USD", "sample item", paymentIntent);
        payment.items(items).paymentDetails(paymentDetails);

        //--- set other optional fields like invoice_number, custom field, and soft_descriptor
        payment.custom("This is text that will be associated with the payment that the app can use.");

        return payment;
    }

    protected void displayResultText(String result) {
//        ((TextView)findViewById(R.id.txtResult)).setText("Result : " + result);
//        Toast.makeText(
//                getApplicationContext(),
//                result, Toast.LENGTH_LONG)
//                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm =
                    data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i(TAG, confirm.toJSONObject().toString(4));
                    Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));
                    /**
                     *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                     * or consent completion.
                     * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                     * for more details.
                     *
                     * For sample mobile backend interactions, see
                     * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
                     */
                    displayResultText("PaymentConfirmation info received from PayPal");


                } catch (JSONException e) {
                    Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i(TAG, "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i(TAG, "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
