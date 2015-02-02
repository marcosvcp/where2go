package activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import persistence.ParseUtil;
import utils.Authenticator;
import utils.FieldValidation;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import br.com.les.where2go.R;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import entity.event.Event;

/**
 * The Class CreateEventActivity.
 */
public class CreateEventActivity extends Activity {

	private EditText et_event_name;
	private EditText et_event_description;
	private EditText et_event_initial_date, et_event_initial_time;
	private EditText et_event_final_date, et_event_final_time;
	private Button bt_create_event;
	private Button bt_select_tags;
	private Button bt_add_aditional_informations;
	private RadioButton rb_radio_public;
	private Date initialDate, finalDate;
	private SimpleDateFormat dateFormatter;
	private DatePickerDialog initialDatePickerDialog;
	private TimePickerDialog initialTimePickerDialog;
	private DatePickerDialog finalDatePickerDialog;
	private TimePickerDialog finalTimePickerDialog;
	private AlertDialog dialog;
	private List<String> tags;

	public static Event event;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		setStatusBarColor(findViewById(R.id.statusBarBackground),
				getResources().getColor(R.color.status_bar));

		tags = new ArrayList<String>();

		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

		et_event_name = (EditText) findViewById(R.id.et_event_name);
		et_event_description = (EditText) findViewById(R.id.et_event_description);
		et_event_initial_date = (EditText) findViewById(R.id.et_event_initial_date);
		et_event_initial_time = (EditText) findViewById(R.id.et_event_initial_time);
		et_event_final_date = (EditText) findViewById(R.id.et_event_final_date);
		et_event_final_time = (EditText) findViewById(R.id.et_event_final_time);
		
		bt_create_event = (Button) findViewById(R.id.bt_create_event);
		bt_select_tags = (Button) findViewById(R.id.bt_select_tags);
		bt_add_aditional_informations = (Button) findViewById(R.id.bt_add_aditional_informations);

		rb_radio_public = (RadioButton) findViewById(R.id.rb_radio_public);
		
		et_event_final_time.setInputType(InputType.TYPE_NULL);
		et_event_final_time.requestFocusFromTouch();
		et_event_final_date.setInputType(InputType.TYPE_NULL);
		et_event_final_date.requestFocusFromTouch();
		et_event_initial_date.setInputType(InputType.TYPE_NULL);
		et_event_initial_time.setInputType(InputType.TYPE_NULL);
		
		et_event_initial_date.setText(dateFormatter.format(Calendar.getInstance().getTime()));
		et_event_final_date.setText(dateFormatter.format(Calendar.getInstance().getTime()));
		et_event_initial_time.setText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE));
		et_event_final_time.setText("23:59");
		
		final Calendar newCalendar = Calendar.getInstance();

		initialDatePickerDialog = new DatePickerDialog(this,
				new OnDateSetListener() {

					@Override
					public void onDateSet(final DatePicker view,
							final int year, final int monthOfYear,
							final int dayOfMonth) {
						final Calendar newDate = Calendar.getInstance();
						newDate.set(year, monthOfYear, dayOfMonth);
						initialDate = newDate.getTime();
						et_event_initial_date.setText(dateFormatter
								.format(newDate.getTime()));
					}

				}, newCalendar.get(Calendar.YEAR),
				newCalendar.get(Calendar.MONTH),
				newCalendar.get(Calendar.DAY_OF_MONTH));
        
        initialTimePickerDialog = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                et_event_initial_time.setText( "" + selectedHour + ":" + selectedMinute);
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY),newCalendar.get(Calendar.MINUTE),true);
        
        finalTimePickerDialog = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                et_event_final_time.setText( "" + selectedHour + ":" + selectedMinute);
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY),newCalendar.get(Calendar.MINUTE),true);

		finalDatePickerDialog = new DatePickerDialog(this,
				new OnDateSetListener() {

					@Override
					public void onDateSet(final DatePicker view,
							final int year, final int monthOfYear,
							final int dayOfMonth) {
						final Calendar newDate = Calendar.getInstance();
						newDate.set(year, monthOfYear, dayOfMonth);
						finalDate = newDate.getTime();
						et_event_final_date.setText(dateFormatter
								.format(newDate.getTime()));
					}

				}, newCalendar.get(Calendar.YEAR),
				newCalendar.get(Calendar.MONTH),
				newCalendar.get(Calendar.DAY_OF_MONTH));

		et_event_initial_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				initialDatePickerDialog.show();
			}
		});
		
		et_event_initial_time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initialTimePickerDialog.show();
			}
		});

		et_event_final_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				finalDatePickerDialog.show();
			}
		});

		et_event_final_time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finalTimePickerDialog.show();
			}
		});

		bt_create_event.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {

				if (checkValidation()) {
					
					event = new Event(et_event_name.getText().toString(),
							et_event_description.getText().toString(),
							"Default Image Path", "INFO", initialDate,
							finalDate, 100.00, "Default Outfit", 999, rb_radio_public.isChecked(),
							Authenticator.getInstance().getLoggedUser());

					if (!tags.isEmpty()) {
						for (int i = 0; i < tags.size(); i++) {
							event.addTags(tags.get(i));
						}
					}

					ParseUtil.saveEvent(event);
					EventsListFragment.adapter.notifyDataSetChanged();
					final Intent intent = new Intent(getApplicationContext(),
							MainScreen.class);
					intent.putExtra("eventslist", 2);
					startActivity(intent);
				}
			}
		});

		bt_select_tags.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				selectTag();
			}
		});

		bt_add_aditional_informations.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
////N�O APAGAR ESTE TRECHO
//				if (checkValidation()) {
//					final Intent intent = new Intent(getApplicationContext(),
//							AditionalEventInformationActivity.class);
//					event = new Event(et_event_name.getText().toString(),
//							et_event_description.getText().toString(),
//							"Default Image Path", "INFO", initialDate,
//							finalDate, 100.00, "Default Outfit", 999, true,
//							Authenticator.getInstance().getLoggedUser());
//					if (!tags.isEmpty()) {
//						for (int i = 0; i < tags.size(); i++) {
//							event.addTags(tags.get(i));
//						}
//					}
//					startActivity(intent);
//				}
				
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
		final FieldValidation validation = new FieldValidation(this);
		final List<EditText> listEditText = new ArrayList<EditText>();
		listEditText.add(et_event_name);
		listEditText.add(et_event_description);
		listEditText.add(et_event_initial_date);
		listEditText.add(et_event_final_date);

		if (!validation.hasText(et_event_name)) {
			ret = false;
		}
		if (!validation.hasText(et_event_description)) {
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
			public void done(final List<ParseObject> objects,
					final ParseException e) {
				final CharSequence[] items = new CharSequence[objects.size()];

				if (e == null) {
					for (int i = 0; i < items.length; i++) {
						items[i] = objects.get(i).getString("nome");
						Log.d("TagList>", objects.get(i).getString("nome"));
					}

					final ArrayList<Integer> seletedItems = new ArrayList<Integer>();

					final AlertDialog.Builder builder = new AlertDialog.Builder(
							CreateEventActivity.this);

					builder.setTitle("Select The Tags");

					builder.setMultiChoiceItems(items, null,
							new DialogInterface.OnMultiChoiceClickListener() {
								@Override
								public void onClick(
										final DialogInterface dialog,
										final int indexSelected,
										final boolean isChecked) {
									if (isChecked) {
										seletedItems.add(indexSelected);
										Log.d("Selected>", indexSelected + "");

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
												final DialogInterface dialog,
												final int id) {
											String t = "";
											for (int i = 0; i < seletedItems
													.size(); i++) {
												tags.add(items[seletedItems
														.get(i)].toString());
												t.concat(" "
														+ items[seletedItems
																.get(i)]
																.toString());
												Log.d("Tag Adicionada",
														items[seletedItems
																.get(i)]
																.toString());
											}
											bt_select_tags.setText(bt_select_tags.getText() + t);
										}
									})
							.setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												final DialogInterface dialog,
												final int id) {
											tags.clear();
										}
									});

					dialog = builder.create();
					dialog.show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.create_event_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			return true;
		case R.id.action_cancel:
			final Intent intent = new Intent(getApplicationContext(),
					MainScreen.class);
			intent.putExtra("fragmentIndex", 2);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Sets the status bar color.
	 * 
	 * @param statusBar
	 *            the status bar
	 * @param color
	 *            the color
	 */
	public void setStatusBarColor(final View statusBar, final int color) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			final Window w = getWindow();
			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// status bar height
			final int actionBarHeight = getActionBarHeight();
			final int statusBarHeight = getStatusBarHeight();
			// action bar height
			statusBar.getLayoutParams().height = actionBarHeight
					+ statusBarHeight;
			statusBar.setBackgroundColor(color);
		}
	}

	/**
	 * Gets the action bar height.
	 * 
	 * @return the action bar height
	 */
	public int getActionBarHeight() {
		int actionBarHeight = 0;
		final TypedValue tv = new TypedValue();
		if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
			actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
					getResources().getDisplayMetrics());
		}
		return actionBarHeight;
	}

	/**
	 * Gets the status bar height.
	 * 
	 * @return the status bar height
	 */
	public int getStatusBarHeight() {
		int result = 0;
		final int resourceId = getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

}
