package android.app.com.emilyrobot;

/**
 * Created by vinay on 4/26/2017.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpCamera {

    private static final int CONNECT_TIMEOUT = 1000;
    private static final int SOCKET_TIMEOUT = 1000;

    private final String url;
    private final Rect bounds;
    private final boolean preserveAspectRatio;
    private final Paint paint = new Paint();

    public HttpCamera(String url, int width, int height, boolean preserveAspectRatio) {

        this.url = url;

        bounds = new Rect(0, 0, width, height);

        this.preserveAspectRatio = preserveAspectRatio;

        paint.setFilterBitmap(true);

        paint.setAntiAlias(true);
    }

    private Bitmap retrieveBitmap() throws IOException {

        Bitmap bitmap = null;

        InputStream in = null;

        int response = -1;

        try {

            URL url = new URL(this.url);

            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setConnectTimeout(CONNECT_TIMEOUT);

            httpConn.setReadTimeout(SOCKET_TIMEOUT);

            httpConn.setRequestMethod("GET");

            httpConn.connect();

            response = httpConn.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) {

                in = httpConn.getInputStream();

                bitmap = BitmapFactory.decodeStream(in);

            }

            return bitmap;

        }

        catch (Exception e) {

            return null;

        }

        finally {

            if (in != null) try {

                in.close();

            } catch (IOException e) {

    /* ignore */

            }

        }

    }

    public boolean captureAndDraw(Canvas canvas) throws IOException {

        Bitmap bitmap = retrieveBitmap();

        if (bitmap == null) throw new IOException("Response Code: ");

        //render it to canvas, scaling if necessary

        if (bounds.right == bitmap.getWidth() && bounds.bottom == bitmap.getHeight()) {

            canvas.drawBitmap(bitmap, 0, 0, null);

        } else {

            Rect dest;

            if (preserveAspectRatio) {

                dest = new Rect(bounds);

                dest.bottom = bitmap.getHeight() * bounds.right / bitmap.getWidth();

                dest.offset(0, (bounds.bottom - dest.bottom)/2);

            }

            else {

                dest = bounds;

            }

            canvas.drawBitmap(bitmap, null, dest, paint);

        }

        return true;

    }

}