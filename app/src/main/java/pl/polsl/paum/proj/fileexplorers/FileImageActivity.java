package pl.polsl.paum.proj.fileexplorers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import pl.polsl.paum.proj.InternalStorageManager;
import pl.polsl.paum.proj.MainActivity;
import pl.polsl.paum.proj.R;
import pl.polsl.paum.proj.SvgFile;
import pl.polsl.paum.proj.drawactivities.DrawActivity;

public class FileImageActivity extends ListActivity {
    private List<String> items = null;
    private String root=InternalStorageManager.path+"/results/";
    private TextView myPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explorer_main);
        myPath = (TextView)findViewById(R.id.path);
        getDir();
    }

    protected void getDir()
    {
        myPath.setText("Ostatnie rezultaty");

        items = new ArrayList<String>();
        File f = new File(root);
        File[] files = f.listFiles();

        for(int i=0; i < files.length; i++)
        {
            File file = files[i];
            String name = file.getName();
            items.add(name);
        }

        Collections.sort(items, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s2.compareToIgnoreCase(s1);
            }
        });

        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, R.layout.explorer_row, items);
        setListAdapter(fileList);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String file = items.get(position);

        Intent intent = new Intent(FileImageActivity.this, FileImagePreviewActivity.class);
        Bundle b = new Bundle();
        b.putString("dir", root+file);
        intent.putExtras(b);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getDir();
    }
}
