package com.ardiarahma.education.activities.child;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.adapters.RV_BanksoalSoalAdapter;
import com.ardiarahma.education.models.Answers;
import com.ardiarahma.education.models.BanksoalSoal;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.models.responses.ResponseChoice;
import com.ardiarahma.education.models.responses.ResponseTask;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BanksoalTestActivity extends AppCompatActivity {

    private RecyclerView rv_soal;
    private RecyclerView.Adapter adapter;
    private ArrayList<BanksoalSoal> banksoalSoals;
    private ArrayList<Answers> answers;


    TextView soal_title, task_title;
    RadioGroup jawaban_group;
    RadioButton radioButton;
    Button next, previous;


    ProgressDialog loading;
    Token auth = PreferencesConfig.getInstance(this).getToken();
    String token = "Bearer " + auth.getToken();

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banksoal_test);

        Intent intent = getIntent();
        String judul = intent.getStringExtra("task_title");
        task_title = findViewById(R.id.title_soal);
        task_title.setText(judul);

        soal_title = findViewById(R.id.pertanyaan);
        jawaban_group = findViewById(R.id.rb_soal);

        next = findViewById(R.id.bNext);
        previous = findViewById(R.id.bPrevious);

    }

    @Override
    protected void onResume() {
        super.onResume();
        task();
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
                        banksoalSoals = responseTask.getBanksoalSoals();
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
                Toast.makeText(BanksoalTestActivity.this, "Kesalahan terjadi. Silakan coba beberapa saat lagi.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showQuestion(){
        for (int i = 0; i < 1; i++){
            soal_title.setText(banksoalSoals.get(i).getSoal());
            answers = banksoalSoals.get(i).getAnswers();
            final int soal_id = banksoalSoals.get(i).getId_soal();
            Log.i("debug", "onResponse : " + answers.size() );
            for (int j = 0; j <  answers.size(); j++){
                radioButton = new RadioButton(BanksoalTestActivity.this);
                radioButton.setText(answers.get(j).getChoice() + ". " + answers.get(j).getAnswer());
//                radioButton.setId(j);
                jawaban_group.addView(radioButton);

            }

            final int finalI = i;
            jawaban_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int checked = jawaban_group.getChildAt(finalI).getId();
                    RadioButton rbChecked = findViewById(checked);

//                    Toast.makeText(BanksoalTestActivity.this, rbChecked.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (radioButton.isChecked()){
//                        RadioButton rb_answer = findViewById(jawaban_group.getCheckedRadioButtonId());
//                        jawaban_group.check(0);
//                        if (answers.get(i).getRight_choice() == 1){
//
//                        }
//                    }

                    if (counter > 0){
                        soal_title.setText(banksoalSoals.get(counter).getSoal());
                        counter++;
                    }
                    RadioButton jawaban_user = findViewById(jawaban_group.getCheckedRadioButtonId());

                }
            });
        }
    }
}
