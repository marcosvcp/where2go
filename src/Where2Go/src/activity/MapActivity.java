package activity;

import android.app.Activity;
import android.os.Bundle;
import br.com.les.where2go.R;

/**
 * The Class MapFragment.
 */
public class MapActivity extends Activity {

    /**
     * Instantiates a new map fragment.
     */
    public MapActivity() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }
}
