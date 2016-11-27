package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;

import static pt.ulisboa.tecnico.meic.sirs.smartrestaurant.util.LogUtil.makeLogTag;

/**
 * Lists all available menu items.
 *
 * Created by Andreas Schrade on 14.12.2015.
 */
public class MenuListActivity extends BaseActivity implements MenuListFragment.Callback {

    private static final String TAG = makeLogTag(BaseActivity.class);
    TabLayout tabLayout;
    ViewPager viewPager;
    //public static String POSITION = "POSITION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        setupToolbar();
        loadTabs();
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

    @OnClick(R.id.go_to_shopping_cart)
    public void onGoToCartClicked(View view) {
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
     * @param id the selected item ID
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
