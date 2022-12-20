package pl.polsl.paum.proj;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;
import java.io.FileOutputStream;

public class DrawActivity extends AppCompatActivity {
    public static int brushclr = Color.BLACK;
    public static Paint brush = new Paint();
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
                brush.setColor(Color.BLACK);
                brushclr = Color.BLACK;
            }
        });
    }

    private void addEraserButton() {
        Button btn = (Button)findViewById(R.id.buttonEraser);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brush.setColor(Color.WHITE);
                brushclr = Color.WHITE;
            }
        });
    }

    private void addExportButton() {
        Button btn = (Button)findViewById(R.id.buttonExport);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawCanvas.savebit();
            }
        });
    }
}
