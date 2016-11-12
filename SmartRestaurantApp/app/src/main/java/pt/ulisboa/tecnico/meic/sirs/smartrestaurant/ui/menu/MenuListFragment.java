package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;
import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.data.RestaurantMenu;

/**
 * Shows a list of all available quotes.
 * <p/>
 * Created by Andreas Schrade on 14.12.2015.
 */
public class MenuListFragment extends ListFragment {

    private Callback callback = dummyCallback;
    private String searchTopic;

    /**
     * A callback interface. Called whenever a item has been selected.
     */
    public interface Callback {
        void onItemSelected(String id);
    }

    /**
     * A dummy no-op implementation of the Callback interface. Only used when no active Activity is present.
     */
    private static final Callback dummyCallback = new Callback() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new MyListAdapter());
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        searchTopic = bundle.getString("searchTopic");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // notify callback about the selected list item
        callback.onItemSelected(RestaurantMenu.COURSE_MAP.get(searchTopic).ITEMS.get(position).id);
        //callback.onItemSelected(RestaurantMenu.ITEMS.get(position).id);
    }

    /**
     * onAttach(Context) is not called on pre API 23 versions of Android.
     * onAttach(Activity) is deprecated but still necessary on older devices.
     */
    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    /**
     * Deprecated on API 23 but still necessary for pre API 23 devices.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    /**
     * Called when the fragment attaches to the context
     */
    protected void onAttachToContext(Context context) {
        if (!(context instanceof Callback)) {
            throw new IllegalStateException("Activity must implement callback interface.");
        }

        callback = (Callback) context;
    }

    public class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return RestaurantMenu.COURSE_MAP.get(searchTopic).ITEMS.size();
            //return RestaurantMenu.ITEMS.size();
        }

        @Override
        public Object getItem(int position) {
            return RestaurantMenu.COURSE_MAP.get(searchTopic).ITEMS.get(position);
            //return RestaurantMenu.ITEMS.get(position);
        }

        @Override
        public long getItemId(int position) {
            return RestaurantMenu.COURSE_MAP.get(searchTopic).ITEMS.get(position).id.hashCode();
            //return RestaurantMenu.ITEMS.get(position).id.hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_article, container, false);
            }

            final RestaurantMenu.MenuItem item = (RestaurantMenu.MenuItem) getItem(position);
            ((TextView) convertView.findViewById(R.id.menu_item_name)).setText(item.title);
            ((TextView) convertView.findViewById(R.id.menu_item_price)).setText(item.year);
            final ImageView img = (ImageView) convertView.findViewById(R.id.thumbnail);

            Glide.with(getActivity()).load(item.posterURL).asBitmap().fitCenter().into(new BitmapImageViewTarget(img) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    img.setImageDrawable(circularBitmapDrawable);
                }
            });

            return convertView;
        }
    }

    public MenuListFragment() {
    }
}