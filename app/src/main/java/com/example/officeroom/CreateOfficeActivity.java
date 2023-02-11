package com.example.officeroom;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateOfficeActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ImageView createOfficeLogo;
    TextView createOfficeNameText;

    TextInputLayout createOfficeName,createOfficeId,createRankId;

    Button createOfficeButton,createJoinOfficeButton,createRankButton;


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
        HashMap <String,String> ranks = new HashMap<>();

        createOfficeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HashMap<String,Object> info = new HashMap<>();
                String text_office_name = createOfficeName.getEditText().getText().toString();
                String text_office_ID = createOfficeId.getEditText().getText().toString();
                String text_office_type = autoCompleteText.getText().toString();

                for(Map.Entry <String,String> element : ranks.entrySet()){
                    HashMap<String,Object> info = new HashMap<>();
                    String text_rank_name = element.getValue().toString();
                    info.put("Office name",text_office_name);
                    info.put("Office type",text_office_type);
                    info.put("Rank name",element.getValue());

                    db.collection(text_office_ID).document(element.getKey()).set(info)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(CreateOfficeActivity.this, "Room for "+element.getValue(), Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CreateOfficeActivity.this, "Failed for "+element.getValue(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


        createRankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_rank_id = createRankId.getEditText().getText().toString();
                String text_rank_name = autoCompleteText2.getText().toString();
                ranks.put(text_rank_id,text_rank_name);
                Toast.makeText(CreateOfficeActivity.this, "created "+text_rank_name, Toast.LENGTH_SHORT).show();
            }
        });


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
        createRankButton = findViewById(R.id.createRankButtonId);

        createOfficeButton = findViewById(R.id.createOfficeRegButtonId);
        createJoinOfficeButton = findViewById(R.id.createOfficeSignInButtonId);
    }
}