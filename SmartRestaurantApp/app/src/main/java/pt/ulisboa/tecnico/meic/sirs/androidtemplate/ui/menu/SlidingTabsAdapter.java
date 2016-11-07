package pt.ulisboa.tecnico.meic.sirs.androidtemplate.ui.menu;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pt.ulisboa.tecnico.meic.sirs.androidtemplate.R;

/**
 * Created by Catarina on 06/11/2016.
 */
public class SlidingTabsAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 4;
    private String[] tabTitles;
    Context context;

    public SlidingTabsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabTitles = new String[] {
                context.getString(R.string.tab_one),
                context.getString(R.string.tab_two),
                context.getString(R.string.tab_three),
                context.getString(R.string.tab_four)};
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return new MenuListFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
