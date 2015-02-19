
package activity;

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
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.les.where2go.R;
import entity.event.Event;
import persistence.ParseUtil;
import utils.Authenticator;
import utils.FieldValidation;

/**
 * The Class CreateEventActivity.
 */
public class CreateEventActivity extends Activity {

    /**
     * The et_event_name.
     */
    private EditText etEventName;

    /**
     * The et_event_description.
     */
    private EditText etEventDescription;

    /**
     * The et_event_initial_time.
     */
    private EditText etEventInitialDate;

    /**
     * The et event initial time.
     */
    private EditText etEventInitialTime;

    /**
     * The et_event_final_time.
     */
    private EditText etEventFinalDate;

    /**
     * The et event final time.
     */
    private EditText etEventFinalTime;

    /**
     * The bt_create_event.
     */
    private Button btCreateEvent;

    /**
     * The bt_select_tags.
     */
    private Button btSelectTags;

    /**
     * The bt_add_aditional_informations.
     */
    private Button btAddAditionalInformations;

    /**
     * The rb_radio_public.
     */
    private RadioButton rbRadioPublic;

    /**
     * The final date.
     */
    private Date initialDate;

    /**
     * The final date.
     */
    private Date finalDate;

    /**
     * The date formatter.
     */
    private SimpleDateFormat dateFormatter;

    /**
     * The initial date picker dialog.
     */
    private DatePickerDialog initialDatePickerDialog;

    /**
     * The initial time picker dialog.
     */
    private TimePickerDialog initialTimePickerDialog;

    /**
     * The final date picker dialog.
     */
    private DatePickerDialog finalDatePickerDialog;

    /**
     * The final time picker dialog.
     */
    private TimePickerDialog finalTimePickerDialog;

    /**
     * The dialog.
     */
    private AlertDialog dialog;

    /**
     * The tags.
     */
    private List<String> tags;

    /**
     * The event.
     */
    private static Event event;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        setStatusBarColor(findViewById(R.id.statusBarBackground),
                getResources().getColor(R.color.status_bar));

        tags = new ArrayList<String>();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        etEventName = (EditText) findViewById(R.id.et_event_name);
        etEventDescription = (EditText) findViewById(R.id.et_event_description);
        etEventInitialDate = (EditText) findViewById(R.id.et_event_initial_date);
        etEventInitialTime = (EditText) findViewById(R.id.et_event_initial_time);
        etEventFinalDate = (EditText) findViewById(R.id.et_event_final_date);
        etEventFinalTime = (EditText) findViewById(R.id.et_event_final_time);

        btCreateEvent = (Button) findViewById(R.id.bt_create_event);
        btSelectTags = (Button) findViewById(R.id.bt_select_tags);
        btAddAditionalInformations = (Button) findViewById(R.id.bt_add_aditional_informations);

        rbRadioPublic = (RadioButton) findViewById(R.id.rb_radio_public);

        etEventFinalTime.setInputType(InputType.TYPE_NULL);
        etEventFinalTime.requestFocusFromTouch();
        etEventFinalDate.setInputType(InputType.TYPE_NULL);
        etEventFinalDate.requestFocusFromTouch();
        etEventInitialDate.setInputType(InputType.TYPE_NULL);
        etEventInitialTime.setInputType(InputType.TYPE_NULL);

        etEventInitialDate.setText(dateFormatter.format(Calendar.getInstance().getTime()));
        etEventFinalDate.setText(dateFormatter.format(Calendar.getInstance().getTime()));
        etEventInitialTime.setText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":"
                + Calendar.getInstance().get(Calendar.MINUTE));
        etEventFinalTime.setText("23:59");

        initialDate = Calendar.getInstance().getTime();
        finalDate = Calendar.getInstance().getTime();


        final Calendar newCalendar = Calendar.getInstance();

        initialDatePickerDialog = new DatePickerDialog(this,
                new OnDateSetListener() {

                    @Override
                    public void onDateSet(final DatePicker view,
                                          final int year, final int monthOfYear,
                                          final int dayOfMonth) {
                        final Calendar newDate = Calendar.getInstance();
                        Log.d("testandoHora: ", " " + Calendar.getInstance().getTime());
                        newDate.set(year, monthOfYear, dayOfMonth);
                        if (newDate.after(Calendar.getInstance())) {

                            initialDate = newDate.getTime();
                            etEventInitialDate.setText(dateFormatter
                                    .format(newDate.getTime()));

                            etEventFinalDate.setText(dateFormatter
                                    .format(newDate.getTime()));
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid date", Toast.LENGTH_SHORT).show();
                        }

                    }

                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));


        initialTimePickerDialog = new TimePickerDialog(CreateEventActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public final void onTimeSet(final TimePicker timePicker,
                                                final int selectedHour,
                                                final int selectedMinute) {
                        etEventInitialTime.setText("" + selectedHour + ":" + selectedMinute);
                    }
                }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);

        finalTimePickerDialog = new TimePickerDialog(CreateEventActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public final void onTimeSet(final TimePicker timePicker,
                                                final int selectedHour,
                                                final int selectedMinute) {
                        etEventFinalTime.setText("" + selectedHour + ":" + selectedMinute);
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
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(initialDate);
                        if (newDate.compareTo(cal) >= 0) {
                            finalDate = newDate.getTime();
                            etEventFinalDate.setText(dateFormatter
                                    .format(newDate.getTime()));

                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid date", Toast.LENGTH_SHORT).show();
                        }

                    }

                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

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

        btCreateEvent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {

                if (checkValidation()) {

                    event = new Event(etEventName.getText().toString(),
                            etEventDescription.getText().toString(),
                            "Default Image Path", "INFO", initialDate,
                            finalDate, 100.00, "Default Outfit", 999, rbRadioPublic.isChecked(),
                            Authenticator.getInstance().getLoggedUser());

                    if (!tags.isEmpty()) {
                        for (int i = 0; i < tags.size(); i++) {
                            event.addTags(tags.get(i));
                        }
                    }
                    ParseUtil.saveEvent(event);
                    EventsListFragment.getAdapter().notifyDataSetChanged();
                    final Intent intent = new Intent(getApplicationContext(),
                            MainScreen.class);
                    intent.putExtra("eventslist", 2);
                    startActivity(intent);
                }
            }
        });

        btSelectTags.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                selectTag();
            }
        });

        btAddAditionalInformations.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(getApplicationContext(),
                        CreateEventAditInfoActivity.class);
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
        listEditText.add(etEventName);
        listEditText.add(etEventDescription);
        listEditText.add(etEventInitialDate);
        listEditText.add(etEventFinalDate);

        if (!validation.hasText(etEventName)) {
            ret = false;
        }
        if (!validation.hasText(etEventDescription)) {
            ret = false;
        }
        if (!validation.hasText(etEventInitialDate)) {
            ret = false;
        }
        if (!validation.hasText(etEventFinalDate)) {
            ret = false;
        }
        return ret;
    }

    /**
     * Method of select a tag on a dialog.
     */
    public final void selectTag() {
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

                    final List<Integer> seletedItems = new ArrayList<Integer>();

                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            CreateEventActivity.this);

                    builder.setTitle("Select The Tags");

                    builder.setMultiChoiceItems(items, null,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(
                                        final DialogInterface dialogInterface,
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
                                                final DialogInterface dialogInterface,
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
                                            }
                                            btSelectTags.setText(btSelectTags.getText() + t);
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                final DialogInterface dialogInterface,
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

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_event_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
            case R.id.action_cancel:
                final Intent intent = new Intent(getApplicationContext(), MainScreen.class);
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
     * @param statusBar the status bar
     * @param color     the color
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

    /**
     * Gets the event.
     *
     * @return the event
     */
    public static Event getEvent() {
        return event;
    }

    /**
     * Sets the event.
     *
     * @param newEvent the new event
     */
    public static final void setEvent(final Event newEvent) {
        CreateEventActivity.event = newEvent;
    }
}
