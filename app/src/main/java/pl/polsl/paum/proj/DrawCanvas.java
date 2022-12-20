package pl.polsl.paum.proj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import static pl.polsl.paum.proj.DrawActivity.brush;
import static pl.polsl.paum.proj.DrawActivity.brushclr;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DrawCanvas extends View {
    private Path path;
    public static List<Path> paths = new ArrayList<>();
    public static List<Integer> colors = new ArrayList<>();

    public ViewGroup.LayoutParams params;

    public DrawCanvas(Context context) {
        super(context);
        init(context);
    }

    public DrawCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        brush.setAntiAlias(true);
        brush.setColor(Color.BLACK);
        brushclr = Color.BLACK;
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeCap(Paint.Cap.ROUND);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(32f);

        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();

                paths.add(path);
                colors.add(brushclr);

                path.moveTo(x,y);
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x,y);
                invalidate();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i = 0; i < paths.size(); i++) {
            brush.setColor(colors.get(i));
            canvas.drawPath(paths.get(i), brush);
            invalidate();
        }
    }


    public Bitmap savebit(){
        Bitmap saveBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(saveBitmap);
        canvas.setBitmap(saveBitmap);
        this.draw(canvas);

        String extStorageState = Environment.getExternalStorageState();
        String path = Environment.getExternalStorageDirectory() + "/output.jpg";
        File file = new File(path);

        try {
            saveBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveBitmap;
    }
}
