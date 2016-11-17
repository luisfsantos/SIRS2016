package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Intent;
import android.os.Bundle;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;

/**
 * Created by Catarina on 17/11/2016.
 */

public class LoadingMenuActivity extends BaseActivity implements SearchIMDB.AsyncResponse {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_menu);
        SearchIMDB.updateDelegate(this);
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }

    @Override
    public void downloadFinished() {
        Intent intent = new Intent(LoadingMenuActivity.this, MenuListActivity.class);
        startActivity(intent);
        finish();
    }
}
