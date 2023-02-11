package com.example.officeroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.nio.Buffer;

public class RegistrationActivity extends AppCompatActivity {

    private static int SPLASH_DURATION = 50;

    ImageView regOfficeRoomLogo;
    TextView regOfficeText;
    TextInputLayout fullName,regMail,regPassword,regConfirmPassword;
    Button regCreateAccount,regSignIn;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setStatusBarColor(ContextCompat.getColor(RegistrationActivity.this,R.color.backgroundColor));
        getWindow().setNavigationBarColor(ContextCompat.getColor(RegistrationActivity.this,R.color.backgroundColor));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        findAllId();

        regCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_mail = regMail.getEditText().getText().toString();
                String text_password = regPassword.getEditText().getText().toString();
                String text_full_name = fullName.getEditText().getText().toString();
                String text_confirm_password = regConfirmPassword.getEditText().getText().toString();

                boolean checked = checkConditions(text_full_name,text_mail,text_password,text_confirm_password);

                if(checked) registerAccount(text_mail,text_password);

            }
        });

        regSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegistrationToLoginAnimation();

            }
        });

    }

    private void RegistrationToLoginAnimation() {
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

    private void registerAccount(String text_mail, String text_password) {
        auth.createUserWithEmailAndPassword(text_mail,text_password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                    RegistrationToLoginAnimation();
                }else{
                    Toast.makeText(RegistrationActivity.this, "Server Problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private boolean checkConditions(String full_name, String mail, String password, String confirm_password) {
        if(TextUtils.isEmpty(full_name)) {
            Toast.makeText(this, "Enter Full name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(mail)) {
            Toast.makeText(this, "Enter E-mail", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(confirm_password)) {
            Toast.makeText(this, "Confirm Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.equals(confirm_password)) {
            Toast.makeText(this, "Password don't match with Confirm Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.length()<6) {
            Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void findAllId() {
        auth = FirebaseAuth.getInstance();

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