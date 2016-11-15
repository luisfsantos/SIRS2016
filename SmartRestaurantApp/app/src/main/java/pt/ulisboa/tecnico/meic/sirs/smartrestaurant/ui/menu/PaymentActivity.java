package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
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

public class PaymentActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);
        fillOrderCard();

        ButterKnife.bind(this);

    }

    @OnClick(R.id.scan_qr_code)
    public void onScanQrCodeClicked(View view) {
        Toast.makeText(this, "Clicky click", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.submit_order)
    public void onSubmitOrderClicked(View view) {
        Toast.makeText(this, "Order submitted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean providesActivityToolbar() { return true; }

    public void fillOrderCard() {
         LinearLayout orderList = ((LinearLayout) findViewById(R.id.order_payment_list));
        for (int pos = 0; pos < Order.ITEMS.size(); pos++) {
            RestaurantMenu.MenuItem item = RestaurantMenu.ITEM_MAP.get(Order.ITEMS.get(pos));
            View view = getLayoutInflater().inflate(R.layout.list_item_order_payment, null);
            ((TextView) view.findViewById(R.id.menu_item_name)).setText(item.title);
            ((TextView) view.findViewById(R.id.menu_item_price)).setText(Order.getItemQuantity(pos) + " x " + item.year);
            orderList.addView(view);
        }
        ((TextView) findViewById(R.id.order_price)).setText(Order.getTotalPrice() + "€");
    }
}
