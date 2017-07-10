package android.app.com.emilyrobot;

/**
 * Created by vinay on 4/26/2017.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class HttpCameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private static final String url = Settings.cameraurl;

    private CanvasThread canvasThread;

    private SurfaceHolder holder;
    private HttpCamera camera;

    private int viewWidth;
    private int viewHeight;

    public HttpCameraPreview(Context context, int viewWidth, int viewHeight) {

        super(context);

        holder = getHolder();

        holder.addCallback(this);

        holder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

        this.viewWidth = viewWidth;

        this.viewHeight = viewHeight;

        canvasThread = new CanvasThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder2, int format, int w, int h) {

        try {

            Canvas c = holder.lockCanvas(null);

            camera.captureAndDraw(c);

            if (c != null) {

                holder.unlockCanvasAndPost(c);

            }

        }

        catch (Exception e) {

            Log.e(getClass().getSimpleName(), "Error when surface changed", e);

            camera = null;

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {

        try {

            camera = new HttpCamera(url, viewWidth, viewHeight, true);

            canvasThread.setRunning(true);

            canvasThread.start();

        }

        catch (Exception e) {

            Log.e(getClass().getSimpleName(), "Error while creating surface", e);

            camera = null;

        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {

        camera = null;

        boolean retry = true;

        canvasThread.setRunning(false);

        while (retry) {

            try {

                canvasThread.join();

                retry = false;

            } catch (InterruptedException e) {

            }

        }

    }

    private class CanvasThread extends Thread {

        private boolean running;

        public void setRunning(boolean running){

            this.running = running;

        }

        public void run() {

            while (running) {

                Canvas c = null;

                try {

                    c = holder.lockCanvas(null);

                    synchronized (holder) {

                        camera.captureAndDraw(c);

                    }

                }

                catch (Exception e) {

                    Log.e(getClass().getSimpleName(), "Error while drawing canvas", e);

                }

                finally {

                    if (c != null) {

                        holder.unlockCanvasAndPost(c);

                    }

                }

            }

        }

    }

}