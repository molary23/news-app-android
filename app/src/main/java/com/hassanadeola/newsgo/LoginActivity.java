package com.hassanadeola.newsgo;

import static android.content.ContentValues.TAG;
import static com.hassanadeola.newsgo.utils.Functions.createAlertDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.hassanadeola.newsgo.Models.FBQueries;

public class LoginActivity extends AppCompatActivity {
    private EditText username, email;
    private Button btn_submit, btn_google, btn_apple, btn_facebook;

    private FrameLayout progressBar;
    private String token = null, user, emailAddy;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // db = this.openOrCreateDatabase(USER_DB, MODE_PRIVATE, null);

        sharedPreferences = getSharedPreferences("userPreference", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btn_submit = findViewById(R.id.button_submit);
        btn_apple = findViewById(R.id.button_apple);
        btn_facebook = findViewById(R.id.button_facebook);
        btn_google = findViewById(R.id.button_google);
        username = findViewById(R.id.text_username);
        email = findViewById(R.id.text_email);
        progressBar = findViewById(R.id.progressBar);


        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonClickable();
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonClickable();
            }
        });

        btn_submit.setOnClickListener((View v) -> addUser());

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    token = task.getResult();

                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_bookmarks) {
            AlertDialog.Builder builder = createAlertDialog(this, "Skip Signup",
                    "You can always sign up later in the Settings Page");
            builder.setPositiveButton("Skip", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                FBQueries fbQueries = new FBQueries(LoginActivity.this, token);
                fbQueries.addLoginInfo(null, null, false);
                addToken(null);
                navToPage(MainActivity.class);
            });
            builder.setNegativeButton("Signup Now", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.show();
        }
        return true;
    }

    public void navToPage(Class<?> activityClass) {
        Intent startIntent = new Intent(LoginActivity.this, activityClass);
        startActivity(startIntent);
    }

    public void addUser() {
        user = username.getText().toString().toLowerCase();
        emailAddy = email.getText().toString().toLowerCase();

        if (user.length() <= 5 || emailAddy.length() <= 5) {
            AlertDialog.Builder builder = createAlertDialog(this, "Wrong Credentials",
                    "Please enter a username and email at least 5 characters long");
            builder.show();
        } else {
            toggleDisable(false);
            FBQueries fbQueries = new FBQueries(this, token);
            fbQueries.addLoginInfo(user, emailAddy, true);
            addToken(user);
            Intent startIntent = new Intent(LoginActivity.this, MainActivity.class);
            toggleDisable(true);
            startActivity(startIntent);
        }
    }

    public void setButtonClickable() {
        emailAddy = email.getText().toString();
        user = username.getText().toString();
        if (user.length() >= 5 && emailAddy.length() >= 8) {
            btn_submit.setEnabled(true);
        }
    }

    public void toggleDisable(boolean status) {
        progressBar.setVisibility(status ? View.INVISIBLE : View.VISIBLE);
        if (status) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    public void addToken(String user) {
        editor.putString("token", token);
        if (user != null) {
            editor.putString("username", user);
        }
        editor.commit();
    }
}