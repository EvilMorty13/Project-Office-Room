package com.example.officeroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

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

        Bundle data = new Bundle();
        String office_name_string = getIntent().getStringExtra("office_name_string");
        String rank_name_string = getIntent().getStringExtra("rank_name_string");
        String text_office_id = getIntent().getStringExtra("text_office_id");
        String text_rank_id = getIntent().getStringExtra("text_rank_id");

        data.putString("office_name_string",office_name_string);
        data.putString("rank_name_string",rank_name_string);
        data.putString("text_office_id",text_office_id);
        data.putString("text_rank_id",text_rank_id);

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(data);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_id,homeFragment).commit();
        bottomView(data);

    }

    private void bottomView(Bundle data) {

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(data);

        AddPostFragment addPostFragment = new AddPostFragment();
        addPostFragment.setArguments(data);

        ProfileFragment profileFragment = new ProfileFragment();

        SignOutFragment signOutFragment = new SignOutFragment();

        AboutUsFragment aboutUsFragment = new AboutUsFragment();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if(item.getItemId()==R.id.home_bar && selected_fragment[0]==false){
                    make_false_all();
                    selected_fragment[0]=true;
                    fragmentTransaction.replace(R.id.fragment_container_id,homeFragment).commit();
                    return true;
                }else if(item.getItemId()==R.id.post_bar && selected_fragment[1]==false){
                    make_false_all();
                    selected_fragment[1]=true;
                    fragmentTransaction.replace(R.id.fragment_container_id,addPostFragment).commit();
                    return true;
                }else if(item.getItemId()==R.id.profile_bar && selected_fragment[2]==false){
                    make_false_all();
                    selected_fragment[2]=true;
                    fragmentTransaction.replace(R.id.fragment_container_id,profileFragment).commit();
                    return true;
                }else if(item.getItemId()==R.id.sign_out_bar && selected_fragment[3]==false){
                    make_false_all();
                    selected_fragment[3]=true;


                    ShowDialogBox();




                    //fragmentTransaction.replace(R.id.fragment_container_id,signOutFragment).commit();
                    return true;
                }else if(item.getItemId()==R.id.about_us_bar && selected_fragment[4]==false){
                    make_false_all();
                    selected_fragment[4]=true;

                    fragmentTransaction.replace(R.id.fragment_container_id,aboutUsFragment).commit();
                    return true;
                }

                return false;
            }
        });
    }

    private void ShowDialogBox() {


        AlertDialog.Builder sign_out_alert = new AlertDialog.Builder(OfficeRoomActivity.this);
        View sign_out_View = getLayoutInflater().inflate(R.layout.dialog_layout_sign_out, null);
        sign_out_alert.setView(sign_out_View);

        AlertDialog sign_out_alertDialog = sign_out_alert.create();
        sign_out_alertDialog.setCancelable(false);

        sign_out_View.findViewById(R.id.sign_out_no_button).setOnClickListener(v -> {
            sign_out_alertDialog.dismiss();
        });

        sign_out_View.findViewById(R.id.sign_out_yes_button).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(OfficeRoomActivity.this, "Signed Out Successfully", Toast.LENGTH_SHORT).show();
            sign_out_alertDialog.dismiss();
            startActivity(new Intent(OfficeRoomActivity.this,LoginActivity.class));
        });

        sign_out_alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sign_out_alertDialog.show();

    }

    private void make_false_all() {
        for(int i=0;i<5;i++) selected_fragment[i]=false;
    }

    public void onBackPressed(){
        AlertDialog.Builder office_room_alert = new AlertDialog.Builder(OfficeRoomActivity.this);
        View office_room_View = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        office_room_alert.setView(office_room_View);

        AlertDialog office_room_alertDialog = office_room_alert.create();

        office_room_View.findViewById(R.id.no_button).setOnClickListener(v -> {
            office_room_alertDialog.dismiss();
        });

        office_room_View.findViewById(R.id.yes_button).setOnClickListener(v -> {
            Toast.makeText(this, "Exited from app successfully", Toast.LENGTH_SHORT).show();
            office_room_alertDialog.dismiss();
            finishAffinity();
        });

        office_room_alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        office_room_alertDialog.show();
    }

    private void findAllId() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_id);
    }
}