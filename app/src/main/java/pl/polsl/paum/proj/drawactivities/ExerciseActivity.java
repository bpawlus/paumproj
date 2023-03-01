package pl.polsl.paum.proj.drawactivities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.polsl.paum.proj.R;
import pl.polsl.paum.proj.canvases.ExerciseCanvas;

public class ExerciseActivity extends AppCompatActivity {
    private ExerciseCanvas exerciseCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.canvas);
        exerciseCanvas = new ExerciseCanvas(this);
        layout.addView(exerciseCanvas);

        if(!exerciseCanvas.hasTask()) {
            finishExercise();
        }

        addReturnButton();
        addNextButton();
        addEraseButton();
    }

    private void finishExercise()
    {
        Toast.makeText(getApplicationContext(), "Zrobiłeś wszystkie zadania! Gratulacje!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void addReturnButton() {
        ImageButton btn = (ImageButton)findViewById(R.id.buttonBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addEraseButton() {
        ImageButton btn = (ImageButton)findViewById(R.id.buttonErase);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exerciseCanvas.erasePaths();
            }
        });
    }

    private void addNextButton() {
        ImageButton btn = (ImageButton)findViewById(R.id.buttonNext);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exerciseCanvas.nextTask();
                exerciseCanvas.paths.clear();
                if(!exerciseCanvas.hasTask()) {
                    finishExercise();
                }
            }
        });
    }

}
