package com.jotterpro.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class newabout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("About");

        Intent intent=getIntent();
        String pass=intent.getStringExtra("nitVal");

        if(pass.equals("One"))
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.DarkTheme);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_newabout);
    }

    @Override
    public void onBackPressed() {

        MainScreen(null);

    }

    public void MainScreen(View view) {

        Intent MainIntent=new Intent(this,MainActivity.class);
        startActivity(MainIntent);
        overridePendingTransition(android.R.anim.slide_in_left,0);
    }

}
