package com.ardiarahma.education.activities.parent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ardiarahma.education.R;
import com.ardiarahma.education.models.Siswa;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.networks.PreferencesConfig;

import java.util.ArrayList;


public class ParentDetailsChildActivity extends AppCompatActivity {

    TextView childName, childUsername, childEmail, childSchool, childClass;
    ImageButton toolbar_details;

    User user = PreferencesConfig.getInstance(this).getUser();
    Token auth = PreferencesConfig.getInstance(this).getToken();
    String token = "Bearer " + auth.getToken();
    int user_id = user.getId();

    ArrayList<Siswa> siswas;

    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_child_details);

        //===================== BACK BUTTON ====================
        toolbar_details = findViewById(R.id.toolbar_details);
        toolbar_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //===================== DETAILS ======================
        childName = findViewById(R.id.namalengkap);
        childEmail = findViewById(R.id.emailSiswa);
        childUsername = findViewById(R.id.usernameSiswa);
        childSchool = findViewById(R.id.sekolahSiswa);
        childClass = findViewById(R.id.kelasSiswa);

        loading = ProgressDialog.show(this, null, "Mohon tunggu sebentar ...", true, false);

        Intent intent = getIntent();
        int id = intent.getIntExtra("childId", 0);
        String name = intent.getStringExtra("childName");
        String email = intent.getStringExtra("childEmail");
        String username = intent.getStringExtra("childUsername");
        String school = intent.getStringExtra("childSchool");
        int classes = intent.getIntExtra("childClass", 0);

        childName.setText(name);
        childEmail.setText(email);
        childUsername.setText(username);
        childSchool.setText(school);
        childClass.setText(String.valueOf(classes));

        loading.dismiss();

    }
}
