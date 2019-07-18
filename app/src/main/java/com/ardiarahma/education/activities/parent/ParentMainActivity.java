package com.ardiarahma.education.activities.parent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ardiarahma.education.R;
import com.ardiarahma.education.activities.LoginActivity;
import com.ardiarahma.education.fragments.FragmentParentActivityReport;
import com.ardiarahma.education.fragments.FragmentParentChild;
import com.ardiarahma.education.fragments.FragmentParentHome;
import com.ardiarahma.education.fragments.FragmentParentProfile;
import com.ardiarahma.education.fragments.FragmentParentStudyReport;
import com.ardiarahma.education.models.User;
import com.ardiarahma.education.networks.PreferencesConfig;

public class ParentMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_parent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_parent);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_parent);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_parent);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        User user = PreferencesConfig.getInstance(this).getUser();
        TextView header_name = (TextView) headerView.findViewById(R.id.name_header);
        TextView header_email = (TextView) headerView.findViewById(R.id.email_header);
        header_name.setText(user.getNama());
        header_email.setText(user.getEmail());

        displaySelectedScreen(R.id.nav_home_parent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_parent);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public void displaySelectedScreen(int id){
        Fragment fragment = null;
        switch (id){
            case R.id.nav_home_parent:
                fragment = new FragmentParentHome();
                break;
            case R.id.nav_profile_parent:
                fragment = new FragmentParentProfile();
                break;
            case R.id.logout_parent:
                logoutConfirmation();
                break;
        }
        if (fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.screen_area_parent, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_parent);
        drawer.closeDrawer(GravityCompat.START);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedScreen(id);

        return true;
    }

    public void onStart(){
        super.onStart();
        if (!PreferencesConfig.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void logoutConfirmation(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Konfirmasi");
        alertDialog.setMessage("Anda yakin ingin keluar?");
        alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PreferencesConfig.getInstance(context).clear();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(context, "Anda berhasil keluar", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

}
