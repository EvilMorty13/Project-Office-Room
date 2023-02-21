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

    boolean[] selected_fragment = {true,false,false,false,false};

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



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if(item.getItemId()==R.id.home_bar && selected_fragment[0]==false){
                    make_false_all();
                    selected_fragment[0]=true;
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.fragment_container_id,homeFragment).commit();
                    return true;
                }else if(item.getItemId()==R.id.post_bar && selected_fragment[1]==false){
                    make_false_all();
                    selected_fragment[1]=true;
                    AddPostFragment addPostFragment = new AddPostFragment();
                    addPostFragment.setArguments(data);
                    fragmentTransaction.replace(R.id.fragment_container_id,addPostFragment).commit();
                    return true;
                }else if(item.getItemId()==R.id.profile_bar && selected_fragment[2]==false){
                    make_false_all();
                    selected_fragment[2]=true;
                    ProfileFragment profileFragment = new ProfileFragment();
                    fragmentTransaction.replace(R.id.fragment_container_id,profileFragment).commit();
                    return true;
                }else if(item.getItemId()==R.id.sign_out_bar && selected_fragment[3]==false){
                    make_false_all();
                    selected_fragment[3]=true;
                    SignOutFragment signOutFragment = new SignOutFragment();
                    fragmentTransaction.replace(R.id.fragment_container_id,signOutFragment).commit();
                    return true;
                }else if(item.getItemId()==R.id.about_us_bar && selected_fragment[4]==false){
                    make_false_all();
                    selected_fragment[4]=true;
                    AboutUsFragment aboutUsFragment = new AboutUsFragment();
                    fragmentTransaction.replace(R.id.fragment_container_id,aboutUsFragment).commit();
                    return true;
                }

                return false;
            }
        });
    }

    private void make_false_all() {
        for(int i=0;i<5;i++) selected_fragment[i]=false;
    }

    private void findAllId() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_id);
    }
}