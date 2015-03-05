package utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

/**
 * The Class ConvertBitmapToFile.
 */
public class ConvertBitmapToFile {

    /**
     * Gets the file.
     *
     * @param bitmap
     *            the bitmap
     * @param ctx
     *            the ctx
     * @return the file
     */
    public static FileOutputStream getFile(final Bitmap bitmap,
            final Context ctx) {
        // create a file to write bitmap data
        final File file = new File(ctx.getCacheDir(), "Event Cover");
        try {
            file.createNewFile();
        } catch (final IOException e1) {
            Log.e("FileOutputStream Error", e1.getMessage());
        }

        // Convert bitmap to byte array
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 0 /* ignored for PNG */, bos);
        final byte[] bitmapdata = bos.toByteArray();

        // write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bitmapdata);
        } catch (final IOException e) {
            Log.e("FileOutputStream Error", e.getMessage());
        }
        return fos;
    }
}
