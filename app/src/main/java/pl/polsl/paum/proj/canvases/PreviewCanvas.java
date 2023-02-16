package pl.polsl.paum.proj.canvases;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;

public class PreviewCanvas extends BaseDrawCanvas {
    public PreviewCanvas(Context context) {
        super(context);
    }

    private void drawLine(Canvas canvas, Paint paint, float xs, float ys, float xe, float ye)
    {
        Path path = new Path();
        path.moveTo(xs,ys);
        path.lineTo(xe,ye);
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onDrawPen(Canvas newCanvas, Paint paint) {
        drawLine(newCanvas, paint, 2*width/3, height/3, width/3, 2*height/3);
    }

    @Override
    protected Paint setPaintToOverlay(Paint paint, int clr, float opacity)
    {
        paint = super.setPaintToOverlay(paint, clr, opacity);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(16f);
        return paint;
    }

    @Override
    protected void onDrawOverlay(Canvas canvas, Paint paint) {
        drawLine(canvas, paint, width/3, height/3, 2*width/3, 2*height/3);
    }
}
