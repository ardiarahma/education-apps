package com.ardiarahma.education.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.activities.parent.ParentDetailsChildActivity;
import com.ardiarahma.education.activities.parent.ParentEditChildActivity;
import com.ardiarahma.education.activities.parent.ResultActivitiesReportActivity;
import com.ardiarahma.education.activities.parent.ResultStudiesReportActivity;
import com.ardiarahma.education.models.Siswa;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.responses.ResponseDelete;
import com.ardiarahma.education.networks.PreferencesConfig;
import com.ardiarahma.education.networks.RetrofitClient;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Windows 10 on 2/9/2019.
 */

public class SiswaAdapter extends RecyclerView.Adapter<SiswaAdapter.ViewHolder> {

    private List<Siswa> siswas;
    Context context;

    public SiswaAdapter(Context context, List<Siswa> siswas) {
        this.context = context;
        this.siswas = siswas;
    }

    @Override
    public SiswaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anak, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SiswaAdapter.ViewHolder holder, final int position) {
        holder.namaanak.setText(siswas.get(position).getName());
        holder.user_id = siswas.get(position).getUser_id();
        holder.id = siswas.get(position).getId();
        holder.email = siswas.get(position).getEmail();
        holder.sekolah = siswas.get(position).getSchool_name();
        holder.kelas = siswas.get(position).getClasses();
        holder.nama = siswas.get(position).getName();
        holder.username = siswas.get(position).getUsername();
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ParentEditChildActivity.class);
                intent.putExtra("childId", holder.id);
                intent.putExtra("user_id", holder.user_id);
                intent.putExtra("childName", holder.nama);
                intent.putExtra("childEmail", holder.email);
                intent.putExtra("childUsername", holder.username);
                intent.putExtra("childSchool", holder.sekolah);
                intent.putExtra("childClass", holder.kelas);
                context.startActivity(intent);
            }
        });
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ParentDetailsChildActivity.class);
                intent.putExtra("childId", holder.id);
                intent.putExtra("user_id", holder.user_id);
                intent.putExtra("childName", holder.nama);
                intent.putExtra("childEmail", holder.email);
                intent.putExtra("childUsername", holder.username);
                intent.putExtra("childSchool", holder.sekolah);
                intent.putExtra("childClass", holder.kelas);
                context.startActivity(intent);
            }
        });

        holder.log_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResultActivitiesReportActivity.class);
                intent.putExtra("childId", holder.id);
                intent.putExtra("childName", holder.nama);
                context.startActivity(intent);
            }
        });

        holder.log_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResultStudiesReportActivity.class);
                intent.putExtra("childId", holder.id);
                intent.putExtra("childName", holder.nama);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return siswas.size();
    }


    public void delete(int position){
        siswas.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        int user_id;
        int id;
        int kelas;
        String email, sekolah, nama, username;
        private TextView namaanak;
        private ImageButton edit, delete;
        private CardView details, log_act, log_study;

        Token auth = PreferencesConfig.getInstance(context).getToken();
        final String token = "Bearer " + auth.getToken();

        public ViewHolder(final View view){
            super(view);
            namaanak = itemView.findViewById(R.id.namalengkapanak);
            edit = itemView.findViewById(R.id.edit);
            details = itemView.findViewById(R.id.item_anak);
            delete = itemView.findViewById(R.id.delete);
            log_act = itemView.findViewById(R.id.b_log_act);
            log_study = itemView.findViewById(R.id.b_log_study);

            delete.setOnClickListener(this);

        }

        public void onClick(View v){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("Konfirmasi");
            alertDialog.setMessage("Anda yakin ingin menghapus akun?");
            alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    retrofit2.Call<ResponseDelete> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .deleteanak(token, id);
                    call.enqueue(new Callback<ResponseDelete>() {
                        @Override
                        public void onResponse(retrofit2.Call<ResponseDelete> call, Response<ResponseDelete> response) {
                            ResponseDelete responseDelete = response.body();
                            Log.d("TAG", "Response " + response.body());
                            if (response.isSuccessful()){
                                if (responseDelete.getStatus().equals("success")){
                                    Log.i("debug", "Response success");
                                    delete(getAdapterPosition());
                                    Toast.makeText(context, responseDelete.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.i("debug", "FAILED");
                            }
                        }
                        @Override
                        public void onFailure(retrofit2.Call<ResponseDelete> call, Throwable t) {
                            Toast.makeText(context, "Gagal menghapus akun", Toast.LENGTH_LONG).show();
                        }
                    });
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = alertDialog.create();
            dialog.show();
        }

//
    }




}
