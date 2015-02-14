
package utils;

import android.content.Context;
import android.widget.EditText;

import br.com.les.where2go.R;

import java.util.regex.Pattern;

/**
 * The Class FieldValidation.
 */
public class FieldValidation {
    // Context
    /** The context. */
    private final Context context;

    /** The currency regex. */

    /**
     * Instantiates a new field validation.
     * 
     * @param c the c
     */
    public FieldValidation(final Context c) {
        context = c;
    }

    /**
     * Checks if is valid.
     * 
     * @param editText the edit text
     * @param regex the regex
     * @param errMsg the err msg
     * @param required the required
     * @return true, if is valid
     */
    public final boolean isValid(final EditText editText, final String regex, final String errMsg,
            final boolean required) {
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
     * @param editText the edit text
     * @return true, if successful
     */
    public final boolean hasText(final EditText editText) {
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
