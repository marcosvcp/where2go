package activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import br.com.les.where2go.R;

import com.facebook.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

/**
 * Application core
 */
public class MainActivity extends FragmentActivity {
//
    private MainFragment mainFragment;
    private List<View> mViews;
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageButton mBtnEventList = (ImageButton) findViewById(R.id.enterEventListButton);
        mViews = new LinkedList<View>();
        mViews.add(mBtnEventList);
        //TODO 
//        if (savedInstanceState == null) {
//            // Add the fragment on initial activity setup
//            mainFragment = new MainFragment();
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(android.R.id.content, mainFragment)
//                    .commit();
//        } else {
//            // Or set the fragment from restored state info
//            mainFragment = (MainFragment) getSupportFragmentManager()
//                    .findFragmentById(android.R.id.content);
//        }
        
        mBtnEventList.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				lockAll();
				enterEventList();
			}
		});
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
    
    /**
     * Bloqueia todas as Views, para evitar que duas das mesmas sejam selecionadas ao mesmo tempo.
     */
    private void lockAll() {
        for (final View v : mViews) {
            v.setEnabled(false);
        }
    }
    
    /**
     * Desbloqueia as Views.
     */
    private void unlockAll() {
        for (final View v : mViews) {
            v.setEnabled(true);
        }
    }
    
    /**
     * Evento referente a ação do botão EnterEventList.
     */
    public void enterEventList() {
    	final Intent intent = new Intent(MainActivity.this,EventListActivity.class);
    	startActivity(intent);
    }
}
