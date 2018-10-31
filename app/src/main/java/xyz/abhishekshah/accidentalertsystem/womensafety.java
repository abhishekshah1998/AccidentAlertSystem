package xyz.abhishekshah.accidentalertsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class womensafety extends AppCompatActivity {

    private Button yes_button;
    private Button no_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_womensafety);

        yes_button = findViewById(R.id.yes_button);
        no_button = findViewById(R.id.no_button);

        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent=new Intent(getApplicationContext(),MainActivity.class);

                startActivity(startIntent);
            }
        });

        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }
}
