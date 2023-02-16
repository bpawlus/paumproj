package pl.polsl.paum.proj.fileexplorers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import pl.polsl.paum.proj.InternalStorageManager;
import pl.polsl.paum.proj.R;
import pl.polsl.paum.proj.SvgFile;


public class FileManageActivity extends ListActivity {
    protected List<SvgFile> items = null;
    protected TextView myPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explorer_main);
        myPath = (TextView)findViewById(R.id.path);
        getDir();
    }

    protected void getDir()
    {
        myPath.setText("Dodaj wzorce do ćwiczenia");

        items = new ArrayList<SvgFile>();

        for(Map.Entry<String, SvgFile> entry : InternalStorageManager.taskAmounts.entrySet())
        {
            items.add(entry.getValue());
        }
        Collections.sort(items);

        ArrayAdapter<SvgFile> fileList = new SvgFileAdapter(this, R.layout.manager_row, items.toArray(new SvgFile[items.size()]));
        setListAdapter(fileList);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        SvgFile file = items.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(FileManageActivity.this);
        LayoutInflater inflater = FileManageActivity.this.getLayoutInflater();

        View view = inflater.inflate(R.layout.view_editsvg, null);

        TextView textView = view.findViewById(R.id.textViewFile);
        textView.setText(file.getName());

        TextView textViewSeekBar = view.findViewById(R.id.textViewSeekBarValue);
        textViewSeekBar.setText(Integer.toString(file.getAmount()));

        SeekBar seekBar = view.findViewById(R.id.seekBar);
        seekBar.setProgress(file.getAmount());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int val = seekBar.getProgress();
                textViewSeekBar.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        builder.setView(view)
                .setPositiveButton("Akceptuj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        InternalStorageManager.changeValue(file.getDirectory(), seekBar.getProgress());
                        getDir();
                    }
                })
                .setNegativeButton("Usuń", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        InternalStorageManager.removeValue(file.getDirectory());
                        getDir();
                    }
                });

        Dialog dialog = builder.create();
        dialog.show();
    }
}