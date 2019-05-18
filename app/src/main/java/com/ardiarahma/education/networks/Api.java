package com.ardiarahma.education.networks;

import com.ardiarahma.education.models.responses.ResponseBanksoal;
import com.ardiarahma.education.models.responses.ResponseCheckUser;
import com.ardiarahma.education.models.responses.ResponseDelete;
import com.ardiarahma.education.models.responses.ResponseDistrict;
import com.ardiarahma.education.models.responses.ResponseEbook;
import com.ardiarahma.education.models.responses.ResponseLog;
import com.ardiarahma.education.models.responses.ResponseLogActivityReport;
import com.ardiarahma.education.models.responses.ResponseLogStudyReport;
import com.ardiarahma.education.models.responses.ResponseLogin;
import com.ardiarahma.education.models.responses.ResponseNews;
import com.ardiarahma.education.models.responses.ResponsePassword;
import com.ardiarahma.education.models.responses.ResponseProvince;
import com.ardiarahma.education.models.responses.ResponseRegency;
import com.ardiarahma.education.models.responses.ResponseRegisterAnak;
import com.ardiarahma.education.models.responses.ResponseRegisterOrtu;
import com.ardiarahma.education.models.responses.ResponseStudent;
import com.ardiarahma.education.models.responses.ResponseTask;
import com.ardiarahma.education.models.responses.ResponseUpdateAnak;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Windows 10 on 2/9/2019.
 */

public interface Api {

    @FormUrlEncoded
    @POST("api/user/login")
    Call<ResponseLogin> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/user/orangtua")
    Call<ResponseRegisterOrtu> createOrangtua(
            @Field("name") String name,
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/user/siswa")
    Call<ResponseRegisterAnak> createAnak(
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("jenis_kelamin") String gender,
            @Field("orangtua_id") int user_id,
            @Field("province_id") int province_id,
            @Field("regency_id") int regency_id,
            @Field("district_id") int district_id,
            @Field("school_name") String school,
            @Field("class") int classes,
            @Header("Accept") String accept
    );

    //============== ORANGTUA ================

    @GET("api/province")
    Call<ResponseProvince> province(
            @Header("Authorization") String token
    );

    @GET("api/regency")
    Call<ResponseRegency> regency(
            @Header("Authorization") String token,
            @Query("province_id") int province_id
    );

    @GET("api/district")
    Call<ResponseDistrict> district(
            @Header("Authorization") String token,
            @Query("regency_id") int regency_id
    );

    @GET("api/berita")
    Call<ResponseNews> getJSON(
            @Header("Authorization") String token
    );

    @GET("api/cek/orangtua")
    Call<ResponseCheckUser> readAnak(
            @Header("Authorization") String token,
            @Query("id") int user_id
    );

    @FormUrlEncoded
    @PUT("api/orangtua/profil/update/{id}")
    Call<ResponseLogin> updateOrtu(
            @Header("Authorization") String token,
            @Path("id") int user_id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("api/orangtua/profil/update/password/{id}")
    Call<ResponsePassword> change_pass_ortu(
            @Path("id") int user_id,
            @Header("Authorization") String token,
            @Field("oldPassword") String oldPassword,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
    );

    @FormUrlEncoded
    @PUT("api/orangtua/anak/update/{id}")
    Call<ResponseUpdateAnak> editAnak(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("username") String username,
            @Field("jenis_kelamin") String gender,
            @Field("province_id") int province_id,
            @Field("regency_id") int regency_id,
            @Field("district_id") int district_id,
            @Field("school_name") String school,
            @Field("class") int classes,
            @Header("Accept") String accept
    );

    @DELETE("api/orangtua/anak/deleteanak/{id}")
    Call<ResponseDelete> deleteanak(
            @Header("Authorization") String token,
            @Path("id") int id
    );

    @GET("api/cek/orangtua")
    Call<ResponseCheckUser> child_details(
            @Header("Authorization") String token,
            @Query("id") int user_id
    );

    @GET("api/log/activity")
    Call<ResponseLogActivityReport> log_report(
            @Header("Authorization") String token,
            @Query("id") int id
    );

    @GET("api/log/study")
    Call<ResponseLogStudyReport> log_study_report(
            @Header("Authorization") String token,
            @Query("id") int id
    );


    //=================== SISWA ==================

    @GET("api/ebook/class/")
    Call<ResponseEbook> books(
            @Header("Authorization") String token,
            @Query("subjectscategories") int subjectsId,
            @Query("class") int classes
    );

    @GET("api/banksoal/class/")
    Call<ResponseBanksoal> subsoal(
            @Header("Authorization") String token,
            @Query("subjectscategories") int subjectsId,
            @Query("class") int classes

    );

    @GET("api/cek/siswa")
    Call<ResponseStudent> profile_siswa(
            @Header("Authorization") String token,
            @Query("id") int user_id
    );

    @FormUrlEncoded
    @POST("api/log/ebook")
    Call<ResponseLog> log_ebook(
            @Header("Authorization") String token,
            @Field("id") int user_id,
            @Field("fitur") String fitur
    );

    @FormUrlEncoded
    @POST("api/log/games")
    Call<ResponseLog> log_games(
            @Header("Authorization") String token,
            @Field("id") int user_id,
            @Field("fitur") String fitur
    );

    @FormUrlEncoded
    @POST("api/log/task")
    Call<ResponseLog> log_task(
            @Header("Authorization") String token,
            @Field("id") int user_id,
            @Field("fitur") String fitur
    );

    @GET("api/banksoal/soal")
    Call<ResponseTask> taskmaster_task(
            @Header("Authorization") String token,
            @Query("id") int task_id,
            @Query("class") int classes

    );

}
