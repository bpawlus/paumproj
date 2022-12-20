package pl.polsl.paum.proj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addDrawButton();
        addExerciseButton();
        addResultsButton();
        addExitButton();
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
}