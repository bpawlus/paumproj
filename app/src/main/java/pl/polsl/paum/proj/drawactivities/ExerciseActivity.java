package pl.polsl.paum.proj.drawactivities;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

        addReturnButton();
        addExportButton();
    }

    private void addReturnButton() {
        Button btn = (Button)findViewById(R.id.buttonBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addExportButton() {
        Button btn = (Button)findViewById(R.id.buttonExport);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exerciseCanvas.saveBitmap();
            }
        });
    }
}
