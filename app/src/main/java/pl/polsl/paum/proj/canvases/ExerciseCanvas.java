package pl.polsl.paum.proj.canvases;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
        erasePaths();
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        bitmapTasks = new ArrayList<>();

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
    protected Paint setPaintToOverlay(Paint paint, int clr, float opacity)
    {
        paint = super.setPaintToOverlay(paint, clr, opacity);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        return paint;
    }

    @Override
    protected void onDrawOverlay(Canvas canvas, Paint paint) {
        if(hasTask()) {
            Bitmap helperBitmap = generateBitmap(getContext(), bitmapTasks.get(taskId).getSvg());

            Bitmap compositeBitmap = Bitmap.createBitmap(imgwidth, imgheight, Bitmap.Config.ARGB_8888);
            Canvas newCanvas = new Canvas(compositeBitmap);

            newCanvas.drawBitmap(helperBitmap, 0, 0, paint); //DEST
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            newCanvas.drawRect(0, 0, width, height, paint); //SRC

            canvas.drawBitmap(compositeBitmap, ofx, ofy, null);
        }
    }
}
