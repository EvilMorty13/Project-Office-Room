package com.example.officeroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class OfficeRoomActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_room);
        getWindow().setStatusBarColor(ContextCompat.getColor(OfficeRoomActivity.this,R.color.white));
        getWindow().setNavigationBarColor(ContextCompat.getColor(OfficeRoomActivity.this,R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        findAllId();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_id,new HomeFragment()).commit();
        bottomView();

    }

    private void bottomView() {

        Bundle data = new Bundle();
        String office_name_string = getIntent().getStringExtra("office_name_string");
        String rank_name_string = getIntent().getStringExtra("rank_name_string");
        String text_office_id = getIntent().getStringExtra("text_office_id");
        String text_rank_id = getIntent().getStringExtra("text_rank_id");

        data.putString("office_name_string",office_name_string);
        data.putString("rank_name_string",rank_name_string);
        data.putString("text_office_id",text_office_id);
        data.putString("text_rank_id",text_rank_id);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home_bar:
                        fragmentTransaction.replace(R.id.fragment_container_id,new HomeFragment()).commit();
                        return true;
                    case R.id.post_bar:
                        AddPostFragment addPostFragment = new AddPostFragment();
                        addPostFragment.setArguments(data);
                        fragmentTransaction.replace(R.id.fragment_container_id,addPostFragment).commit();
                        return true;
                    case R.id.profile_bar:
                        fragmentTransaction.replace(R.id.fragment_container_id,new ProfileFragment()).commit();
                        return true;
                    case R.id.sign_out_bar:
                        fragmentTransaction.replace(R.id.fragment_container_id,new SignOutFragment()).commit();
                        return true;
                    case R.id.about_us_icon:
                        fragmentTransaction.replace(R.id.fragment_container_id,new AboutUsFragment()).commit();
                        return true;

                }

                return false;
            }
        });
    }

    private void findAllId() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_id);
    }
}