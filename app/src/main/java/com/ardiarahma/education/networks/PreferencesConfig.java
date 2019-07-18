package com.ardiarahma.education.networks;

import android.content.Context;
import android.content.SharedPreferences;

import com.ardiarahma.education.models.BanksoalShelves;
import com.ardiarahma.education.models.Token;
import com.ardiarahma.education.models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Windows 10 on 5/18/2019.
 */

public class PreferencesConfig {

    private SharedPreferences sharedPreferences;
    private static PreferencesConfig instance;
    private List<String> arrAnswer;
    private Context context;

    public PreferencesConfig(Context context) {
        this.context = context;
    }

    public static synchronized PreferencesConfig getInstance(Context context){
        if (instance == null){
            instance = new PreferencesConfig(context);
        }
        return instance;
    }

//    public void saveAnswer(){
//        sharedPreferences = context.getSharedPreferences("answers", Context.MODE_PRIVATE);
//        arrAnswer = new ArrayList<>();
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Set<String> set = new HashSet<String>();
//        set.addAll(arrAnswer);
////        editor.putInt("ans", )
//        editor.apply();
//    }
//
//    public String getAns(){
//        sharedPreferences = context.getSharedPreferences("answers", Context.MODE_PRIVATE);
//        return new String(
//                sharedPreferences.getString("ans", null)
//        );
//    }

    //nyimpen task_id, dsb
    public void saveTaskVar(BanksoalShelves task){
        sharedPreferences = context.getSharedPreferences("BankSoal", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("task_id", task.getTask_id());
        editor.putString("name", task.getName());
        editor.putInt("classes", task.getClasses());
        editor.apply();
    }

    public BanksoalShelves getTaskData(){
        sharedPreferences = context.getSharedPreferences("BankSoal", Context.MODE_PRIVATE);
        return  new BanksoalShelves(
                sharedPreferences.getInt("task_id", 0),
                sharedPreferences.getString("name", null),
                sharedPreferences.getInt("classes", 0)
        );
    }

    //untuk menyimpan user ortu pas login
    public void saveUser(User user){
        sharedPreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("nama", user.getNama());
        editor.putString("email", user.getEmail());
        editor.putString("username", user.getUsername());
        editor.putString("type", user.getType());
        editor.putInt("id_user", user.getId_user());
        editor.apply();
    }

    //untuk mengetahui user udah login atau belum
    public boolean isLoggedIn(){
        sharedPreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    //buat manggil user pas login
    public User getUser(){
        sharedPreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("nama",null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("type", null),
                sharedPreferences.getInt("id_user", -1)
        );
    }

    //logout, clear data yang udah dilogin
    public void clear(){
        sharedPreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    public void saveToken(Token token){
        sharedPreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token.getToken());
        editor.apply();
    }

    public Token getToken(){
        sharedPreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        return new Token(
                sharedPreferences.getString("token", null)
        );
    }
}
