package com.ardiarahma.education.activities.child;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.models.BanksoalShelves;
import com.ardiarahma.education.models.Task;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.models.responses.ResponseScore;
import com.ardiarahma.education.models.responses.ResponseTask;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskActivity extends AppCompatActivity {

    private ArrayList<Task> tasks;
    ArrayList<String> answersArray = new ArrayList<String>();
    TextView task_question, task_header, timer, count;
    RadioGroup choices_group;
    RadioButton choice_A, choice_B, choice_C, choice_D;
    Button next, previous;

    Context context;
    ProgressDialog loading;
    User user = PreferencesConfig.getInstance(this).getUser();
    Token auth = PreferencesConfig.getInstance(this).getToken();
    String token = "Bearer " + auth.getToken();

    int score;
    private int currentTaskId = 0;
    private boolean check;
    String task_answer;

    private static final long START_TIME_IN_MILLIS = 600000;
    private CountDownTimer mCountDown;
    private boolean nTimerRunning;
    private long mTimeLeftUntilFinished = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banksoal_test);

        String judul = PreferencesConfig.getInstance(this).getTaskData().getName();
        task_header = findViewById(R.id.task_header);
        task_header.setText(judul);

        timer = findViewById(R.id.time);
        task_question = findViewById(R.id.pertanyaan);
        choices_group = findViewById(R.id.rg_question);
        choice_A = findViewById(R.id.option_A);
        choice_B = findViewById(R.id.option_B);
        choice_C = findViewById(R.id.option_C);
        choice_D = findViewById(R.id.option_D);
        count = findViewById(R.id.count);
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
//                onBackPressed();
                Intent intent = new Intent(TaskActivity.this, BanksoalShelvesActivity.class);
                startActivity(intent);
            }
        });
        alertDialog.setPositiveButton("Ayo, dimulai!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                task();
                timer();
                dialog.dismiss();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public void timer(){
        mCountDown = new CountDownTimer(mTimeLeftUntilFinished, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftUntilFinished = millisUntilFinished;
                int minutes = (int) mTimeLeftUntilFinished / 1000 / 60;
                int seconds = (int) mTimeLeftUntilFinished / 1000 % 60;
                @SuppressLint("DefaultLocale") String formatted = String.format("%02d:%02d", minutes, seconds);

                timer.setText(formatted);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(TaskActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        }.start();
    }

    public void task(){

        loading = ProgressDialog.show(this, null, "Please wait...",true, false);

        int task_id = PreferencesConfig.getInstance(this).getTaskData().getTask_id();
        int classes = PreferencesConfig.getInstance(this).getTaskData().getClasses();

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
        final Task task = tasks.get(currentTaskId);
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
                    String ans = selectedRB.getText().toString();
                    score+=10;
                }

//                if (!choice_A.isChecked() || !choice_B.isChecked() || !choice_C.isChecked() || !choice_D.isChecked()){
//                    Toast.makeText(TaskActivity.this, "Pertanyaan wajib diisi!", Toast.LENGTH_SHORT).show();
//                }

                if (currentTaskId < tasks.size() - 1){

                    currentTaskId++;
                    showQuestion();
                    selectedRB.setChecked(true);
                    choices_group.clearCheck();
                    previous.setVisibility(View.VISIBLE);
                    count.setText(String.valueOf(currentTaskId +1));
                }else {
                    previous.setVisibility(View.INVISIBLE);
                    next.setText("Selesai");
                    next.setGravity(Gravity.CENTER);
                    Intent intent = new Intent(TaskActivity.this, ResultActivity.class);
                    intent.putExtra("score", score);
                    storescore();
                    startActivity(intent);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTaskId > 0){
                    currentTaskId--;
                    count.setText(String.valueOf(currentTaskId));
                    showQuestion();
                }
            }
        });
    }

    public void storescore(){
        int task_id = PreferencesConfig.getInstance(this).getTaskData().getTask_id();

        Call<ResponseScore> call = RetrofitClient
                .getInstance()
                .getApi()
                .store_score(token, task_id, score);
        call.enqueue(new Callback<ResponseScore>() {
            @Override
            public void onResponse(Call<ResponseScore> call, Response<ResponseScore> response) {
                loading.dismiss();
                ResponseScore responseScore = response.body();
                Log.d("TAG", "Response " + response.body());
                if (response.isSuccessful()){
                    if (responseScore.getStatus().equals("success")){
                        Log.i("debug", "onResponse : SUCCESSFUL");
                    }else {
                        Log.i("debug", "onResponse : FAILED");
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseScore> call, Throwable t) {

            }
        });
    }
}
