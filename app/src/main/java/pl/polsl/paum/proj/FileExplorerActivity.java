package pl.polsl.paum.proj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class FileExplorerActivity extends ListActivity {
    private List<String> item = null;
    private List<String> path = null;
    private String root="/storage/emulated/0";
    private TextView myPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explorer_main);
        myPath = (TextView)findViewById(R.id.path);
        getDir(root);
    }

    private void getDir(String dirPath)
    {
        myPath.setText("Ścieżka do zestawu zip: " + dirPath);

        item = new ArrayList<String>();
        path = new ArrayList<String>();
        File f = new File(dirPath);
        File[] files = f.listFiles();

        if(!dirPath.equals(root))
        {
            item.add(root);
            path.add(root);
            item.add("../");
            path.add(f.getParent());
        }

        for(int i=0; i < files.length; i++)
        {
            File file = files[i];
            String name = file.getName();
            int n = name.lastIndexOf('.');
            String extension = "";
            if (n > 0) {
                extension = name.substring(n+1);
            }


            if(file.isDirectory()) {
                path.add(file.getPath());
                item.add(name + "/");
            }
            else if(extension.equals("zip")) {
                path.add(file.getPath());
                item.add(file.getName());
            }
        }

        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, R.layout.explorer_row, item);
        setListAdapter(fileList);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        File file = new File(path.get(position));

        if (file.isDirectory())
        {
            if(file.canRead())
                getDir(path.get(position));
            else
            {
                new AlertDialog.Builder(this).setTitle("[" + file.getName() + "] nie mógł zostać odczytany!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        }
        else
        {
            new AlertDialog.Builder(this).setTitle("Dodać zestaw: [" + file.getName() + "]?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            InternalStorageManager.addZipContent(file.getAbsolutePath(), file.getName());
                        }
                    }).show();
        }

    }
}