
package activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import br.com.les.where2go.R;

import com.parse.GetCallback;
import com.parse.ParseException;

import entity.event.Event;
import persistence.ParseUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The Class EditEventActivity.
 */
public class EditEventActivity extends Activity {

    /** The et_event_name. */
    private EditText et_event_name;

    /** The et_event_status. */
    private EditText et_event_status;

    /** The et_event_description. */
    private EditText et_event_description;

    /** The et_event_info. */
    private EditText et_event_info;

    /** The et_event_initial_time. */
    private EditText et_event_initial_date, et_event_initial_time;

    /** The et_event_final_time. */
    private EditText et_event_final_date, et_event_final_time;

    /** The bt_edit_event. */
    private Button bt_edit_event;

    /** The bt_edit_aditional_informations. */
    private Button bt_edit_aditional_informations;

    /** The event. */
    private Event event;

    /** The final date. */
    private Date initialDate, finalDate;

    /** The date formatter. */
    private SimpleDateFormat dateFormatter;

    /** The initial date picker dialog. */
    private DatePickerDialog initialDatePickerDialog;

    /** The initial time picker dialog. */
    private TimePickerDialog initialTimePickerDialog;

    /** The final date picker dialog. */
    private DatePickerDialog finalDatePickerDialog;

    /** The final time picker dialog. */
    private TimePickerDialog finalTimePickerDialog;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        setStatusBarColor(findViewById(R.id.statusBarBackground),
                getResources().getColor(R.color.status_bar));

        final Bundle data = getIntent().getExtras();
        final String key = data.getString("event_id");
        et_event_name = (EditText) findViewById(R.id.et_event_name);
        // et_event_status = (EditText) findViewById(R.id.et_event_status);
        et_event_description = (EditText) findViewById(R.id.et_event_description);
        et_event_initial_date = (EditText) findViewById(R.id.et_event_initial_date);
        et_event_initial_time = (EditText) findViewById(R.id.et_event_initial_time);
        et_event_final_date = (EditText) findViewById(R.id.et_event_final_date);
        et_event_final_time = (EditText) findViewById(R.id.et_event_final_time);
        bt_edit_event = (Button) findViewById(R.id.bt_edit_event);
        bt_edit_aditional_informations = (Button) findViewById(R.id.bt_edit_aditional_informations);

        et_event_final_time.setInputType(InputType.TYPE_NULL);
        et_event_final_time.requestFocusFromTouch();
        et_event_final_date.setInputType(InputType.TYPE_NULL);
        et_event_final_date.requestFocusFromTouch();
        et_event_initial_date.setInputType(InputType.TYPE_NULL);
        et_event_initial_time.setInputType(InputType.TYPE_NULL);

        bt_edit_aditional_informations.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(getApplicationContext(), EditEventAditInfoActivity.class);
                startActivity(intent);
            }
        });

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

        initialTimePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(final TimePicker timePicker,
                            final int selectedHour,
                            final int selectedMinute) {
                        et_event_initial_time.setText("" + selectedHour + ":" + selectedMinute);
                    }
                }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);

        finalTimePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(final TimePicker timePicker, final int selectedHour,
                            final int selectedMinute) {
                        et_event_final_time.setText("" + selectedHour + ":" + selectedMinute);
                    }
                }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);

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
            public void onClick(final View v) {
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
            public void onClick(final View v) {
                finalTimePickerDialog.show();
            }
        });

        bt_edit_event.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                confirmEdition();
            }
        });
        // Busca no servidor o Objeto que tem o ID
        ParseUtil.findEventById(key, new GetCallback<Event>() {
            @Override
            public void done(final Event newEvent, final ParseException e) {
                if (e == null) {
                    event = newEvent;
                    setDataFields();
                }
            }
        });
    }

    /**
     * Confirm edition.
     */
    protected final void confirmEdition() {
        event.setName(et_event_name.getText().toString());
        event.setDescription(et_event_description.getText().toString());
        event.setInfo(et_event_info.getText().toString());
        ParseUtil.saveEvent(event);
        EventsListFragment.adapter.notifyDataSetChanged();
        final Intent intent = new Intent(getApplicationContext(),
                MainScreen.class);
        intent.putExtra("eventslist", 2);
        startActivity(intent);
    }

    /**
     * Set all fields of view with name of events fields.
     */
    private void setDataFields() {
        et_event_name.setText(event.getName());
        et_event_description.setText(event.getDescription());
        et_event_initial_date.setText(ParseUtil.ptbr.format(event
                .getInitialDate()));
        et_event_final_date
                .setText(ParseUtil.ptbr.format(event.getFinalDate()));
        et_event_initial_time.setText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":"
                + Calendar.getInstance().get(Calendar.MINUTE));
        et_event_final_time.setText("23:59");
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_list, menu);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets the status bar color.
     * 
     * @param statusBar the status bar
     * @param color the color
     */
    public final void setStatusBarColor(final View statusBar, final int color) {
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
    public final int getActionBarHeight() {
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
    public final int getStatusBarHeight() {
        int result = 0;
        final int resourceId = getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
