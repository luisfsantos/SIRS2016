package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.Order;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.RestaurantMenu;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;

/**
 * Created by Catarina on 14/11/2016.
 */

public class ChoosePaymentMethodActivity extends BaseActivity  {

    public static final String PAYMENT_METHOD = "PaymentMethod";
    public static final int PAYPAL_CHOSEN = 1;
    public static final int CASH_CHOSEN = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.proceed)
    public void onProceedButtonClicked(View view) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.payment_radio_group);
        int method_checked = radioGroup.getCheckedRadioButtonId();

        switch (method_checked) {
            case R.id.choice_paypal:
                method_checked = PAYPAL_CHOSEN;
                break;
            case R.id.choice_cash:
                method_checked = CASH_CHOSEN;
                break;
        }
        Intent intent = new Intent(ChoosePaymentMethodActivity.this, OrderPaymentActivity.class);
        intent.putExtra(PAYMENT_METHOD, method_checked);
        startActivity(intent);
    }

    @Override
    public boolean providesActivityToolbar() { return true; }
}
