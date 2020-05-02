package com.example.drzavnamatura_endgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.drzavnamatura_endgame.MainMenuActivity.mediaPlayer;

public class SettingsActivity extends AppCompatActivity {
    Button goBackButton_settings;
    Button musicSwitch;
    ToggleButton enableReminderButton;
    Button editReminder;
    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    View lay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        goBackButton_settings = findViewById(R.id.backButton_settings);
        goBackButton_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
        musicSwitch = findViewById(R.id.music_switch_button);
        enableReminderButton = findViewById(R.id.toggleButtonSetReminder);
        editReminder = findViewById(R.id.setReminderButton);
        mAuth = FirebaseAuth.getInstance();
        lay = findViewById(R.id.constLayout);
        /*sharedPreferences = this.getSharedPreferences("com.example.drzavnamatura_endgame", MODE_PRIVATE);

        musicSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicSwitch.getText().toString().equals("ON")){
                    musicSwitch.setText("OFF");
                    sharedPreferences.edit().clear().apply();
                    sharedPreferences.edit().putBoolean("musicEnabled", false).apply();
                    makeSnackbar("MUSIC DISABLED");
                    mediaPlayer.stop();
                }else if (musicSwitch.getText().toString().equals("OFF")){
                    musicSwitch.setText("ON");
                    sharedPreferences.edit().clear().apply();
                    sharedPreferences.edit().putBoolean("musicEnabled", true).apply();
                    makeSnackbar("MUSIC ENABLED");
                }
            }
        });*/


        enableReminderButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(SettingsActivity.this, "Reminder On", Toast.LENGTH_SHORT).show();
                    editReminder.setVisibility(View.VISIBLE);
                }else{
                    editReminder.setVisibility(View.INVISIBLE);
                }
            }
        });
        editReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, SetReminderActivity.class);
                startActivity(intent);
            }
        });


    }
    public void signOut(View view) {
        mAuth.signOut();
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void makeSnackbar(String text){
        Snackbar snackbar = Snackbar.make(lay, text, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
