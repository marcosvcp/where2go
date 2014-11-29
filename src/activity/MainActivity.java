package activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import br.com.les.where2go.R;
import com.facebook.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Application core
 */
public class MainActivity extends FragmentActivity {

    private MainFragment mainFragment;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "activity",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Wrong Package", e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            Log.d("SHA Algorithm Not Found", e.getMessage());
        }
        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mainFragment = new MainFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, mainFragment)
                    .commit();
        } else {
            // Or set the fragment from restored state info
            mainFragment = (MainFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }
        setContentView(R.layout.main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
