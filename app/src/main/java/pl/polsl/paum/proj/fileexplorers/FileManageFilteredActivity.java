package pl.polsl.paum.proj.fileexplorers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import pl.polsl.paum.proj.InternalStorageManager;
import pl.polsl.paum.proj.R;
import pl.polsl.paum.proj.SvgFile;


public class FileManageFilteredActivity extends FileManageActivity {
    @Override
    protected void getDir()
    {
        myPath.setText("Dodane wzorce do ćwiczenia");

        items = new ArrayList<SvgFile>();

        for(Map.Entry<String, SvgFile> entry : InternalStorageManager.taskAmounts.entrySet())
        {
            if(entry.getValue().getAmount() != 0) {
                items.add(entry.getValue());
            }
        }
        Collections.sort(items);

        ArrayAdapter<SvgFile> fileList = new SvgFileAdapter(this, R.layout.manager_row, items.toArray(new SvgFile[items.size()]));
        setListAdapter(fileList);
    }
}