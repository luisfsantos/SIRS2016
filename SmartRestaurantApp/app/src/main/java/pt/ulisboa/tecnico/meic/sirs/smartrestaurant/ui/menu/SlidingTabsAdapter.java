package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.BuildConfig;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;

/**
 * Created by Catarina on 06/11/2016.
 */
public class SlidingTabsAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 3;
    private String[] tabTitles;
    static String[] searchTopics = new String[] {BuildConfig.AP_DIR, BuildConfig.MC_DIR,  BuildConfig.DE_DIR};
    private Context context;

    public SlidingTabsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabTitles = new String[] {
                context.getString(R.string.tab_one),
                context.getString(R.string.tab_two),
                context.getString(R.string.tab_three)};

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        MenuListFragment f = new MenuListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("searchTopic", searchTopics[position]);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate name based on item position
        return tabTitles[position];
    }
}
