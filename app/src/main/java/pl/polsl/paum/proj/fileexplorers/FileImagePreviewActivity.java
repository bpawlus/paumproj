package pl.polsl.paum.proj.fileexplorers;

import static pl.polsl.paum.proj.InternalStorageManager.path;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import pl.polsl.paum.proj.InternalStorageManager;
import pl.polsl.paum.proj.R;

public class FileImagePreviewActivity extends AppCompatActivity {
    private String dir = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            dir = b.getString("dir");
        }

        setContentView(R.layout.activity_image_preview);
        ImageView imageView = (ImageView)findViewById(R.id.imagePreview);
        Bitmap bitmap = BitmapFactory.decodeFile(dir);
        imageView.setImageBitmap(bitmap);

        addReturnButton();
        addDeleteButton();
    }

    private void addReturnButton() {
        ImageButton btn = (ImageButton)findViewById(R.id.buttonBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private void addDeleteButton() {
        ImageButton btn = (ImageButton)findViewById(R.id.buttonDelete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InternalStorageManager.removeImage(dir);
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}
