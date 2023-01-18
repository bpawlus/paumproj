package pl.polsl.paum.proj;

import pl.polsl.paum.proj.InternalStorageManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import pl.polsl.paum.proj.drawactivities.DrawActivity;
import pl.polsl.paum.proj.drawactivities.ExerciseActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(
        new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
            for(Map.Entry<String, Boolean> entry : isGranted.entrySet()) {
                if (entry.getValue()) {
                    System.out.println(entry.getKey()+": ok");
                }
                else{
                    showExitDialog();
                    return;
                }
            }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InternalStorageManager.path = getFilesDir().getAbsolutePath();
        setContentView(R.layout.activity_main);

        ArrayList<String> toAdd = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        )
        {
            System.out.println(11);
        }
        else
        {
            toAdd.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            System.out.println(12);
        }
        else
        {
            toAdd.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        requestPermissionLauncher.launch(toAdd.toArray(new String[toAdd.size()]));

        //PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().clear().commit();
        InternalStorageManager.initResources();
        onTutorSetPassword("");

        addDrawButton();
        addExerciseButton();
        addResultsButton();
        addExitButton();
        addSettingsButton();
    }



    private void addDrawButton() {
        CardView btn = (CardView)findViewById(R.id.opt1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DrawActivity.class));
            }
        });
    }

    private void addExerciseButton() {
        CardView btn = (CardView)findViewById(R.id.opt2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ExerciseActivity.class));
            }
        });
    }

    private void addResultsButton() {
        CardView btn = (CardView)findViewById(R.id.opt3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ResultsActivity.class));
            }
        });
    }

    private void addExitButton() {
        CardView btn = (CardView)findViewById(R.id.opt4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }

    private void addSettingsButton() {
        ImageButton btn = (ImageButton)findViewById(R.id.buttonSettings);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginDialog();
            }
        });
    }



    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();

        View view = inflater.inflate(R.layout.view_login, null);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Zaloguj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText et = view.findViewById(R.id.tutorDialogPassword);
                        onTutorLogin(et.getText().toString());
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false);

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void onTutorLogin(String loginPassword) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String password = sharedPreferences.getString("password", "");

        if(password.equals(loginPassword)) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }
        else {
            Toast.makeText(getApplicationContext(), "Podano złe hasło nauczyciela!", Toast.LENGTH_SHORT).show();
        }
    }



    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();

        builder.setTitle("Aplikacja nie może działać poprawnie bez nadanych permisji")
                .setNeutralButton("Wyjdź", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onExit();
                    }
                })
                .setCancelable(false);

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void onExit() {
        finish();
        System.exit(0);
    }



    private void showPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();

        View view = inflater.inflate(R.layout.view_login, null);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Ustaw", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText et = view.findViewById(R.id.tutorDialogPassword);
                        onTutorSetPassword(et.getText().toString());
                    }
                })
                .setCancelable(false);

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void onTutorSetPassword(String setPassword) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String password = sharedPreferences.getString("password", "");

        if(password.isEmpty() && setPassword.isEmpty()) {
            showPasswordDialog();
        }
        else if(password.isEmpty() && !setPassword.isEmpty())
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("password", setPassword);
            editor.commit();
        }
    }
}