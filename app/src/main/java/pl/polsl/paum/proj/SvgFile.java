package pl.polsl.paum.proj;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SvgFile implements Comparable<SvgFile> {
    private String label;
    private String directory;
    private int amount;
    private String displayName;
    private String name;
    private SVG svg;

    public SvgFile(String label, String name)
    {
        this.name = name;
        this.label = label;
        displayName = "[" + label + "]: " + name;
        amount = 0;
        directory = InternalStorageManager.path+"/svgpacks/"+label+"/"+name;

        InputStream stream = null;
        try {
            stream = new FileInputStream(directory);
            svg = SVG.getFromInputStream(stream);
            stream.close();
        } catch (SVGParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeAmount(int newAmount)
    {
        amount = newAmount;
    }

    public String getDirectory() {
        return directory;
    }

    public int getAmount() {
        return amount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public SVG getSvg() {
        return svg;
    }

    @Override
    public int compareTo(SvgFile o) {
        int i = InternalStorageManager.allowed.indexOf(o.getLabel());
        int j = InternalStorageManager.allowed.indexOf(label);
        if(i<j)
            return 1;
        return -1;
    }
}
