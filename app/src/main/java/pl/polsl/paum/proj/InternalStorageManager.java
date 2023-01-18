package pl.polsl.paum.proj;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class InternalStorageManager {
    public static String path;
    public static ArrayList<String> allowed = new ArrayList<String>();

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

        f = new File(path + "/svgpacks/other");
        allowed.add("other");
        if(!f.exists())
        {
            f.mkdir();
        }
    }

    public static void addZipContent(String dir, String name)
    {
        InputStream source = null;
        try {
            source = new FileInputStream(dir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        int dodano = 0;
        byte[] buffer = new byte[1024];

        try (FileInputStream fis = new FileInputStream(dir);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ZipInputStream zis = new ZipInputStream(bis)) {

            ZipEntry ze;

            while ((ze = zis.getNextEntry()) != null) {
                System.out.println("File: " + ze.getName()+" Size: " + ze.getSize());

                Pattern pattern = Pattern.compile("(\\w+)/(\\w+\\.xml)");
                Matcher matcher = pattern.matcher(ze.getName());
                int cnt = matcher.groupCount();

                if(matcher.find() && cnt == 2) {
                    String folder = matcher.group(1);
                    String filename = matcher.group(2);

                    if(allowed.contains(folder)) {
                        OutputStream dest = new FileOutputStream(path+"/svgpacks/"+folder+"/"+filename);
                        int length;

                        while((length = zis.read(buffer)) >= 0) {
                            dest.write(buffer, 0, length);
                            dest.flush();
                        }
                        dest.close();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File[] files = new File(path).listFiles();
        return;
    }
}

