package pl.polsl.paum.proj.fileexplorers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.caverock.androidsvg.RenderOptions;
import com.caverock.androidsvg.SVG;

import java.io.File;

import pl.polsl.paum.proj.R;
import pl.polsl.paum.proj.SvgFile;

public class SvgFileAdapter extends ArrayAdapter<SvgFile> {
    private Context context;
    private int resourceLayout;

    public SvgFileAdapter(@NonNull Context context, int resource, @NonNull SvgFile[] objects) {
        super(context, resource, objects);
        this.resourceLayout = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SvgFile item = getItem(position);

        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            convertView = vi.inflate(resourceLayout, null);
        }

        if (item != null) {
            TextView ttname = (TextView) convertView.findViewById(R.id.textViewName);
            TextView ttamount = (TextView) convertView.findViewById(R.id.textViewAmount);
            TextView ttlabel = (TextView) convertView.findViewById(R.id.textViewLabel);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

            ttname.setText("Nazwa: " + item.getName());
            ttamount.setText("Ilość: " + Integer.toString(item.getAmount()));
            ttlabel.setText("Znak: " + item.getLabel());

            SVG svg = item.getSvg();
            Bitmap myBitmap = Bitmap.createBitmap((int) Math.ceil(svg.getDocumentWidth()),
                    (int) Math.ceil(svg.getDocumentHeight()),
                    Bitmap.Config.ARGB_8888);
            Canvas myCanvas = new Canvas(myBitmap);
            myCanvas.drawRGB(255, 255, 255);
            svg.renderToCanvas(myCanvas);
            imageView.setImageBitmap(myBitmap);
        }

        return convertView;
    }
}
