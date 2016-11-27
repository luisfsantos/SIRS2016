package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.Order;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;

/**
 * Created by Catarina on 09/11/2016.
 */

public class OrderListActivity extends BaseActivity implements UpdateTotalPriceInterface{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        setupToolbar();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        updateTotalPrice();
    }


    @OnClick(R.id.payment_fab)
    public void onPaymentClicked(View view) {
        if (Order.isEmpty()) {
            Toast.makeText(this, "Your order is empty. Nothing to pay for.", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, PaymentActivity.class));
        }
    }


    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.nav_cart;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    @Override
    public void updateTotalPrice() {
        ((TextView)findViewById(R.id.total_price)).setText(String.format("%.2f", Order.getTotalPrice()) + getString(R.string.currency));
    }
}
