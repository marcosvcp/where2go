
package activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.les.where2go.R;

/**
 * The Class AdsFragment.
 */
public class AdsFragment extends Fragment {

    /**
     * Instantiates a new ads fragment.
     */
    public AdsFragment() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_ads,
                container, false);

        return rootView;
    }
}
