package activity;


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

public class CreateEventActivity extends Activity {
	private EditText et_event_name;
	private EditText et_event_status;
	private EditText et_event_description;
	private EditText et_event_info;
	private EditText et_event_initial_date;
	private EditText et_event_final_date;
	private Button bt_create_event;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		
        et_event_name = (EditText) findViewById(R.id.et_event_name);
        et_event_status = (EditText) findViewById(R.id.et_event_status);
        et_event_description = (EditText) findViewById(R.id.et_event_description);
        et_event_info = (EditText) findViewById(R.id.et_event_info);
        et_event_initial_date = (EditText) findViewById(R.id.et_event_initial_date);
        et_event_final_date = (EditText) findViewById(R.id.et_event_final_date);
        
        bt_create_event = (Button) findViewById(R.id.bt_create_event);
        bt_create_event.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Event event = new Event(1, et_event_name.getText().toString(), et_event_status.getText().toString(), et_event_description.getText().toString(), null, et_event_info.getText().toString(), et_event_initial_date.getText().toString(), et_event_final_date.getText().toString(), null, null, null, null);
				Log.v("EVENT", "NAME: " + event.getName());
				Log.v("EVENT", "STATUS: " + event.getStatus());
				Log.v("EVENT", "DESCRIPTION: " + event.getDescription());
				Log.v("EVENT", "INFO: " + event.getInfo());
				Log.v("EVENT", "INITIAL DATE: " + event.getInitialDate());
				Log.v("EVENT", "FINAL DATE: " + event.getFinalDate());
				
				//Adiciona evento na lista estática provisória 
				MainActivity.events.add(event);
				Log.v("TAMANHO", MainActivity.events.size()+"");
				
				Toast.makeText(getApplicationContext(), "Event Created", Toast.LENGTH_SHORT).show();
				
	            Intent intent = new Intent(getApplicationContext(), EventListActivity.class);
	            startActivity(intent);
			}
		});
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
