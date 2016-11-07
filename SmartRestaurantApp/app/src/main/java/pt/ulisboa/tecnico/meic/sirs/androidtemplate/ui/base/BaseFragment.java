package pt.ulisboa.tecnico.meic.sirs.androidtemplate.ui.base;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import pt.ulisboa.tecnico.meic.sirs.androidtemplate.util.LogUtil;

import static pt.ulisboa.tecnico.meic.sirs.androidtemplate.util.LogUtil.makeLogTag;

/**
 * The base class for all fragment classes.
 *
 * Created by Andreas Schrade on 14.12.2015.
 */
public class BaseFragment extends Fragment {

    private static final String TAG = makeLogTag(BaseFragment.class);

    /**
     * Inflates the layout and binds the view via ButterKnife.
     * @param inflater the inflater
     * @param container the layout container
     * @param layout the layout resource
     * @return the inflated view
     */
    public View inflateAndBind(LayoutInflater inflater, ViewGroup container, int layout) {
        View view = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, view);

        LogUtil.logD(TAG, ">>> view inflated");
        return view;
    }
}
