package com.ardiarahma.education.activities.child;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ardiarahma.education.R;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.networks.PreferencesConfig;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    TextView nilai;
    ImageView home;
    Button repeat, discussion;

    Token auth = PreferencesConfig.getInstance(this).getToken();
    String token = "Bearer " + auth.getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        nilai = findViewById(R.id.nilai);
        home = findViewById(R.id.home);
        repeat = findViewById(R.id.repeat);
        discussion = findViewById(R.id.discussion);

        final Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        ArrayList<String> answersArray;
        nilai.setText(String.valueOf(score));

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ResultActivity.this, TaskActivity.class);
                startActivity(intent2);
            }
        });

        discussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(ResultActivity.this, DiscussionActivity.class);
                startActivity(intent3);
            }
        });


    }
}
