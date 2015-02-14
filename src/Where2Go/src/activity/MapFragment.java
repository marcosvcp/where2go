
package activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.les.where2go.R;

/**
 * The Class MapFragment.
 */
public class MapFragment extends Fragment {

    /**
     * Instantiates a new map fragment.
     */
    public MapFragment() {
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
        final View rootView = inflater.inflate(R.layout.activity_map,
                container, false);

        return rootView;
    }
}
