package com.example.officeroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_DURATION = 2300;

    Animation topAnim,bottomAnim;

    ImageView officeRoomLogo;
    TextView officeRoomText,sloganText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.backgroundColor));
        getWindow().setNavigationBarColor(ContextCompat.getColor(MainActivity.this,R.color.backgroundColor));
        findAllId();

        officeRoomLogo.setAnimation(topAnim);
        officeRoomText.setAnimation(bottomAnim);
        sloganText.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(officeRoomLogo,"officeRoomLogoImage");
                pairs[1] = new Pair<View,String>(officeRoomText,"officeRoomText");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(intent,options.toBundle());
            }
        },SPLASH_DURATION);

    }

    private void findAllId() {
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        officeRoomLogo = findViewById(R.id.officeLogoId);
        officeRoomText = findViewById(R.id.officeTextId);
        sloganText = findViewById(R.id.sloganTextId);
    }
}