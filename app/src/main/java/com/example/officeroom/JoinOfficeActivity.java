package com.example.officeroom;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class JoinOfficeActivity extends AppCompatActivity {

    private static int SPLASH_DURATION = 50;
    ImageView joinOfficeLogo;
    TextView joinOfficeNameText;

    TextInputLayout joinOfficeInputId,joinRankInputId;

    Button joinOfficeSignInButton,joinOfficeSignUpButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_office);
        getWindow().setStatusBarColor(ContextCompat.getColor(JoinOfficeActivity.this,R.color.backgroundColor));
        getWindow().setNavigationBarColor(ContextCompat.getColor(JoinOfficeActivity.this,R.color.backgroundColor));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        findAllId();

        joinOfficeSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_office_id = joinOfficeInputId.getEditText().getText().toString();
                String text_rank_id = joinRankInputId.getEditText().getText().toString();
                db.collection(text_office_id).document(text_rank_id).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    startActivity(new Intent(JoinOfficeActivity.this,OfficeRoomActivity.class));
                                    Toast.makeText(JoinOfficeActivity.this, "Join Office Successful", Toast.LENGTH_SHORT).show();
                                }else
                                    Toast.makeText(JoinOfficeActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(JoinOfficeActivity.this, "Server problem", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

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