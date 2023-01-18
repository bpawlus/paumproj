package pl.polsl.paum.proj.drawactivities;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.Button;

import pl.polsl.paum.proj.R;
import pl.polsl.paum.proj.canvases.PreviewCanvas;

public class PreviewActivity extends AppCompatActivity {
    private PreviewCanvas previewCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.canvas);
        previewCanvas = new PreviewCanvas(this);
        layout.addView(previewCanvas);

        addReturnButton();
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
}