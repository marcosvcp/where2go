package activity;

import java.util.ArrayList;
import java.util.List;

import persistence.ParseUtil;
import utils.FieldValidation;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.com.les.where2go.R;
import entity.event.Event;

public class AditionalEventInformationActivity extends Activity {
    private EditText et_event_notes;
    private EditText et_event_outfit;
    private EditText et_event_capacity;
    private Button bt_create_event_in_aditional_information;
    private final FieldValidation validation = new FieldValidation(this);

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aditional_event_information);
        et_event_notes = (EditText) findViewById(R.id.et_event_notes);
        et_event_outfit = (EditText) findViewById(R.id.et_event_outfit);
        et_event_capacity = (EditText) findViewById(R.id.et_event_capacity);
        bt_create_event_in_aditional_information = (Button) findViewById(R.id.bt_create_event_in_aditional_information);

        bt_create_event_in_aditional_information
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(final View v) {

                        final Event event = CreateEventActivity.event;
                        if (validation.hasText(et_event_notes)) {
                            event.setNote(et_event_notes.getText().toString());
                        }
                        if (validation.hasText(et_event_outfit)) {
                            event.setOutfit(et_event_outfit.getText()
                                    .toString());
                        }
                        if (validation.hasText(et_event_capacity)) {
                            event.setCapacity(Integer
                                    .parseInt(et_event_capacity.getText()
                                            .toString()));
                        }
                        ParseUtil.saveEvent(event);
                        EventsListFragment.adapter.notifyDataSetChanged();
                        final Intent intent = new Intent(
                                getApplicationContext(), MainScreen.class);
                        intent.putExtra("eventslist", 2);
                        startActivity(intent);

                    }
                });

    }

    // Nao esta usando pq creio que n eh necessario preencher essas informacoes
    private boolean checkValidation() {
        boolean ret = true;
        final FieldValidation validation = new FieldValidation(this);
        final List<EditText> listEditText = new ArrayList<EditText>();
        listEditText.add(et_event_notes);
        listEditText.add(et_event_outfit);
        listEditText.add(et_event_capacity);

        if (!validation.hasText(et_event_notes)) {
            ret = false;
        }
        if (!validation.hasText(et_event_outfit)) {
            ret = false;
        }
        if (!validation.hasText(et_event_capacity)) {
            ret = false;
        }
        return ret;
    }

}
