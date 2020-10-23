package com.jotterpro.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.File;
import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    ListView lView;
    ArrayList<String> ar = new ArrayList<>();
    String fileName;
    NitSettings nitSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nitSettings = new NitSettings(this);

        setTitle("My Jots");

        Intent intent = getIntent();
        String pass = intent.getStringExtra("nitVal");

        if (pass.equals("One"))
        //           if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
//        if (nitSettings.loadNitState() == true)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.DarkTheme);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_home_screen);

        lView = findViewById(R.id.File_list);

        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File jotDir = new File(sdCard, "MyJots");

            for (File f : jotDir.listFiles()) {
                ar.add(f.getName());
            }

            ArrayAdapter<String> fileAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ar);

            lView.setAdapter(fileAdapter);

            lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    fileName = ar.get(position);
                    viewFiles(null);

                }
            });

            lView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog alt = new AlertDialog.Builder(HomeScreen.this).create();
                    alt.setTitle("Warning!");
                    fileName = ar.get(position);
                    alt.setMessage("Do you want to delete, " + fileName + "?");

                    alt.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File f = new File(Environment.getExternalStorageDirectory() + "/MyJots" + "/" + fileName);
                            f.delete();
                            finish();
                            startActivity(getIntent());
                            Toast.makeText(getApplicationContext(), "File Deleted", Toast.LENGTH_SHORT).show();

                        }
                    });

                    alt.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    alt.show();
                    return true;
                }
            });

        } catch (Exception e) {

        }
    }


    public void viewFiles(View view) {
        Intent FilesIntent = new Intent(this, fileView.class);
        FilesIntent.putExtra("message", fileName);
        startActivity(FilesIntent);
        overridePendingTransition(R.anim.right_enter, R.anim.left_out);
    }

    @Override
    public void onBackPressed() {

        MainScreen(null);

    }

    public void MainScreen(View view) {

        Intent MainIntent = new Intent(this, MainActivity.class);
        startActivity(MainIntent);
        overridePendingTransition(R.anim.left_enter, R.anim.right_out);
    }


}
