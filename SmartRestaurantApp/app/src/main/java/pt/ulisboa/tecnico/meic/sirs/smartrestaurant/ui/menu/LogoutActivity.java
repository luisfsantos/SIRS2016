package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Intent;
import android.os.Bundle;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.CallsAsyncTask;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.LogoutSR;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.WebRequest;

/**
 * Created by Catarina on 27/11/2016.
 */

public class LogoutActivity extends BaseActivity implements CallsAsyncTask{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LogoutSR(this).execute();
    }
    @Override
    public boolean providesActivityToolbar() {
        return false;
    }

    @Override
    public void onRequestFinished() {
        WebRequest.clearCookies();
        Intent intent = new Intent(LogoutActivity.this, SessionStartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
