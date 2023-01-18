package pl.polsl.paum.proj.canvases;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Environment;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.polsl.paum.proj.R;

public class ExerciseCanvas extends DrawCanvas {
    private Bitmap svgBitmapSrc;

    public ExerciseCanvas(Context context) {
        super(context);
    }

    private Bitmap getBitmap(Context context, int id) {
        Drawable drawable = ContextCompat.getDrawable(context, id);
        if (drawable instanceof BitmapDrawable) {
            Bitmap newBitmap = BitmapFactory.decodeResource(context.getResources(), id);
            imgwidth = newBitmap.getWidth();
            imgheight = newBitmap.getHeight();
            return newBitmap;
        } else if (drawable instanceof VectorDrawable) {
            Bitmap newBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBitmap);
            imgwidth = 600;
            imgheight = 800;
            drawable.setBounds(0, 0, imgwidth, imgheight);
            drawable.draw(canvas);
            return newBitmap;
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        svgBitmapSrc = getBitmap(context, R.drawable.test_lambda);
    }

    @Override
    protected void updateSize() {
        width = getWidth();
        height = getHeight();
        ofx = (width-imgwidth)/2;
        ofy = (height-imgheight)/2;
    }

    @Override
    protected void onDrawSource(Canvas newCanvas, Paint paint) {
        newCanvas.drawBitmap(svgBitmapSrc, 0, 0, paint);
    }

    public Bitmap saveBitmap(){
        Bitmap saveBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(saveBitmap);
        canvas.setBitmap(saveBitmap);
        this.draw(canvas);

        String pattern = "yyyyMMddmmss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        String extStorageState = Environment.getExternalStorageDirectory().getAbsolutePath();
        String path = extStorageState + "/" + Environment.DIRECTORY_PICTURES + "/paum"+date+"out.jpg";
        File file = new File(path);

        try {
            saveBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return saveBitmap;
    }
}
