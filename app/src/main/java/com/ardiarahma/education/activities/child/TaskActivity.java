package com.ardiarahma.education.activities.child;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.models.Task;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.responses.ResponseTask;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskActivity extends AppCompatActivity {

    Task question;
    private ArrayList<Task> tasks;
    TextView task_question, task_header;
    RadioGroup choices_group;
    RadioButton choice_A, choice_B, choice_C, choice_D;
    Button next, previous;

    ProgressDialog loading;
    Token auth = PreferencesConfig.getInstance(this).getToken();
    String token = "Bearer " + auth.getToken();

    int score;
    private int currentTaskId = 0;
    String task_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banksoal_test);

        final Intent intent = getIntent();
        String judul = intent.getStringExtra("task_title");
        task_header = findViewById(R.id.task_header);
        task_header.setText(judul);


        task_question = findViewById(R.id.pertanyaan);
        choices_group = findViewById(R.id.rg_question);
        choice_A = findViewById(R.id.option_A);
        choice_B = findViewById(R.id.option_B);
        choice_C = findViewById(R.id.option_C);
        choice_D = findViewById(R.id.option_D);
        next = findViewById(R.id.bNext);
        previous = findViewById(R.id.bPrevious);

    }

    @Override
    protected void onResume() {
        super.onResume();
        alert_start();
    }

    public void alert_start(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Mulai?");
        alertDialog.setNegativeButton("Jangan dulu, saya belum siap!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
//                Intent intent = new Intent(TaskActivity.this, BanksoalShelvesActivity.class);
//                startActivity(intent);
            }
        });
        alertDialog.setPositiveButton("Ayo, dimulai!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                task();
                dialog.dismiss();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public void task(){

        loading = ProgressDialog.show(this, null, "Please wait...",true, false);

        Intent intent = getIntent();
        final int task_id = intent.getIntExtra("task_id", 0);
        int classes = intent.getIntExtra("task_class", 0);

        Call<ResponseTask> call = RetrofitClient
                .getInstance()
                .getApi()
                .taskmaster_task(token, task_id, classes);

        call.enqueue(new Callback<ResponseTask>() {
            @Override
            public void onResponse(Call<ResponseTask> call, Response<ResponseTask> response) {
                loading.dismiss();
                ResponseTask responseTask = response.body();
                Log.d("TAG", "Response " + response.body());
                if (response.isSuccessful()){
                    if (responseTask.getStatus().equals("success")){
                        Log.i("debug", "onResponse : SUCCESSFUL");
                        tasks = responseTask.getTasks();
                        showQuestion();
                    }else {
                        Log.i("debug", "onResponse : FAILED");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseTask> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                Toast.makeText(TaskActivity.this, "Kesalahan terjadi.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showQuestion(){
        Task task = tasks.get(currentTaskId);
        task_question.setText(task.getSoal());
        choice_A.setText(task.getOption_A());
        choice_B.setText(task.getOption_B());
        choice_C.setText(task.getOption_C());
        choice_D.setText(task.getOption_D());
        task_answer = task.getJawaban();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = choices_group.getCheckedRadioButtonId();
                RadioButton selectedRB = findViewById(selectedId);
                if (selectedRB.getText().toString().equals(task_answer)){
                    score+=10;
                }

                if (currentTaskId < tasks.size() - 1){
                    currentTaskId++;
                    showQuestion();
                    selectedRB.setChecked(false);
                }else {
                    Intent intent1 = new Intent(TaskActivity.this, ResultActivity.class);
                    startActivity(intent1);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTaskId > 0){
                    currentTaskId--;
                    showQuestion();
                }
            }
        });
    }
}
