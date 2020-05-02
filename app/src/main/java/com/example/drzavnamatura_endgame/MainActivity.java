package com.example.drzavnamatura_endgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    private CollectionReference usersCollectionRef = db.collection("users");

    private TextInputLayout inputLayout_username;
    private TextInputLayout inputLayout_password;
    private TextInputLayout inputLayout_repeatPassword;
    private TextInputLayout inputLayout_email;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private EditText emailEditText;

    private Button registerButton;
    private Button goToLoginButton;
    ConstraintLayout wholeLayoutRegister;

    final String score = "0";
    static ArrayList<String> allUsernames;
    boolean isTakenUsername;
    boolean isUsernameValid;
    boolean autoLoginCheck;
    ProgressBar loadingBar;

    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputLayout_username =  findViewById(R.id.username_textInputLayout);
        inputLayout_password =  findViewById(R.id.password_textInputLayout);
        inputLayout_repeatPassword =  findViewById(R.id.passwordRepeat_textInputLayout);
        inputLayout_email =  findViewById(R.id.email_textInputLayout);
        usernameEditText =  findViewById(R.id.username_editText);
        passwordEditText =  findViewById(R.id.password_editText);
        repeatPasswordEditText =  findViewById(R.id.passwordRepeat_editText);
        emailEditText =  findViewById(R.id.email_editText);
        registerButton = findViewById(R.id.register_button);
        goToLoginButton = findViewById(R.id.go_to_login_Button);
        loadingBar = findViewById(R.id.progressBar_register);
        wholeLayoutRegister = findViewById(R.id.outsideRelativeLayout);
        mAuth = FirebaseAuth.getInstance();
        //
        wholeLayoutRegister.setAlpha(0);


        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains(" ")) {
                    inputLayout_username.setErrorEnabled(true);
                    inputLayout_username.setError("No spaces allowed");
                } else {
                    inputLayout_username.setErrorEnabled(false);
                }
            }
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() < 6) {
                    inputLayout_password.setErrorEnabled(true);
                    inputLayout_password.setError("Password must be at least 6 characters");
                } else if (s.length() >= 6) {
                    inputLayout_password.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 6) {
                    inputLayout_password.setErrorEnabled(true);
                    inputLayout_password.setError("Password must be at least 6 characters");
                } else if (s.length() >= 6) {
                    inputLayout_password.setErrorEnabled(false);
                }
            }
        });

        repeatPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (String.valueOf(s).equals(passwordEditText.getText().toString())) {
                    inputLayout_repeatPassword.setErrorEnabled(false);
                } else {
                    inputLayout_repeatPassword.setErrorEnabled(true);
                    inputLayout_repeatPassword.setError("Passwords do not match");
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (String.valueOf(s).equals(passwordEditText.getText().toString())) {
                    inputLayout_repeatPassword.setErrorEnabled(false);
                } else {
                    inputLayout_repeatPassword.setErrorEnabled(true);
                    inputLayout_repeatPassword.setError("Passwords do not match");
                }
            }
        });
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() < 5) {
                    inputLayout_email.setErrorEnabled(true);
                    inputLayout_email.setError("Please insert a valid e-mail address");
                } else {
                    inputLayout_email.setErrorEnabled(false);
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
            }
        };
        allUsernames = new ArrayList<>();
        usersCollectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                for (int i = 0; i < documentSnapshots.size(); i++) {
                    DocumentSnapshot documentSnapshot = documentSnapshots.get(i);
                    String names = Objects.requireNonNull(documentSnapshot.get("username")).toString();
                    allUsernames.add(names);
                }
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        goToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableDoubleClick(goToLoginButton);
                disableDoubleClick(registerButton);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableDoubleClick(goToLoginButton);
                disableDoubleClick(registerButton);
                String username = usernameEditText.getText().toString();
                if (username.contains(" ")) {
                    isUsernameValid = false;
                } else if (!username.contains(" ")) {
                    isUsernameValid = true;
                }

                String password = passwordEditText.getText().toString();
                String repeatPassword = repeatPasswordEditText.getText().toString();
                String email = emailEditText.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(username) && !isUsernameTaken(username) && isUsernameValid) {
                    if (repeatPassword.equals(password)) {
                        CreateUser(email, password, username);
                    } else {
                        Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                } else if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
                    Toast.makeText(MainActivity.this, "Field(s) cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (isUsernameTaken(username)) {
                    Toast.makeText(MainActivity.this, "Username is taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    private void CreateUser(final String email, final String password, final String username) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        loadingBar.setVisibility(View.VISIBLE);
                        currentUser = mAuth.getCurrentUser();
                        assert currentUser != null;
                        final String userID = currentUser.getUid();
                        User user = new User(username, email, userID, score);

                        usersCollectionRef.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (Objects.requireNonNull(task.getResult()).exists()) {
                                            String name = task.getResult().getString("username");
                                            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                @Override
                                                public void onSuccess(AuthResult authResult) {
                                                    Toast.makeText(MainActivity.this, "signed in", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                            currentUser.updateProfile(changeRequest);

                                            Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                                            intent.putExtra("username", name);
                                            intent.putExtra("userId", userID);
                                            loadingBar.setVisibility(View.INVISIBLE);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Objects.requireNonNull(task.getException()).printStackTrace();
                                        }
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Objects.requireNonNull(task.getException()).printStackTrace();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(authStateListener);
        if (currentUser != null) {
            usersCollectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                    for (int i = 0; i < documentSnapshots.size(); i++) {
                        DocumentSnapshot documentSnapshot = documentSnapshots.get(i);
                        String names = Objects.requireNonNull(documentSnapshot.get("username")).toString();
                        assert currentUser != null;
                        autoLoginCheck = names.equals(currentUser.getDisplayName());
                        if (mAuth.getCurrentUser() != null && autoLoginCheck) {
                            Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                }
            });
        } else {
            wholeLayoutRegister.animate().alpha(1).setDuration(1000);
            loadingBar.setVisibility(View.INVISIBLE);
        }

    }

    public boolean isUsernameTaken(final String username) {
        isTakenUsername = false;
        for (int i = 0; i < allUsernames.size(); i++) {
            if (allUsernames.get(i).equals(username)) {
                isTakenUsername = true;
            }
        }
        return isTakenUsername;
    }

    public static void disableDoubleClick(final Button button) {
        button.setEnabled(false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setEnabled(true);
            }
        }, 2500);
    }
}








