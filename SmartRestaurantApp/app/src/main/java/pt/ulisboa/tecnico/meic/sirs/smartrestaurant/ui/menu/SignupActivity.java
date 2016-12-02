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
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.SignUpSR;

/**
 * Created by Catarina on 16/11/2016.
 */

public class SignUpActivity extends BaseActivity implements CallsAsyncTask {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
    }
    
    @OnClick(R.id.submit_signup)
    public void onSubmitSignUpClicked(View view) {
        String email = ((EditText)findViewById(R.id.email)).getText().toString();
        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();
        String firstName = ((EditText)findViewById(R.id.first_name)).getText().toString();
        String lastName = ((EditText)findViewById(R.id.last_name)).getText().toString();
        String nif = ((EditText)findViewById(R.id.nif)).getText().toString();

        if (email.matches("") || username.matches("")
                || password.matches("") || firstName.matches("")
                || lastName.matches("") || nif.matches("") ) {
            updateErrorView("All fields are mandatory.");
            return;
        }
        new SignUpSR(this).execute(email, username, password, firstName, lastName, nif);
    }
    
    @Override
    public boolean providesActivityToolbar() {
        return false;
    }

    @Override
    public void onRequestFinished(Object object) {
        Intent intent = new Intent(SignUpActivity.this, PromptQrScanActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void updateErrorView(String content) {
        ((TextView)findViewById(R.id.oops_text)).setText(content);
    }
}
