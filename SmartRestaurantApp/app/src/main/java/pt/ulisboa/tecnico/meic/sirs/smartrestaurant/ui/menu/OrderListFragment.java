package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.Order;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.RestaurantMenu;

/**
 * Created by Catarina on 12/11/2016.
 */
public class OrderListFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new OrderListAdapter(getContext()));
        setHasOptionsMenu(true);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View  emptyView = getActivity().getLayoutInflater().inflate(R.layout.include_empty_cart, null);
        ((ViewGroup)getListView().getParent()).addView(emptyView);
        getListView().setEmptyView(emptyView);
    }

    private class OrderListAdapter extends BaseAdapter {
        UpdateTotalPriceInterface context;

        public OrderListAdapter(Context context) {
            this.context = (OrderListActivity) context;
        }

        @Override
        public int getCount() {
            return Order.ITEMS.size();
        }

        @Override
        public Object getItem(int position) {
            return RestaurantMenu.ITEM_MAP.get(Order.ITEMS.get(position));
        }

        @Override
        public long getItemId(int position) {
            return RestaurantMenu.ITEM_MAP.get(Order.ITEMS.get(position)).id.hashCode();
        }

        public String getItemQuantity(int position) {
            return Integer.toString(Order.getItemQuantity(position));
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_shopping_cart, container, false);
            }

            final RestaurantMenu.MenuItem item = (RestaurantMenu.MenuItem) getItem(position);
            ((TextView) convertView.findViewById(R.id.menu_item_name)).setText(item.name);
            ((TextView) convertView.findViewById(R.id.menu_item_price)).setText(getItemQuantity(position) + " x " + item.price + getString(R.string.currency_symbol));
            final ImageView img = (ImageView) convertView.findViewById(R.id.thumbnail);

            Glide.with(getActivity()).load(item.imageURL).asBitmap().fitCenter().into(new BitmapImageViewTarget(img) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    img.setImageDrawable(circularBitmapDrawable);
                }
            });

            convertView.findViewById(R.id.delete_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order.deleteItem(position);
                    notifyDataSetChanged();
                    context.updateTotalPrice();
                }
            });

            convertView.findViewById(R.id.quantity_up).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order.increaseItemQuantity(position);
                    notifyDataSetChanged();
                    context.updateTotalPrice();

                }
            });

            convertView.findViewById(R.id.quantity_down).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order.decreaseItemQuantity(position);
                    notifyDataSetChanged();
                    context.updateTotalPrice();
                }
            });


            return convertView;
        }
    }

}
