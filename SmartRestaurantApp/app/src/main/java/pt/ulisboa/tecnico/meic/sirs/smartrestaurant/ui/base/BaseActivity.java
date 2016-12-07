package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.text.TextUtilsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.lang.reflect.Modifier;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.Order;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.RestaurantMenu;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.AccountDetailsActivity;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu.LogoutActivity;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu.MenuListActivity;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu.OrderListActivity;

import static pt.ulisboa.tecnico.meic.sirs.smartrestaurant.util.LogUtil.logD;
import static pt.ulisboa.tecnico.meic.sirs.smartrestaurant.util.LogUtil.makeLogTag;

/**
 * The base class for all Activity classes.
 * This class creates and provides the navigation drawer and toolbar.
 * The navigation logic is handled in {@link BaseActivity#goToNavDrawerItem(int)}
 * <p>
 * Created by Catarina on 14.12.2015.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = makeLogTag(BaseActivity.class);

    protected static final int NAV_DRAWER_ITEM_INVALID = -1;
    private static final long LOGOUT = 1;

    private DrawerLayout drawerLayout;
    private Drawer drawer;
    private AccountHeader header;
    private Toolbar actionBarToolbar;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupNavDrawer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        GsonBuilder gsonBuilder = new GsonBuilder();
        // Allowing the serialization of static fields
        gsonBuilder.excludeFieldsWithModifiers(Modifier.TRANSIENT);
        Gson gson = gsonBuilder.create();
        String order = gson.toJson(new Order());
        String menu = gson.toJson(new RestaurantMenu());
        outState.putString("order", order);
        outState.putString("menu", menu);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        GsonBuilder gsonBuilder = new GsonBuilder();
        // Allowing the deserialization to static fields
        gsonBuilder.excludeFieldsWithModifiers(Modifier.TRANSIENT);
        Gson gson = gsonBuilder.create();
        String menu = savedInstanceState.getString("menu");
        String order = savedInstanceState.getString("order");
        gson.fromJson(menu, RestaurantMenu.class);
        gson.fromJson(order, Order.class);
    }


    /**
     * Sets up the navigation drawer.
     */
    private void setupNavDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout == null) {
            // current activity does not have a drawer.
            return;
        }

        SharedPreferences sp = getSharedPreferences(getString(R.string.user_info_pref), Context.MODE_PRIVATE);
        String first_name = sp.getString(getString(R.string.user_info_first_name), "Not found.");
        String last_name = sp.getString(getString(R.string.user_info_last_name), "Not found.");
        String email = sp.getString(getString(R.string.user_info_email), "Not found.");

        header = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(first_name + " " + last_name)
                                .withEmail(email)
                                .withIcon(R.mipmap.ic_launcher)
                                .withSelectedTextColorRes(R.color.primary_text),
                        new ProfileSettingDrawerItem()
                                .withName("Logout")
                                .withIcon(R.drawable.ic_logout_variant_black_24dp)
                                .withIdentifier(LOGOUT)

                )
                .withHeaderBackground(new ColorDrawable(ContextCompat.getColor(this, R.color.theme_status_bar)))
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        if (profile instanceof IDrawerItem && profile.getIdentifier() == LOGOUT) {
                            drawer.closeDrawer();
                            Intent intent = new Intent(BaseActivity.this, LogoutActivity.class);
                            startActivity(intent);
                        }
                        return false;
                    }
                })
                .build();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(header, true)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withIdentifier(R.id.nav_restaurant_menu)
                                .withName(R.string.navigation_restaurant_menu)
                                .withTextColorRes(R.color.theme_primary_dark)
                                .withSelectedTextColorRes(R.color.theme_primary_light)
                                .withIcon(R.drawable.ic_restaurant_menu_black_24dp)
                                .withIconColorRes(R.color.theme_primary_dark)
                                .withSelectedIconColorRes(R.color.theme_primary_light),
                        new PrimaryDrawerItem()
                                .withIdentifier(R.id.nav_cart)
                                .withName(R.string.navigation_cart)
                                .withTextColorRes(R.color.theme_primary_dark)
                                .withSelectedTextColorRes(R.color.theme_primary_light)
                                .withIcon(R.drawable.ic_shopping_cart_black_24dp)
                                .withIconColorRes(R.color.theme_primary_dark)
                                .withSelectedIconColorRes(R.color.theme_primary_light),
                        new PrimaryDrawerItem()
                                .withIdentifier(R.id.nav_account)
                                .withName(R.string.navigation_account)
                                .withTextColorRes(R.color.theme_primary_dark)
                                .withSelectedTextColorRes(R.color.theme_primary_light)
                                .withIcon(R.drawable.ic_person_black_24dp)
                                .withIconColorRes(R.color.theme_primary_dark)
                                .withSelectedIconColorRes(R.color.theme_primary_light)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        drawer.closeDrawer();
                        int id = (int) drawerItem.getIdentifier();
                        onNavigationItemClicked(id);
                        return true;
                    }
                })
                .build();
        drawer.setSelection(getSelfNavDrawerItem());

        Log.d(TAG, "navigation drawer setup finished");
    }


    /**
     * Handles the navigation item click.
     *
     * @param itemId the clicked item
     */
    private void onNavigationItemClicked(final int itemId) {
        if (itemId == getSelfNavDrawerItem()) {
            // Already selected
            closeDrawer();
            return;
        }

        goToNavDrawerItem(itemId);
    }

    /**
     * Handles the navigation item click and starts the corresponding activity.
     *
     * @param item the selected navigation item
     */
    private void goToNavDrawerItem(int item) {
        switch (item) {
            case R.id.nav_restaurant_menu:
                startActivity(new Intent(this, MenuListActivity.class));
                finish();
                break;
            case R.id.nav_cart:
                startActivity(new Intent(this, OrderListActivity.class));
                if (!(getSelfNavDrawerItem() == R.id.nav_restaurant_menu)) finish();
                break;
            case R.id.nav_account:
                startActivity(new Intent(this, AccountDetailsActivity.class));
                if (!(getSelfNavDrawerItem() == R.id.nav_restaurant_menu)) finish();
                break;
        }
    }

    /**
     * Provides the action bar instance.
     *
     * @return the action bar.
     */
    protected ActionBar getActionBarToolbar() {
        if (actionBarToolbar == null) {
            actionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (actionBarToolbar != null) {
                setSupportActionBar(actionBarToolbar);
            }
        }
        return getSupportActionBar();
    }


    /**
     * Returns the navigation drawer item that corresponds to this Activity. Subclasses
     * have to override this method.
     */
    protected int getSelfNavDrawerItem() {
        return NAV_DRAWER_ITEM_INVALID;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
            return;
        }
        super.onBackPressed();
    }

    protected void openDrawer() {
        if (drawerLayout == null)
            return;
        drawer.openDrawer();
    }

    protected void closeDrawer() {
        if (drawerLayout == null)
            return;
        drawer.closeDrawer();
    }

    public abstract boolean providesActivityToolbar();

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Base Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
