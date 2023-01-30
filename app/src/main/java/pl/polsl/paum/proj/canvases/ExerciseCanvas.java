package pl.polsl.paum.proj.canvases;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Environment;

import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import com.caverock.androidsvg.SVG;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import pl.polsl.paum.proj.InternalStorageManager;
import pl.polsl.paum.proj.R;
import pl.polsl.paum.proj.SvgFile;

public class ExerciseCanvas extends DrawCanvas {
    private ArrayList<SvgFile> bitmapTasks;
    private int taskId = 0;

    protected float svgSize;
    protected float svgOpacity;

    public ExerciseCanvas(Context context) {
        super(context);
    }

    private Bitmap generateBitmap(Context context, SVG svg) {
        float preW = bitmapTasks.get(taskId).getSvg().getDocumentWidth();
        float preH = bitmapTasks.get(taskId).getSvg().getDocumentHeight();

        Bitmap myBitmap = Bitmap.createBitmap((int)Math.floor(preW),
                (int)Math.floor(preH),
                Bitmap.Config.ARGB_8888);
        Canvas myCanvas = new Canvas(myBitmap);
        myCanvas.drawARGB(0,255, 255, 255);
        svg.renderToCanvas(myCanvas);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(myBitmap, imgwidth, imgheight, false);
        myBitmap.recycle();

        return resizedBitmap;
    }

    public boolean hasTask()
    {
        return bitmapTasks.size() > taskId;
    }

    public void nextTask()
    {
        saveBitmap(bitmapTasks.get(taskId).getLabel());
        taskId++;
        invalidate();
    }

    @Override
    protected void init(Context context) {
        super.init(context);

        bitmapTasks = new ArrayList<>();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        svgSize = sharedPreferences.getInt("svgSize", 75);
        svgSize /= 100;
        svgOpacity = sharedPreferences.getInt("svgOpacity", 60);
        svgOpacity *= 2.55;

        for(Map.Entry<String, SvgFile> entry : InternalStorageManager.taskAmounts.entrySet())
        {
            for(int i = 0 ; i < entry.getValue().getAmount(); i++) {
                bitmapTasks.add(entry.getValue());
            }
        }

        Collections.shuffle(bitmapTasks);
    }

    @Override
    protected void updateSize() {
        super.updateSize();
        if(hasTask()) {
            float preW = bitmapTasks.get(taskId).getSvg().getDocumentWidth();
            float preH = bitmapTasks.get(taskId).getSvg().getDocumentHeight();
            float scale = preH / preW;

            float overflowW = preW / width; //2
            float overflowH = preH / height; //3

            if (overflowW < overflowH) {
                preW /= overflowH;
                preH /= overflowH;
            } else {
                preW /= overflowW;
                preH /= overflowW;
            }

            preW *= svgSize;
            preH *= svgSize;

            imgwidth = (int) Math.ceil(preW);
            imgheight = (int) Math.ceil(preH);

            ofx = (width-imgwidth)/2;
            ofy = (height-imgheight)/2;
        }
    }

    @Override
    protected void onDrawSource(Canvas newCanvas, Paint paint) {
        if(hasTask()) {
            newCanvas.drawBitmap(generateBitmap(getContext(), bitmapTasks.get(taskId).getSvg()), 0, 0, paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(hasTask()) {
            Paint paint = new Paint();
            paint.setAlpha((int) svgOpacity);
            Bitmap helperBitmap = generateBitmap(getContext(), bitmapTasks.get(taskId).getSvg());
            canvas.drawBitmap(helperBitmap, ofx, ofy, paint);
        }
    }
}
