package activity;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private final String url;
    private final ImageView imageView;

    public ImageLoadTask(final String url, final ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(final Void... params) {
        try {
            final URL urlConnection = new URL(url);
            final HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            final InputStream input = connection.getInputStream();
            final Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(final Bitmap result) {
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
        imageView.setAdjustViewBounds(true);
    }

}