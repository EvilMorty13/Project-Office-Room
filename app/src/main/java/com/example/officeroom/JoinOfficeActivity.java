package com.example.officeroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class JoinOfficeActivity extends AppCompatActivity {

    private static int SPLASH_DURATION = 50;
    ImageView joinOfficeLogo;
    TextView joinOfficeNameText;

    TextInputLayout joinOfficeInputId,joinRankInputId;

    Button joinOfficeSignInButton,joinOfficeSignUpButton;

    String userId,userName;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_office);
        getWindow().setStatusBarColor(ContextCompat.getColor(JoinOfficeActivity.this,R.color.backgroundColor));
        getWindow().setNavigationBarColor(ContextCompat.getColor(JoinOfficeActivity.this,R.color.backgroundColor));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        findAllId();


        userId = auth.getCurrentUser().getUid();
        db.collection("USER ID").document(userId).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    userName = documentSnapshot.getString("NAME");
                                }
                            }
                        });

        joinOfficeSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_office_id = joinOfficeInputId.getEditText().getText().toString();
                String text_rank_id = joinRankInputId.getEditText().getText().toString();

                boolean join_office_checked = join_office_checkConditions(text_office_id,text_rank_id);

                if(join_office_checked) {


                    db.collection(text_office_id).document(text_rank_id).collection("INFO").document("RANK INFO").get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        Intent intent = new Intent(JoinOfficeActivity.this,OfficeRoomActivity.class);

                                        String office_name_string = documentSnapshot.getString("OFFICE NAME");
                                        String rank_name_string = documentSnapshot.getString("RANK NAME");

                                        intent.putExtra("office_name_string",office_name_string);
                                        intent.putExtra("rank_name_string",rank_name_string);
                                        intent.putExtra("text_office_id",text_office_id);
                                        intent.putExtra("text_rank_id",text_rank_id);
                                        intent.putExtra("userName",userName);

                                        startActivity(intent);
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

    private boolean join_office_checkConditions(String text_office_id, String text_rank_id) {
        if(TextUtils.isEmpty(text_office_id) && TextUtils.isEmpty(text_rank_id)) {
            Toast.makeText(this, "Enter Office ID and Rank ID", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(text_office_id)) {
            Toast.makeText(this, "Enter Office ID", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(text_rank_id)) {
            Toast.makeText(this, "Enter Rank ID", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void onBackPressed(){
        AlertDialog.Builder join_office_alert = new AlertDialog.Builder(JoinOfficeActivity.this);
        View join_office_View = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        join_office_alert.setView(join_office_View);

        AlertDialog join_office_alertDialog = join_office_alert.create();

        join_office_View.findViewById(R.id.no_button).setOnClickListener(v -> {
            join_office_alertDialog.dismiss();
        });

        join_office_View.findViewById(R.id.yes_button).setOnClickListener(v -> {
            Toast.makeText(this, "Exited from app successfully", Toast.LENGTH_SHORT).show();
            join_office_alertDialog.dismiss();
            finishAffinity();
        });

        join_office_alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        join_office_alertDialog.show();

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