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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static int SPLASH_DURATION = 50;

    ImageView loginOfficeLogo;
    TextView loginOfficeText;
    TextInputLayout loginMail,loginPassword;
    Button loginButton,registrationButton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this,R.color.backgroundColor));
        getWindow().setNavigationBarColor(ContextCompat.getColor(LoginActivity.this,R.color.backgroundColor));
        findAllId();



        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                        Pair[] pairs = new Pair[6];
                        pairs[0] = new Pair<View,String>(loginMail,"transitionMail");
                        pairs[1] = new Pair<View,String>(loginPassword,"transitionPassword");
                        pairs[2] = new Pair<View,String>(loginButton,"transitionSignToReg");
                        pairs[3] = new Pair<View,String>(registrationButton,"transitionRegToSign");
                        pairs[4] = new Pair<View,String>(loginOfficeLogo,"officeRoomLogoImage");
                        pairs[5] = new Pair<View,String>(loginOfficeText,"officeRoomText");

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
                        startActivity(intent,options.toBundle());
                    }
                },SPLASH_DURATION);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_mail = loginMail.getEditText().getText().toString();
                String text_password = loginPassword.getEditText().getText().toString();

                boolean checked = checkConditions(text_mail,text_password);

                if(checked) {
                    loginAccount(text_mail, text_password);
                }
            }
        });
    }

    private void loginAccount(String text_mail, String text_password) {
        auth.signInWithEmailAndPassword(text_mail,text_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(LoginActivity.this,JoinOfficeActivity.class);
                            Pair[] pairs = new Pair[6];
                            pairs[0] = new Pair<View,String>(loginMail,"transitionMail");
                            pairs[1] = new Pair<View,String>(loginPassword,"transitionPassword");
                            pairs[2] = new Pair<View,String>(loginButton,"transitionSignToReg");
                            pairs[3] = new Pair<View,String>(registrationButton,"transitionRegToSign");
                            pairs[4] = new Pair<View,String>(loginOfficeLogo,"officeRoomLogoImage");
                            pairs[5] = new Pair<View,String>(loginOfficeText,"officeRoomText");

                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
                            startActivity(intent,options.toBundle());
                        }
                    },SPLASH_DURATION);
                }else{
                    Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkConditions(String text_mail, String text_password) {
        if(TextUtils.isEmpty(text_mail)&&TextUtils.isEmpty(text_password)) {
            Toast.makeText(this, "Enter Mail and Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(text_mail)) {
            Toast.makeText(this, "Enter Mail", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(text_password)) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void findAllId() {
        auth = FirebaseAuth.getInstance();
        loginMail = (TextInputLayout) findViewById(R.id.loginMailId);
        loginPassword = (TextInputLayout) findViewById(R.id.loginPasswordId);
        loginButton = (Button) findViewById(R.id.signInButtonId);
        registrationButton = (Button) findViewById(R.id.signUpButtonId);
        loginOfficeLogo = (ImageView) findViewById(R.id.loginOfficeLogoId);
        loginOfficeText = (TextView) findViewById(R.id.loginOfficRoomNameId);
    }
}