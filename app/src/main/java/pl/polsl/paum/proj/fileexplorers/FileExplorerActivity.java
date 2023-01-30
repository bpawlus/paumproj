package pl.polsl.paum.proj.fileexplorers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import pl.polsl.paum.proj.InternalStorageManager;
import pl.polsl.paum.proj.R;


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
        myPath.setText("Ścieżka do znaku: " + dirPath);

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
            else if(extension.equals("svg")) {
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
                new AlertDialog.Builder(this).setTitle(file.getName() + " nie mógł zostać odczytany!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(FileExplorerActivity.this);
            LayoutInflater inflater = FileExplorerActivity.this.getLayoutInflater();

            View view = inflater.inflate(R.layout.view_import, null);

            TextView textView = view.findViewById(R.id.textViewFile);
            textView.setText(file.getName());

            Spinner spinner = view.findViewById(R.id.spinnerLabel);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, InternalStorageManager.allowed);
            spinner.setAdapter(adapter);

            builder.setView(view)
                    .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            boolean result = InternalStorageManager.addSvgContent(file.getAbsolutePath(), file.getName(), spinner.getSelectedItem().toString());
                            if(result)
                            {
                                InternalStorageManager.updateTaskAmounts();
                                Toast.makeText(getApplicationContext(), "Poprawnie dodano: "+file.getName(), Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Nie udało się dodać: "+file.getName(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            Dialog dialog = builder.create();
            dialog.show();
        }

    }
}