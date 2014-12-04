package activity;


import persistence.DatabaseStorage;
import entity.event.Event;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.les.where2go.R;

public class EditEventActivity extends Activity {
	private EditText et_event_name;
	private EditText et_event_status;
	private EditText et_event_description;
	private EditText et_event_info;
	private EditText et_event_initial_date;
	private EditText et_event_final_date;
	private Button bt_edit_event;
	private Event event;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_event);
		
		Bundle data = getIntent().getExtras();
		int key = data.getInt("event_id");
		DatabaseStorage bd = new DatabaseStorage(EditEventActivity.this);
		event = bd.getEvent(key);
		
        et_event_name = (EditText) findViewById(R.id.et_event_name);
        //et_event_status = (EditText) findViewById(R.id.et_event_status);
        et_event_description = (EditText) findViewById(R.id.et_event_description);
        et_event_info = (EditText) findViewById(R.id.et_event_info);
        et_event_initial_date = (EditText) findViewById(R.id.et_event_initial_date);
        et_event_final_date = (EditText) findViewById(R.id.et_event_final_date);
        bt_edit_event = (Button) findViewById(R.id.bt_edit_event);
        
        setDataFields();
        
        bt_edit_event.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				confirmEdition();
			}
		});
	}

	protected void confirmEdition() {
		EventsListFragment.bdHelper.delete(event.getId());
		Event new_event = new Event(null, et_event_name.getText().toString(), "Opened", et_event_description.getText().toString(), "Default Image Path", et_event_info.getText().toString(), et_event_initial_date.getText().toString(), et_event_final_date.getText().toString(), "Default Price", "Default Outfit", 999, "Default Time Stamp");
		EventsListFragment.bdHelper.add(new_event);
		
		EventsListFragment.adapter.notifyDataSetChanged();
		Intent intent = new Intent(getApplicationContext(), MainScreen.class);
		intent.putExtra("eventslist", 2);
        startActivity(intent);
	}

	private void setDataFields() {
		et_event_name.setText(event.getName());
		et_event_description.setText(event.getDescription());
		et_event_info.setText(event.getInfo());
		et_event_initial_date.setText(event.getInitialDate());
		et_event_final_date.setText(event.getFinalDate());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		return super.onOptionsItemSelected(item);
	}
}
