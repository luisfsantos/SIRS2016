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

public class SignupActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
    }
    
    @OnClick(R.id.submit_signup)
    public void onSubmitSignupClicked(View view) {
        //FIXME
        Intent intent = new Intent(SignupActivity.this, PromptQrScanActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    
    @Override
    public boolean providesActivityToolbar() {
        return false;
    }
}
