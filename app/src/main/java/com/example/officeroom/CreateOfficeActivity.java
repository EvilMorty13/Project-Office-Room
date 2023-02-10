package com.example.officeroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateOfficeActivity extends AppCompatActivity {

    ImageView createOfficeLogo;
    TextView createOfficeNameText;

    TextInputLayout createOfficeName,createOfficeId,createRankId;

    Button createOfficeButton,createJoinOfficeButton;


    String[] items = {"Institute","Software Farm","Factory"};
    String[] institute_ranks = {"Dean","Professor","Assistant Professor","Lecturer","Assistant","Lab Assistant"};
    String[] software_farm_rank = {"CEO","CTO","Lead Engineer","Senior Software Engineer","Junior Software Engineer","Software Engineer Intern"};
    String[] Factory_rank = {"Manager","Assistant Manager","Staff","Labour"};
    AutoCompleteTextView autoCompleteText,autoCompleteText2;
    ArrayAdapter <String> arrayAdapter;
    ArrayAdapter <String> arrayAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_office);
        getWindow().setStatusBarColor(ContextCompat.getColor(CreateOfficeActivity.this,R.color.backgroundColor));
        getWindow().setNavigationBarColor(ContextCompat.getColor(CreateOfficeActivity.this,R.color.backgroundColor));
        findAllId();

        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(CreateOfficeActivity.this, "selected "+item, Toast.LENGTH_SHORT).show();
                autoCompleteText2.setText(null);
                if(item.equals("Institute")){
                    arrayAdapter2 = new ArrayAdapter<>(CreateOfficeActivity.this,R.layout.list_item,institute_ranks);
                }
                else if(item.equals("Software Farm")){
                    arrayAdapter2 = new ArrayAdapter<>(CreateOfficeActivity.this,R.layout.list_item,software_farm_rank);
                }
                else if(item.equals("Factory")){
                    arrayAdapter2 = new ArrayAdapter<>(CreateOfficeActivity.this,R.layout.list_item,Factory_rank);
                }
                autoCompleteText2.setAdapter(arrayAdapter2);
            }
        });

        autoCompleteText2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(CreateOfficeActivity.this, "Selected "+item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findAllId() {
        autoCompleteText = findViewById(R.id.createOfficeTypeId);
        arrayAdapter = new ArrayAdapter<>(this,R.layout.list_item,items);
        autoCompleteText.setAdapter(arrayAdapter);

        autoCompleteText2 = findViewById(R.id.createAddRankId);

        createOfficeLogo = findViewById(R.id.createOfficeLogoId);
        createOfficeNameText = findViewById(R.id.createOfficeRoomNameId);
        createOfficeName = findViewById(R.id.createOfficeNameId);
        createOfficeId = findViewById(R.id.createOfficeID_Id);
        createRankId = findViewById(R.id.createRankID_Id);

        createOfficeButton = findViewById(R.id.createOfficeRegButtonId);
        createJoinOfficeButton = findViewById(R.id.createOfficeSignInButtonId);
    }
}