package pl.polsl.paum.proj.canvases;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

public abstract class BaseDrawCanvas extends View {
    private ViewGroup.LayoutParams params;

    protected float ofx;
    protected float ofy;
    protected int width;
    protected int height;
    protected int imgwidth;
    protected int imgheight;

    private int backgroundColor;
    private int drawColor;
    private int drawSize;

    public BaseDrawCanvas(Context context) {
        super(context);
        init(context);
    }

    protected void init(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        backgroundColor = sharedPreferences.getInt("backgroundColor", Color.WHITE);
        drawColor = sharedPreferences.getInt("drawColor", Color.BLACK);
        drawSize = sharedPreferences.getInt("drawSize", 32);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        updateSize();
    }

    protected void updateSize()
    {
        width = getWidth();
        height = getHeight();
        imgwidth = getWidth();
        imgheight = getHeight();
        ofx = (width-imgwidth)/2;
        ofy = (height-imgheight)/2;
    }

    protected Paint setPaintToFill(Paint paint, int clr)
    {
        paint.setColor(clr);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        return paint;
    }

    protected Paint setPaintToLines(Paint paint, int clr, float stroke)
    {
        paint.setAntiAlias(true);
        paint.setColor(clr);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(stroke);
        return paint;
    }

    protected abstract void onDrawDestination(Canvas newCanvas, Paint paint);
    protected abstract void onDrawSource(Canvas newCanvas, Paint paint);

    @Override
    protected void onDraw(Canvas canvas) {
        Paint penPaint = setPaintToLines(new Paint(), drawColor, (float)drawSize);
        Paint fillPaint = setPaintToFill(new Paint(), backgroundColor);

        canvas.drawARGB(225, 225, 225, 255);
        canvas.drawRect(0, 0, width, height, fillPaint);

        Bitmap compositeBitmap = Bitmap.createBitmap(imgwidth, imgheight, Bitmap.Config.ARGB_8888);
        Canvas newCanvas = new Canvas(compositeBitmap);

        onDrawDestination(newCanvas, penPaint);

        penPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        onDrawSource(newCanvas, penPaint);

        canvas.drawBitmap(compositeBitmap, ofx, ofy, null);
    }
}
