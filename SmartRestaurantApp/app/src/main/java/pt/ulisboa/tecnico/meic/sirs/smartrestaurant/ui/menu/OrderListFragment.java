package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

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
        setListAdapter(new OrderListFragment.OrderListAdapter());
        setHasOptionsMenu(true);
    }

    private class OrderListAdapter extends BaseAdapter {

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
        public View getView(final int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.shopping_cart_item_article, container, false);
            }

            final RestaurantMenu.MenuItem item = (RestaurantMenu.MenuItem) getItem(position);
            ((TextView) convertView.findViewById(R.id.menu_item_name)).setText(item.title);
            ((TextView) convertView.findViewById(R.id.menu_item_price)).setText(getItemQuantity(position) + " x " + item.year);
            final ImageView img = (ImageView) convertView.findViewById(R.id.thumbnail);

            Glide.with(getActivity()).load(item.posterURL).asBitmap().fitCenter().into(new BitmapImageViewTarget(img) {
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
                }
            });

            convertView.findViewById(R.id.quantity_up).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order.increaseItemQuantity(position);
                    notifyDataSetChanged();
                }
            });

            convertView.findViewById(R.id.quantity_down).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order.decreaseItemQuantity(position);
                    notifyDataSetChanged();
                }
            });


            return convertView;
        }
    }

}
