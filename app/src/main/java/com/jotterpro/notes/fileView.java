package com.jotterpro.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;

public class fileView extends AppCompatActivity {

    EditText fileHead, fileBody;
    FloatingActionButton fab_edit;
    String fileName;
    int stat = 0;

    NitSettings nitSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nitSettings = new NitSettings(this);
        setTitle("Edit Jot");

        if (nitSettings.loadNitState() == true) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_file_view);


        fileHead = findViewById(R.id.edit_head);
        fileHead.setEnabled(false);
        fileBody = findViewById(R.id.edit_body);
        fileBody.setFocusableInTouchMode(false);
        fileBody.clearFocus();
        fab_edit = findViewById(R.id.fab_edit);

        Intent intent = getIntent();
        String pass = intent.getStringExtra("message");
        fileName = pass;
        fileHead.setText(pass);

        File f = new File(Environment.getExternalStorageDirectory() + "/" + "MyJots", pass);
        StringBuilder readTxt = new StringBuilder();
        try {

            Reader reader = new InputStreamReader(new FileInputStream(f));
            char[] buff = new char[500];
            for (int charsRead; (charsRead = reader.read(buff)) != -1; ) {
                readTxt.append(buff, 0, charsRead);
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        fileBody.setText(readTxt);

        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stat == 0) {
                    fileHead.setFocusableInTouchMode(true);
                    fileBody.setFocusableInTouchMode(true);
                    stat = 1;
                    fab_edit.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp));
                    Snackbar.make(v, "Now you can edit your Jot", Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(),"Now you can edit your Jot",Toast.LENGTH_SHORT).show();
                } else {
                    String filename = fileHead.getText().toString();
                    String content = fileBody.getText().toString();
                    String folder_main = "MyJots";

                    File f = new File(Environment.getExternalStorageDirectory() + "/" + folder_main);
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    try {
                        File nFile = new File(f + "/" + filename);
                        FileWriter fw = new FileWriter(nFile);
                        fw.write(content);
                        fw.close();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    fileBody.setFocusableInTouchMode(false);
                    fileBody.clearFocus();
                    stat = 0;
                    fab_edit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_black_24dp));
                    Snackbar.make(v, "Jot Saved", Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(),"Jot Saved",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {

        AlertDialog alt_dia = new AlertDialog.Builder(fileView.this).create();
        alt_dia.setTitle("Save your edits!");
        alt_dia.setMessage("Are you sure, you want to go back?");

        alt_dia.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HomeActivity(null);
            }
        });

        alt_dia.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alt_dia.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int del_menu_id = item.getItemId();

        switch (del_menu_id) {
            case R.id.opt_del:
                AlertDialog alt = new AlertDialog.Builder(fileView.this).create();
                alt.setTitle("Warning!");
                alt.setMessage("Do you want to delete, " + fileName + "?");

                alt.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File f = new File(Environment.getExternalStorageDirectory() + "/MyJots" + "/" + fileName);
                        f.delete();
                        finish();
                        HomeActivity(null);
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void HomeActivity(View view) {

        Intent HomeIntent = new Intent(this, HomeScreen.class);
        if (nitSettings.loadNitState() == true) {
            HomeIntent.putExtra("nitVal", "One");
        } else
            HomeIntent.putExtra("nitVal", "Zero");
        startActivity(HomeIntent);
        overridePendingTransition(R.anim.left_enter, R.anim.right_out);
    }
}
