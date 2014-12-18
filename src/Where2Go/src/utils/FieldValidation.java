package utils;

import java.util.regex.Pattern;

import android.content.Context;
import android.widget.EditText;
import br.com.les.where2go.R;

/**
 * The Class FieldValidation.
 */
public class FieldValidation {
    // Context
    /** The context. */
    private final Context context;
    // Regular Expression
    /** The email regex. */
    private final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /** The name regex. */
    private final String NAME_REGEX = "^[\\sa-zA-Z0-9_-]{3,16}$";

    /** The password regex. */
    private final String PASSWORD_REGEX = "^[a-zA-Z0-9_-]{6,16}$";

    /** The num ap regex. */
    private final String NUM_AP_REGEX = "^[\\sa-zA-Z0-9_-]{1,10}$";

    /** The currency regex. */
    private final String CURRENCY_REGEX = "(0|[1-9]+[0-9]*)?((\\.[0-9]{2})|(\\[.|,][0-9]{2}))?";

    /**
     * Instantiates a new field validation.
     *
     * @param c
     *            the c
     */
    public FieldValidation(Context c) {
        context = c;
    }

    /**
     * Checks if is valid.
     *
     * @param editText
     *            the edit text
     * @param regex
     *            the regex
     * @param errMsg
     *            the err msg
     * @param required
     *            the required
     * @return true, if is valid
     */
    public boolean isValid(EditText editText, String regex, String errMsg,
            boolean required) {
        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other
        // values
        editText.setError(null);
        // text required and editText is blank, so return false
        if (required && !hasText(editText)) {
            return false;
        }
        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }
        return true;
    }

    /**
     * Checks for text.
     *
     * @param editText
     *            the edit text
     * @return true, if successful
     */
    public boolean hasText(EditText editText) {
        String text = editText.getText().toString().trim();
        editText.setError(null);
        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(context.getString(R.string.required));
            return false;
        }
        return true;
    }
}