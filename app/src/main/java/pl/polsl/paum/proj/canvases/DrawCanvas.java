package pl.polsl.paum.proj.canvases;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

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
}
