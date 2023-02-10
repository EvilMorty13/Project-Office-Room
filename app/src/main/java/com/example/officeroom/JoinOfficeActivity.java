package com.example.officeroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class JoinOfficeActivity extends AppCompatActivity {

    private static int SPLASH_DURATION = 50;
    ImageView joinOfficeLogo;
    TextView joinOfficeNameText;

    TextInputLayout joinOfficeInputId,joinRankInputId;

    Button joinOfficeSignInButton,joinOfficeSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_office);
        getWindow().setStatusBarColor(ContextCompat.getColor(JoinOfficeActivity.this,R.color.backgroundColor));
        getWindow().setNavigationBarColor(ContextCompat.getColor(JoinOfficeActivity.this,R.color.backgroundColor));
        findAllId();

        joinOfficeSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(JoinOfficeActivity.this,CreateOfficeActivity.class);
                        Pair[] pairs = new Pair[6];
                        pairs[0] = new Pair<View,String>(joinOfficeInputId,"transitionMail");
                        pairs[1] = new Pair<View,String>(joinRankInputId,"transitionPassword");
                        pairs[2] = new Pair<View,String>(joinOfficeSignInButton,"transitionSignToReg");
                        pairs[3] = new Pair<View,String>(joinOfficeSignUpButton,"transitionRegToSign");
                        pairs[4] = new Pair<View,String>(joinOfficeLogo,"officeRoomLogoImage");
                        pairs[5] = new Pair<View,String>(joinOfficeNameText,"officeRoomText");

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(JoinOfficeActivity.this,pairs);
                        startActivity(intent,options.toBundle());
                    }
                },SPLASH_DURATION);
            }
        });
    }

    private void findAllId() {
        joinOfficeLogo = (ImageView) findViewById(R.id.joinOfficeLogoId);
        joinOfficeNameText = (TextView) findViewById(R.id.joinOfficeRoomNameId);
        joinOfficeInputId = (TextInputLayout) findViewById(R.id.joinOfficeId);
        joinRankInputId = (TextInputLayout) findViewById(R.id.joinRankId);
        joinOfficeSignInButton = (Button) findViewById(R.id.joinOfficeSignInButtonId);
        joinOfficeSignUpButton = (Button) findViewById(R.id.joinOfficeSignUpButtonId);
    }
}