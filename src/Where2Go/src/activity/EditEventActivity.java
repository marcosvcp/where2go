
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
import android.widget.Toast;
import br.com.les.where2go.R;

import com.parse.GetCallback;
import com.parse.ParseException;

import entity.event.Event;
import persistence.ParseUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * The Class EditEventActivity.
 */
public class EditEventActivity extends Activity {

    /** The et_event_name. */
    private EditText etEventName;

    /** The et_event_description. */
    private EditText etEventDescription;

    /** The et_event_initial_time. */
    private EditText etEventInitialDate;

    /** The et event initial time. */
    private EditText etEventInitialTime;

    /** The et_event_final_time. */
    private EditText etEventFinalDate;

    /** The et event final time. */
    private EditText etEventFinalTime;

    /** The bt_edit_event. */
    private Button btEditEvent;

    /** The bt_edit_aditional_informations. */
    private Button btEditAditionalInformations;

    /** The event. */
    private static Event event;

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

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        etEventName = (EditText) findViewById(R.id.et_event_name);
        etEventDescription = (EditText) findViewById(R.id.et_event_description);
        etEventInitialDate = (EditText) findViewById(R.id.et_event_initial_date);
        etEventInitialTime = (EditText) findViewById(R.id.et_event_initial_time);
        etEventFinalDate = (EditText) findViewById(R.id.et_event_final_date);
        etEventFinalTime = (EditText) findViewById(R.id.et_event_final_time);
        btEditEvent = (Button) findViewById(R.id.bt_edit_event);
        btEditAditionalInformations = (Button) findViewById(R.id.bt_edit_aditional_informations);

        etEventFinalTime.setInputType(InputType.TYPE_NULL);
        etEventFinalTime.requestFocusFromTouch();
        etEventFinalDate.setInputType(InputType.TYPE_NULL);
        etEventFinalDate.requestFocusFromTouch();
        etEventInitialDate.setInputType(InputType.TYPE_NULL);
        etEventInitialTime.setInputType(InputType.TYPE_NULL);
        
        btEditAditionalInformations.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(getApplicationContext(),
                        EditEventAditInfoActivity.class);
                startActivity(intent);
            }
        });


        

        etEventInitialDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                initialDatePickerDialog.show();
            }
        });

        etEventInitialTime.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                initialTimePickerDialog.show();
            }
        });

        etEventFinalDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                finalDatePickerDialog.show();
            }
        });

        etEventFinalTime.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                finalTimePickerDialog.show();
            }
        });

        btEditEvent.setOnClickListener(new OnClickListener() {

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
        event.setName(etEventName.getText().toString());
        event.setDescription(etEventDescription.getText().toString());
        ParseUtil.saveEvent(event);
        EventsListFragment.getAdapter().notifyDataSetChanged();
        final Intent intent = new Intent(getApplicationContext(),
                MainScreen.class);
        intent.putExtra("eventslist", 2);
        startActivity(intent);
    }

    /**
     * Set all fields of view with name of events fields.
     */
    private void setDataFields() {
        final Calendar newCalendar = Calendar.getInstance();
        Date initialDate = event.getInitialDate();
        Toast.makeText(getApplicationContext(), initialDate.toString(), Toast.LENGTH_LONG).show();
        
        etEventName.setText(event.getName());
        etEventDescription.setText(event.getDescription());
        etEventInitialDate.setText(ParseUtil.PT_BR.format(event
                .getInitialDate()));
        etEventFinalDate.setText(ParseUtil.PT_BR.format(event.getFinalDate()));
        etEventInitialTime.setText(Calendar.getInstance().get(
                Calendar.HOUR_OF_DAY)
                + ":" + Calendar.getInstance().get(Calendar.MINUTE));
        
        initialDatePickerDialog = new DatePickerDialog(this,
                new OnDateSetListener() {

                    @SuppressWarnings("deprecation")
                    @Override
                    public void onDateSet(final DatePicker view,
                            final int year, final int monthOfYear,
                            final int dayOfMonth) {
                        final Calendar newDate = Calendar.getInstance();
                        Date eventDate = event.getInitialDate();
                        
                        newDate.set(eventDate.getYear(), eventDate.getMonth(), eventDate.getYear());
                        etEventInitialDate.setText(ParseUtil.PT_BR.format(newDate
                                .getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        initialTimePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(final TimePicker timePicker,
                            final int selectedHour, final int selectedMinute) {
                        etEventInitialTime.setText("" + selectedHour + ":"
                                + selectedMinute);
                        event.setInitialTime("" + selectedHour + ":"
                                + selectedMinute);
                    }
                }, newCalendar.get(Calendar.HOUR_OF_DAY),
                newCalendar.get(Calendar.MINUTE), true);

        finalTimePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(final TimePicker timePicker,
                            final int selectedHour, final int selectedMinute) {
                        etEventFinalTime.setText("" + selectedHour + ":"
                                + selectedMinute);
                        event.setFinalTime("" + selectedHour + ":"
                                + selectedMinute);
                    }
                }, newCalendar.get(Calendar.HOUR_OF_DAY),
                newCalendar.get(Calendar.MINUTE), true);

        finalDatePickerDialog = new DatePickerDialog(this,
                new OnDateSetListener() {

                    @Override
                    public void onDateSet(final DatePicker view,
                            final int year, final int monthOfYear,
                            final int dayOfMonth) {
                        final Calendar newDate = Calendar.getInstance();
                        Date eventDate = event.getFinalDate();
                        newDate.set(eventDate.getYear(), eventDate.getMonth(), eventDate.getDay());
                        etEventFinalDate.setText(dateFormatter.format(newDate
                                .getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
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

    public static Event getEvent() {
        return event;
    }

}
