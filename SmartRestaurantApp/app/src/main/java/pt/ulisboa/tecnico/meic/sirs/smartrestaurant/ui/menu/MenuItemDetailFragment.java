package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.OnClick;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.Order;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.RestaurantMenu;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseActivity;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.base.BaseFragment;

/**
 * Shows the quote detail page.
 *
 * Created by Andreas Schrade on 14.12.2015.
 */
public class MenuItemDetailFragment extends BaseFragment {

    /**
     * The argument represents the dummy item ID of this fragment.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content of this fragment.
     */
    private RestaurantMenu.MenuItem menuItem;

    @Bind(R.id.description)
    TextView description;

    @Bind(R.id.price)
    TextView price;

    @Bind(R.id.backdrop)
    ImageView backdropImg;

    @Bind(R.id.calorie_count)
    TextView calorie_count;

    @Bind(R.id.ingredients)
    TextView ingredient_list;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @Bind(R.id.add_to_cart)
    FloatingActionButton addCart;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // load menu item by using the passed item ID.
            menuItem = RestaurantMenu.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflateAndBind(inflater, container, R.layout.fragment_menu_item_detail);

        if (!((BaseActivity) getActivity()).providesActivityToolbar()) {
            // No Toolbar present. Set include_toolbar:
            ((BaseActivity) getActivity()).setToolbar((Toolbar) rootView.findViewById(R.id.toolbar));
        }

        if (menuItem != null) {
            loadBackdrop();
            collapsingToolbar.setTitle(menuItem.name);
            price.setText(Float.toString(menuItem.price) + getString(R.string.currency));
            description.setText(menuItem.description);
            calorie_count.setText(Integer.toString(menuItem.calories) + " " + getString(R.string.menu_item_calories_unit));
            ingredient_list.setText(TextUtils.join(", ", menuItem.ingredients));
        }

        return rootView;
    }

    private void loadBackdrop() {
        Glide.with(this).load(menuItem.imageURL).centerCrop().into(backdropImg);
    }

    @OnClick(R.id.add_to_cart)
    public void onAddToCartClicked(View view) {
        String msg;
        if(Order.addItem(menuItem)) {
            msg = "Added to your order";
        } else {
            msg = "Already in your order";
        }
        Snackbar.make(view, msg , Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public static MenuItemDetailFragment newInstance(String itemID) {
        MenuItemDetailFragment fragment = new MenuItemDetailFragment();
        Bundle args = new Bundle();
        args.putString(MenuItemDetailFragment.ARG_ITEM_ID, itemID);
        fragment.setArguments(args);
        return fragment;
    }

    public MenuItemDetailFragment() {}
}
