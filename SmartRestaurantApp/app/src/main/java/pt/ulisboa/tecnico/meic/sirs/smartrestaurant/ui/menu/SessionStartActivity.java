package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;

/**
 * Created by Catarina on 16/11/2016.
 */

public class SessionStartActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_button)
    public void onLoginClicked(View view) {
        Intent intent = new Intent(SessionStartActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.signup_button)
    public void onSignupClicked(View view) {
        Intent intent = new Intent(SessionStartActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }
}
