package pl.polsl.paum.proj.canvases;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.PreferenceManager;

public abstract class BaseDrawCanvas extends View {
    private ViewGroup.LayoutParams params;

    protected float ofx;
    protected float ofy;
    protected int width;
    protected int height;
    protected int imgwidth;
    protected int imgheight;

    protected int backgroundColor;
    protected int drawColor;
    protected int drawSize;

    protected float svgSize;
    protected float svgOpacity;
    protected int svgColor;

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

        svgColor = sharedPreferences.getInt("svgColor", Color.BLACK);
        svgSize = sharedPreferences.getInt("svgSize", 75);
        svgSize /= 100;
        svgOpacity = sharedPreferences.getInt("svgOpacity", 60);
        svgOpacity *= 2.55;
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

    protected Paint setPaintToOverlay(Paint paint, int clr, float opacity)
    {
        paint.setColor(svgColor);
        paint.setAlpha((int) svgOpacity);
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

    protected abstract void onDrawPen(Canvas newCanvas, Paint paint);
    protected abstract void onDrawOverlay(Canvas canvas, Paint paint);

    @Override
    protected void onDraw(Canvas canvas) {
        Paint penPaint = setPaintToLines(new Paint(), drawColor, (float)drawSize);
        Paint fillPaint = setPaintToFill(new Paint(), backgroundColor);
        Paint paintOverlay = setPaintToOverlay(new Paint(), svgColor, svgOpacity);

        canvas.drawARGB(225, 225, 225, 255);
        canvas.drawRect(0, 0, width, height, fillPaint);

        onDrawPen(canvas, penPaint);
        onDrawOverlay(canvas, paintOverlay);
    }
}
