package pl.polsl.paum.proj;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InternalStorageManager {
    public static String path;
    public static ArrayList<String> allowed = new ArrayList<>();
    public static Map<String, SvgFile> taskAmounts = new HashMap<>();

    public static void initResources() {
        File[] files = new File(path).listFiles();

        File f = new File(path + "/svgpacks");
        if(!f.exists())
        {
            f.mkdir();
        }

        char c;
        for(c = 'a'; c <= 'z'; c++)
        {
            f = new File(path + "/svgpacks/" + c);
            allowed.add(String.valueOf(c));
            if(!f.exists())
            {
                f.mkdir();
            }
        }

        for(c = '0'; c <= '9'; c++)
        {
            f = new File(path + "/svgpacks/" + c);
            allowed.add(String.valueOf(c));
            if(!f.exists())
            {
                f.mkdir();
            }
        }

        f = new File(path + "/svgpacks/inne");
        allowed.add("inne");
        if(!f.exists())
        {
            f.mkdir();
        }

        for(c = '0'; c <= '9'; c++)
        {
            f = new File(path + "/results");
            if(!f.exists())
            {
                f.mkdir();
            }
        }

        updateTaskAmounts();
    }

    public static void updateTaskAmounts() {
        Map<String, List<String>> content = getContent();
        for(Map.Entry<String, List<String>> entry : content.entrySet()) {
            for(String name : entry.getValue()) {
                SvgFile svg = new SvgFile(entry.getKey(), name);
                String dir = svg.getDirectory();
                if(!taskAmounts.containsKey(dir)) {
                    taskAmounts.put(dir, svg);
                }
            }
        }
    }

    public static Map<String, List<String>> getContent() {
        Map<String, List<String>> toRet = new HashMap<>();
        for(String label : allowed)
        {
            File[] files = new File(path+"/svgpacks/"+label).listFiles();
            if(files.length != 0)
            {
                List<String> fileNames = new ArrayList<>();
                for(File file : files) {
                    fileNames.add(file.getName());
                }
                toRet.put(label, fileNames);
            }
        }
        return toRet;
    }

    public static void saveBitmapResults(Bitmap bitmap, String name)
    {
        String res = path + "/results/" + name;
        File file = new File(res);
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean addSvgContent(String dir, String name, String label) {
        InputStream source = null;
        try {
            source = new FileInputStream(dir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        OutputStream dest;
        try {
            dest = new FileOutputStream(path+"/svgpacks/"+label+"/"+name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        byte[] buffer = new byte[1024];
        int read;
        try {
            while ((read = source.read(buffer)) != -1) {
                dest.write(buffer, 0, read);
            }
            source.close();
            source = null;

            dest.flush();
            dest.close();
            dest = null;
        }
        catch(IOException e) {
            e.printStackTrace();
            return false;
        }

        File[] files = new File(path).listFiles();
        return true;
    }

    public static void changeValue(String dir, int progress) {
        SvgFile svgFile = taskAmounts.get(dir);
        if(svgFile != null)
        {
            svgFile.changeAmount(progress);
        }
    }

    public static void removeValue(String dir) {
        SvgFile svgFile = taskAmounts.get(dir);
        if(svgFile != null)
        {
            taskAmounts.remove(dir);
            File file = new File(dir);
            if(file.exists())
            {
                file.delete();
            }
        }
    }

    public static void removeImage(String dir) {
        File file = new File(dir);
        if(file.exists())
        {
            file.delete();
        }
    }
}

