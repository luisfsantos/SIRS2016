package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.RestaurantMenu;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.web.SearchIMDB;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.util.LogUtil;

import static pt.ulisboa.tecnico.meic.sirs.smartrestaurant.util.LogUtil.makeLogTag;

/**
 * Lists all available quotes. This Activity supports a single pane (= smartphones) and a two pane mode (= large screens with >= 600dp width).
 *
 * Created by Andreas Schrade on 14.12.2015.
 */
public class MenuListActivity extends BaseActivity implements MenuListFragment.Callback {
    /**
     * Whether or not the activity is running on a device with a large screen
     */
    private static final String TAG = makeLogTag(BaseActivity.class);
    TabLayout tabLayout;
    ViewPager viewPager;
    //public static String POSITION = "POSITION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setupToolbar();

        //only make API call if not made already
        if (RestaurantMenu.ITEM_MAP.isEmpty()) {
            updateMenu(this);
        } else {
            loadTabs();
        }
    }

    public void loadTabs() {
        // Get the ViewPager and set its PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SlidingTabsAdapter(getSupportFragmentManager(), MenuListActivity.this));

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        ButterKnife.bind(this);
    }

    public void updateMenu(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new SearchIMDB(context).execute(SlidingTabsAdapter.searchTopics);
            LogUtil.logD(TAG, "Menu updated");
        } else {
            Toast toast = Toast.makeText(this, getText(R.string.mobile_data), Toast.LENGTH_SHORT);
            toast.show();
            LogUtil.logD(TAG, "Failed to update menu");
        }
    }
    @OnClick(R.id.go_to_shopping_cart)
    public void onGoToCartClicked(View view) {
        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        startActivity(new Intent(this, OrderListActivity.class));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt(POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //viewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }

    /**
     * Called when an item has been selected
     *
     * @param id the selected quote ID
     */
    @Override
    public void onItemSelected(String id) {
        Intent detailIntent = new Intent(this, MenuItemDetailActivity.class);
        detailIntent.putExtra(MenuItemDetailFragment.ARG_ITEM_ID, id);
        startActivity(detailIntent);
    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.sample_actions, menu);
//        return true;
//    }

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
        return R.id.nav_restaurant_menu;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }
}
