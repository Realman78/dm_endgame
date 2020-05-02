package com.example.drzavnamatura_endgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import android.widget.Toast;

import com.example.drzavnamatura_endgame.MainFragments.CompeteFragment;
import com.example.drzavnamatura_endgame.MainFragments.GradivaFragment;
import com.example.drzavnamatura_endgame.MainFragments.HomeFragment;
import com.example.drzavnamatura_endgame.MainFragments.ProfileFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainMenuActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    private CollectionReference usersCollectionRef = db.collection("users");

    public static String gradivoPosition;
    BottomNavigationView bottomNavigationView;

    String TAG = "Find me";

    boolean pressed;
    public static Fragment currentF;
    public static int kojiFragment = 0;

    public static String currentUserUsername = "";
    public static String currentUserEmail = "";

    public static MediaPlayer mediaPlayer;
    public static boolean musicEnabled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        currentUserUsername = currentUser.getDisplayName();
        currentUserEmail = currentUser.getEmail();

//        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.drzavnamatura_endgame", MODE_PRIVATE);
//
//
//        if (!sharedPreferences.contains("musicEnabled")){
//            musicEnabled = true;
//            sharedPreferences.edit().putBoolean("musicEnabled", musicEnabled).apply();
//        }else if (sharedPreferences.contains("musicEnabled")){
//            musicEnabled = sharedPreferences.getBoolean("musicEnabled", false);
//        }
//        boolean musicEnabledFromSp = sharedPreferences.getBoolean("musicEnabled", false);
//        currentF = HomeFragment.newInstance("", "");
//        if (musicEnabledFromSp){
//            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.background_music);
//            mediaPlayer.start();
//        }



        try {
            final ArrayList<String> emails = new ArrayList<>();
            usersCollectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                    for (int i = 0;i<documentSnapshots.size();i++){
                        DocumentSnapshot documentSnapshot = documentSnapshots.get(i);
                        String objects = Objects.requireNonNull(documentSnapshot.get("email")).toString();
                        Log.d(TAG, "onSuccess: " + objects);
                        emails.add(objects);
                    }
                    Log.i("emails", "wowo" + emails);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }







        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        pressed = false;
                        kojiFragment = 0;
                        getSupportFragmentManager().popBackStack();
                        currentF = HomeFragment.newInstance("", "");
                        openFragment(currentF);
                        return true;
                    case R.id.navigation_compete:
                        getSupportFragmentManager().popBackStack();
                        currentF = CompeteFragment.newInstance("", "");
                        openFragment(currentF);
                        return true;
                    case R.id.navigation_profile:
                        getSupportFragmentManager().popBackStack();
                        currentF = ProfileFragment.newInstance("", "");
                        openFragment(currentF);
                        return true;
                }
                return false;
            }
        });
        openFragment(currentF);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }


    public void openToolbarActivity(View view) {
        switch (view.getId()) {
            case R.id.shopButton:
                Intent intent = new Intent(MainMenuActivity.this, ShopActivity.class);
                startActivity(intent);
                break;
            case R.id.settingsButton:
                Intent intent1 = new Intent(MainMenuActivity.this, SettingsActivity.class);
                startActivity(intent1);
                break;
        }
    }

    public void openGradiva(View v) {
        GradivaFragment gradivaFragment = new GradivaFragment();
        FragmentManager fragmentManager1 = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
        kojiFragment = 1;
        fragmentTransaction1.addToBackStack(null);
        fragmentTransaction1.remove(currentF);
        currentF = gradivaFragment;
        fragmentTransaction1.add(R.id.fragment_container, gradivaFragment, "gradiva").commit();

    }

    @Override
    public void onBackPressed() {


        switch (kojiFragment) {
            case 0:
                if (!pressed) {
                    Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
                    pressed = true;
                } else {
                    super.onBackPressed();
                }

                break;
            case 1:
                kojiFragment = 0;
                getSupportFragmentManager().popBackStack();
                currentF = HomeFragment.newInstance("", "");
                openFragment(currentF);
                pressed = false;
                break;
            case 2:
                GradivaFragment gradivaFragment = new GradivaFragment();
                FragmentManager fragmentManager1 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                kojiFragment = 1;
                fragmentTransaction1.addToBackStack(null);
                fragmentTransaction1.remove(currentF);
                currentF = gradivaFragment;
                fragmentTransaction1.add(R.id.fragment_container, gradivaFragment, "gradiva").commit();

                break;
            case 3:
                getSupportFragmentManager().popBackStack();
                currentF = CompeteFragment.newInstance("", "");
                openFragment(currentF);
                break;
        }

    }

    public void signOut(View view) {
        mAuth.signOut();
        Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
        startActivity(intent);
    }


}
