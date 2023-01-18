package pl.polsl.paum.proj.drawactivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import pl.polsl.paum.proj.R;
import pl.polsl.paum.proj.canvases.DrawCanvas;
import pl.polsl.paum.proj.canvases.ExerciseCanvas;
import pl.polsl.paum.proj.canvases.PreviewCanvas;

public class DrawActivity extends AppCompatActivity {
    private DrawCanvas drawCanvas;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.canvas);
        drawCanvas = new DrawCanvas(this);
        layout.addView(drawCanvas);

        addReturnButton();
        addPencilButton();
        addEraserButton();
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

    private void addPencilButton() {
        Button btn = (Button)findViewById(R.id.buttonPencil);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void addEraserButton() {
        Button btn = (Button)findViewById(R.id.buttonEraser);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void addExportButton() {
        Button btn = (Button)findViewById(R.id.buttonExport);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}