package pl.polsl.paum.proj.canvases;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public class PreviewCanvas extends BaseDrawCanvas {
    public PreviewCanvas(Context context) {
        super(context);
    }

    @Override
    protected void onDrawDestination(Canvas newCanvas, Paint paint) {
        Path path = new Path();
        path.moveTo(width/3,height/3);
        path.lineTo(2*width/3,2*height/3);
        newCanvas.drawPath(path, paint);
    }

    @Override
    protected void onDrawSource(Canvas newCanvas, Paint paint) {
        Bitmap saveBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        saveBitmap.eraseColor(Color.WHITE);
        newCanvas.drawBitmap(saveBitmap, 0, 0, paint);
    }
}
