package activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import persistence.ParseUtil;
import entity.event.Event;
import entity.user.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.com.les.where2go.R;

public class CreateEventActivity extends Activity {
	private EditText et_event_name;
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
		et_event_description = (EditText) findViewById(R.id.et_event_description);
		et_event_info = (EditText) findViewById(R.id.et_event_info);
		et_event_initial_date = (EditText) findViewById(R.id.et_event_initial_date);
		et_event_final_date = (EditText) findViewById(R.id.et_event_final_date);

		bt_create_event = (Button) findViewById(R.id.bt_create_event);
		bt_create_event.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Event event = null;
				try {
					event = new Event(et_event_name.getText().toString(), et_event_description.getText()
							.toString(), "Default Image Path", et_event_info.getText().toString(), ParseUtil.ptbr.parse(et_event_initial_date
							.getText().toString()), ParseUtil.ptbr.parse(et_event_final_date.getText().toString()), 100.00, "Default Outfit", 999,
							true, new User("Marcos", 18)); 
				} catch (ParseException e) {
					Log.e("DATEFORMAT", "Formato Inválido de Data");
				}
				// Salva o evento
				ParseUtil.saveEvent(event);
				EventsListFragment.adapter.notifyDataSetChanged();
				Intent intent = new Intent(getApplicationContext(), MainScreen.class);
				intent.putExtra("eventslist", 2);
				startActivity(intent);
			}
		});
	}

}
