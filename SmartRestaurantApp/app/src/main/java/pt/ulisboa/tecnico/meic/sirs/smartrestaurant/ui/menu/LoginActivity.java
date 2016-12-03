package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.CallsAsyncTask;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.LoginSR;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.TestSR;

/**
 * Created by Catarina on 16/11/2016.
 */

public class LoginActivity extends BaseActivity implements CallsAsyncTask {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.submit_login)
    public void onSubmitLoginClicked(View view) {
        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();

        if (username.matches("") || password.matches("")) {
            updateErrorView("One or more fields are missing.");
            return;
        }
        new LoginSR(this).execute(username, password);
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }

    public void updateErrorView(String content) {
        ((TextView)findViewById(R.id.oops_text)).setText(content);
    }

    @Override
    public void onRequestFinished(Object object) {
        Intent intent = new Intent(LoginActivity.this, PromptQrScanActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
