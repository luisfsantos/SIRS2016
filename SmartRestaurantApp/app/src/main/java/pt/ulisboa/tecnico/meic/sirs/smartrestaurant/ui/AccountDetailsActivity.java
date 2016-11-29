package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;

/**
 * Created by Catarina on 29.11.2016.
 */
public class AccountDetailsActivity extends BaseActivity {

    @Bind(R.id.username)
    TextView username;

    @Bind(R.id.email)
    TextView email;

    @Bind(R.id.first_name)
    TextView firstName;

    @Bind(R.id.last_name)
    TextView lastName;

    @Bind(R.id.vat_id)
    TextView vatID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        setupToolbar();
        ButterKnife.bind(this);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences(getString(R.string.user_info_pref), Context.MODE_PRIVATE);
        username.setText(sp.getString(getString(R.string.user_info_username), "Not found."));
        email.setText(sp.getString(getString(R.string.user_info_email), "Not found."));
        firstName.setText(sp.getString(getString(R.string.user_info_first_name), "Not found."));
        lastName.setText(sp.getString(getString(R.string.user_info_last_name), "Not found."));
        vatID.setText(Integer.toString(sp.getInt(getString(R.string.user_info_nif), 0)));
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
        return R.id.nav_account;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }
}
