package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.os.Bundle;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;

/**
 * Created by Catarina on 15/11/2016.
 */

public class NoNetworkConnectionActivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_error);
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }
}
