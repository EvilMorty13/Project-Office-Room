package com.example.officeroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.nio.Buffer;

public class RegistrationActivity extends AppCompatActivity {

    private static int SPLASH_DURATION = 50;

    ImageView regOfficeRoomLogo;
    TextView regOfficeText;
    TextInputLayout fullName,regMail,regPassword,regConfirmPassword;
    Button regCreateAccount,regSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setStatusBarColor(ContextCompat.getColor(RegistrationActivity.this,R.color.backgroundColor));

        findAllId();
        regSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);

                        Pair[] pairs = new Pair[6];
                        pairs[0] = new Pair<View,String>(regMail,"transitionMail");
                        pairs[1] = new Pair<View,String>(regPassword,"transitionPassword");
                        pairs[2] = new Pair<View,String>(regCreateAccount,"transitionSignToReg");
                        pairs[3] = new Pair<View,String>(regSignIn,"transitionRegToSign");
                        pairs[4] = new Pair<View,String>(regOfficeRoomLogo,"officeRoomLogoImage");
                        pairs[5] = new Pair<View,String>(regOfficeText,"officeRoomText");

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegistrationActivity.this,pairs);
                        startActivity(intent,options.toBundle());
                    }
                },SPLASH_DURATION);
            }
        });

        regCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegistrationActivity.this, "Account created", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findAllId() {
        fullName = (TextInputLayout) findViewById(R.id.fullNameId);
        regMail = (TextInputLayout) findViewById(R.id.regMailId);
        regPassword = (TextInputLayout) findViewById(R.id.regPasswordId);
        regConfirmPassword = (TextInputLayout) findViewById(R.id.regConfirmPasswordId);

        regCreateAccount = (Button) findViewById(R.id.regSignUpButtonId);
        regSignIn = (Button) findViewById(R.id.regSignInButtonId);

        regOfficeRoomLogo = (ImageView) findViewById(R.id.regOfficeRoomLogoId);
        regOfficeText = (TextView) findViewById(R.id.regOfficRoomNameId);
    }


}