package pl.polsl.paum.proj.canvases;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.view.MotionEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.polsl.paum.proj.InternalStorageManager;

public class DrawCanvas extends PreviewCanvas {
    public List<Path> paths = new ArrayList<>();
    private Path path;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                paths.add(path);
                path.moveTo(x-ofx,y-ofy);
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x-ofx,y-ofy);
                invalidate();
                return true;
            default:
                return false;
        }
    }

    public DrawCanvas(Context context) {
        super(context);
    }

    @Override
    protected void onDrawDestination(Canvas newCanvas, Paint paint) {
        for(int i = 0; i < paths.size(); i++) {
            newCanvas.drawPath(paths.get(i), paint);
        }
    }

    public Bitmap saveBitmap(String extra){
        Bitmap saveBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(saveBitmap);
        canvas.setBitmap(saveBitmap);
        this.draw(canvas);

        String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        String name = date + " Wynik - " + extra + ".jpg";

        InternalStorageManager.saveBitmapResults(saveBitmap, name);

        return saveBitmap;
    }
}
