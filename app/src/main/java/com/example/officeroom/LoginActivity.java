package com.example.officeroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private static int SPLASH_DURATION = 50;
    TextInputLayout loginMail,loginPassword;
    Button loginButton,registrationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this,R.color.backgroundColor));

        findAllId();

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                        Pair[] pairs = new Pair[4];
                        pairs[0] = new Pair<View,String>(loginMail,"transitionMail");
                        pairs[1] = new Pair<View,String>(loginPassword,"transitionPassword");
                        pairs[2] = new Pair<View,String>(loginButton,"transitionSignToReg");
                        pairs[3] = new Pair<View,String>(registrationButton,"transitionRegToSign");

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
                        startActivity(intent,options.toBundle());

                    }
                },SPLASH_DURATION);
            }
        });
    }

    private void findAllId() {
        loginMail = (TextInputLayout) findViewById(R.id.loginMailId);
        loginPassword = (TextInputLayout) findViewById(R.id.loginPasswordId);
        loginButton = (Button) findViewById(R.id.signInButtonId);
        registrationButton = (Button) findViewById(R.id.signUpButtonId);
    }
}