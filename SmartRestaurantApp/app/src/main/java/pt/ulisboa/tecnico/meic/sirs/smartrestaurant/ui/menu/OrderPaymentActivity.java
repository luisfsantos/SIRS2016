package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.app.Activity;
import android.content.Intent;
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
import org.json.JSONObject;

import java.math.BigDecimal;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.BuildConfig;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.Order;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.RestaurantMenu;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.CallsAsyncTask;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.GetOrderInfoSR;

/**
 * Created by Catarina on 14/11/2016.
 */
public class OrderPaymentActivity extends BaseActivity implements CallsAsyncTask {

    private static final String TAG = "OrderPaymentActivity";
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;


    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = BuildConfig.PAYPAL_CLIENT_ID;

    private static final int REQUEST_CODE_PAYMENT = 1;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);

    private int payment_chosen;
    private JSONObject order;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);

        payment_chosen = getIntent().getExtras().getInt(ChoosePaymentMethodActivity.PAYMENT_METHOD);
        if (payment_chosen == ChoosePaymentMethodActivity.PAYPAL_CHOSEN) {
            Intent intent = new Intent(this, PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            startService(intent);
        }

        fillOrderCard();
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
        switch (payment_chosen) {
            case ChoosePaymentMethodActivity.PAYPAL_CHOSEN:
                new GetOrderInfoSR(this).execute(Order.toJSONArray());
                break;
            case ChoosePaymentMethodActivity.CASH_CHOSEN:
                //TODO launch confirm activity?
                Toast.makeText(this, "Order submitted", Toast.LENGTH_SHORT).show();
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
            ((TextView) view.findViewById(R.id.menu_item_price)).setText(Order.getItemQuantity(pos) + " x " + item.price + getString(R.string.currency_symbol));
            orderList.addView(view);
        }
        ((TextView) findViewById(R.id.order_price)).setText(String.format("%.2f%s", Order.getTotalPrice(), getString(R.string.currency_symbol)));
    }

    @Override
    public void onRequestFinished(Object object) {
        order = (JSONObject) object;
        handlePayPalPayment();
    }

    /**
    * PAYPAL STUFF BELOW
    * */
    private void handlePayPalPayment() {
        try {
            PayPalPayment stuffToBuy = getStuffToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
            Intent intent = new Intent(OrderPaymentActivity.this, PaymentActivity.class);
            // send the same configuration for restart resiliency
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, stuffToBuy);
            startActivityForResult(intent, REQUEST_CODE_PAYMENT);
        } catch (JSONException e) {
            Toast.makeText(this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * This method shows use of optional payment details and item list.
     */
    private PayPalPayment getStuffToBuy(String paymentIntent) throws JSONException {
        //--- include an item list, payment amount details
        String currency = getString(R.string.currency_short);

            int itemsInOrder = order.getJSONArray("order_items").length();
            PayPalItem[] items = new PayPalItem[itemsInOrder];
            JSONObject order_item;
            for (int i = 0; i < itemsInOrder; i++) {
                order_item = order.getJSONArray("order_items").getJSONObject(i);
                items[i] = new PayPalItem(
                        order_item.getString("item_name"),
                        order_item.getInt("quantity"),
                        new BigDecimal(order_item.getString("price")),
                        currency,
                        order_item.getString("menu_item_id"));
            }

        BigDecimal subtotal = PayPalItem.getItemTotal(items);
        BigDecimal shipping = new BigDecimal("0.00");
        BigDecimal tax = new BigDecimal("0.00");
        PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(shipping, subtotal, tax);
        BigDecimal amount = subtotal.add(shipping).add(tax);
        PayPalPayment payment = new PayPalPayment(amount, currency, "Smart Restaurant Meal", paymentIntent);
        payment.items(items).paymentDetails(paymentDetails);

        //--- set other optional fields like invoice_number, custom field, and soft_descriptor
        payment.custom(order.getString("identifier"));
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
