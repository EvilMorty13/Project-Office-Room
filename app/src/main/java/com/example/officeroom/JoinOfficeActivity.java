package com.example.officeroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class JoinOfficeActivity extends AppCompatActivity {

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
                startActivity(new Intent(JoinOfficeActivity.this,CreateOfficeActivity.class));
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