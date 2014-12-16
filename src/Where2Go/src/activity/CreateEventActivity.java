package activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import persistence.ParseUtil;
import utils.FieldValidation;
import entity.event.Event;
import entity.user.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import br.com.les.where2go.R;

/**
 * The Class CreateEventActivity.
 */
public class CreateEventActivity extends Activity {
	
	private EditText et_event_name;
	private EditText et_event_description;
	private EditText et_event_info;
	private EditText et_event_initial_date;
	private EditText et_event_final_date;
	private Button bt_create_event;
	private Button bt_select_tags;
	private ImageButton bt_add_aditional_informations;
	private Date initialDate, finalDate;
	private SimpleDateFormat dateFormatter;
	private DatePickerDialog initialDatePickerDialog;
    private DatePickerDialog finalDatePickerDialog;
    private AlertDialog dialog;
    private List<String> tags;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		
		tags = new ArrayList<String>();
		
		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

		et_event_name = (EditText) findViewById(R.id.et_event_name);
		et_event_description = (EditText) findViewById(R.id.et_event_description);
		et_event_info = (EditText) findViewById(R.id.et_event_info);
		et_event_initial_date = (EditText) findViewById(R.id.et_event_initial_date);
		et_event_final_date = (EditText) findViewById(R.id.et_event_final_date);
		bt_create_event = (Button) findViewById(R.id.bt_create_event);
		bt_select_tags = (Button) findViewById(R.id.bt_select_tags);
		bt_add_aditional_informations = (ImageButton) findViewById(R.id.bt_add_aditional_informations);

		et_event_final_date.setInputType(InputType.TYPE_NULL);
		et_event_final_date.requestFocusFromTouch();
		et_event_initial_date.setInputType(InputType.TYPE_NULL);
		
		Calendar newCalendar = Calendar.getInstance();
		
        initialDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
 
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                initialDate = newDate.getTime();
                et_event_initial_date.setText(dateFormatter.format(newDate.getTime()));
            }
 
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
		
        finalDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
        	 
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                finalDate = newDate.getTime();
                et_event_final_date.setText(dateFormatter.format(newDate.getTime()));
            }
 
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        
		et_event_initial_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initialDatePickerDialog.show();				
			}
		});
		
		et_event_final_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finalDatePickerDialog.show();				
			}
		});	
		
		bt_create_event.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(checkValidation()){
					Event event = null;
					event = new Event(et_event_name.getText().toString(), et_event_description.getText()
							.toString(), "Default Image Path", et_event_info.getText().toString(), initialDate, finalDate, 100.00, "Default Outfit", 999,
							true, new User("Marcos"));
				
					ParseUtil.saveEvent(event);
					EventsListFragment.adapter.notifyDataSetChanged();
					Intent intent = new Intent(getApplicationContext(), MainScreen.class);
					intent.putExtra("eventslist", 2);
					startActivity(intent);
				}
			}
		});
		
		bt_select_tags.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectTag();
			}
		});
		
		bt_add_aditional_informations.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), AditionalEventInformationActivity.class);
            	startActivity(intent);
			}
		});
		
		
	}
	
	/**
	 * Check validation of the event fields.
	 *
	 * @return true, if successful
	 */
	private boolean checkValidation() {
        boolean ret = true;
        FieldValidation validation = new FieldValidation(this);
        List<EditText> listEditText = new ArrayList<EditText>();
        listEditText.add(et_event_name);
        listEditText.add(et_event_description);
        listEditText.add(et_event_info);
        listEditText.add(et_event_initial_date);
        listEditText.add(et_event_final_date);

        if (!validation.hasText(et_event_name)) {
            ret = false;
        }
        if (!validation.hasText(et_event_description)) {
            ret = false;
        }
        if (!validation.hasText(et_event_info)) {
            ret = false;
        }
        if (!validation.hasText(et_event_initial_date)) {
            ret = false;
        }
        if (!validation.hasText(et_event_final_date)) {
            ret = false;
        }
        return ret;
    }
	
	/**
	 * Method of select a tag on a dialog.
	 */
	public void selectTag() {
		// final CharSequence[] items =
		// {"Festa","Churrasco","Confra","Casamento"};
		

		ParseUtil.findAllTags(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				final CharSequence[] items = new CharSequence[objects.size()];
				if (e == null) {
					
					
					for(int i = 0; i < items.length; i++){
						items[i] = objects.get(i).getString("nome");
					}
					
					final ArrayList<Integer> seletedItems = new ArrayList<Integer>();

					AlertDialog.Builder builder = new AlertDialog.Builder(
							CreateEventActivity.this);
					builder.setTitle("Select The Tags");
					builder.setMultiChoiceItems(items, null,
							new DialogInterface.OnMultiChoiceClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int indexSelected, boolean isChecked) {
									if (isChecked) {
										seletedItems.add(indexSelected);
									} else if (seletedItems
											.contains(indexSelected)) {
										seletedItems.remove(Integer
												.valueOf(indexSelected));
									}
								}
							})

							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog, int id) {
											for (int i = 0; i < seletedItems
													.size(); i++) {
												tags.add(items[i]
														.toString());
											}
											for (int i = 0; i < tags.size(); i++) {
												Log.e("Tags>", tags.get(i));
											}
										}
									})
							.setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog, int id) {
											tags.clear();
										}
									});

					dialog = builder.create();
					dialog.show();
				}
			}
		});
	}
		
}
