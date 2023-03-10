package com.example.officeroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CreateOfficeActivity extends AppCompatActivity {

    private static int SPLASH_DURATION = 50;

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
    ArrayAdapter <String> arrayAdapter,arrayAdapter2,arrayAdapter3;

    MultiAutoCompleteTextView multiAutoCompleteTextView;
    ArrayList<Integer> selectedItems;
    ArrayList<String> communicate_with_items;
    String[] selected_array;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_office);
        getWindow().setStatusBarColor(ContextCompat.getColor(CreateOfficeActivity.this,R.color.backgroundColor));
        getWindow().setNavigationBarColor(ContextCompat.getColor(CreateOfficeActivity.this,R.color.backgroundColor));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        findAllId();
        HashMap <String,String> ranks = new HashMap<>();
        HashMap <String,ArrayList<String>> ranks_communicate_with = new HashMap<>();


        String unique_office_id = new UniqueIdGenerator().generateID();
        createOfficeId.getEditText().setText(unique_office_id);

        createJoinOfficeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOfficeToJoinOffice();
            }
        });

        createOfficeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HashMap<String,Object> info = new HashMap<>();
                String text_office_name = createOfficeName.getEditText().getText().toString();
                String text_office_ID = createOfficeId.getEditText().getText().toString();
                String text_office_type = autoCompleteText.getText().toString();

                boolean checked = checkConditions(text_office_name,text_office_ID,text_office_type);

                if(checked) {

                    HashMap <String,Object> map = new HashMap<>();
                    map.put("OFFICE NAME",text_office_name);
                    map.put("OFFICE ID",text_office_ID);
                    map.put("OFFICE TYPE",text_office_type);

                    db.collection(text_office_ID).document("OFFICE INFO").set(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(CreateOfficeActivity.this, "Office Info Set", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CreateOfficeActivity.this, "Failed to Create Office Info", Toast.LENGTH_SHORT).show();
                                }
                            });

                    for(Map.Entry <String,String> element : ranks.entrySet()){

                        String text_rank_id = element.getKey();
                        String text_rank_name = element.getValue();

                        HashMap<String,String> rank_id_collection = new HashMap<>();
                        rank_id_collection.put(text_rank_name,text_rank_id);

                        HashMap <String,Object> note = new HashMap<>();
                        note.put("OFFICE NAME",text_office_name);
                        note.put("RANK NAME",text_rank_name);
                        note.put("OFFICE ID",text_office_ID);

                        ArrayList<String> temp_ArrayList = ranks_communicate_with.get(text_rank_id);

                        HashMap<String,ArrayList<String>> store_communication = new HashMap<>();
                        store_communication.put("Communications",temp_ArrayList);

                        db.collection(text_office_ID).document("RANK ID INFO").collection(text_rank_name).document(text_rank_name).set(rank_id_collection);

                        db.collection(text_office_ID).document(text_rank_id).collection("INFO").document("RANK INFO")
                                .set(note);

                        db.collection(text_office_ID).document(text_rank_id).collection("INFO").document("COMMUNICATION")
                                .set(store_communication);
                    }createOfficeToJoinOffice();
                }
            }
        });


        createRankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_rank_id = createRankId.getEditText().getText().toString();
                String text_rank_name = autoCompleteText2.getText().toString();
                String text_office_id = createOfficeId.getEditText().getText().toString();


                //redesign database
                ranks.put(text_rank_id,text_rank_name);
                ranks_communicate_with.put(text_rank_id,communicate_with_items);
            }
        });


        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                String unique_office_id_v2 = new UniqueIdGenerator().generateID();
                createOfficeId.getEditText().setText(unique_office_id_v2);
                Toast.makeText(CreateOfficeActivity.this, "selected "+item, Toast.LENGTH_SHORT).show();

                autoCompleteText2.setText(null);
                multiAutoCompleteTextView.setText(null);


                if(item.equals("Institute")){
                    selected_array = new String[institute_ranks.length];
                    arrayAdapter2 = new ArrayAdapter<>(CreateOfficeActivity.this,R.layout.list_item,institute_ranks);
                    selected_array = institute_ranks;
                }
                else if(item.equals("Software Farm")){
                    selected_array = new String[software_farm_rank.length];
                    arrayAdapter2 = new ArrayAdapter<>(CreateOfficeActivity.this,R.layout.list_item,software_farm_rank);
                    selected_array = software_farm_rank;
                }
                else if(item.equals("Factory")){
                    selected_array = new String[Factory_rank.length];
                    arrayAdapter2 = new ArrayAdapter<>(CreateOfficeActivity.this,R.layout.list_item,Factory_rank);
                    selected_array = Factory_rank;
                }
                autoCompleteText2.setAdapter(arrayAdapter2);

                arrayAdapter3 = new ArrayAdapter<>(CreateOfficeActivity.this,R.layout.list_item,selected_array);
                multiAutoCompleteTextView.setAdapter(arrayAdapter3);

                multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

                selectedItems = new ArrayList<>();
                communicate_with_items = new ArrayList<>();

            }
        });

        autoCompleteText2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItems = new ArrayList<>();
                communicate_with_items = new ArrayList<>();
                multiAutoCompleteTextView.setText(null);
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(CreateOfficeActivity.this, "Selected "+item, Toast.LENGTH_SHORT).show();
            }
        });

        multiAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(CreateOfficeActivity.this, "Communicate with "+item, Toast.LENGTH_SHORT).show();

                if(!selectedItems.contains(i)){
                    selectedItems.add(i);
                    Collections.sort(selectedItems);
                }

                StringBuilder stringBuilder = new StringBuilder();
                for(int j=0;j<selectedItems.size();j++){
                    String temp_string = selected_array[selectedItems.get(j)];
                    stringBuilder.append(temp_string);
                    if(!communicate_with_items.contains(temp_string)) communicate_with_items.add(temp_string);
                    if(j!=selectedItems.size()-1) stringBuilder.append(", ");
                }

                multiAutoCompleteTextView.setText(stringBuilder.toString());
                multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            }
        });

    }

    public void onBackPressed(){
        createOfficeToJoinOffice();
    }

    private void createOfficeToJoinOffice() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(CreateOfficeActivity.this,JoinOfficeActivity.class);
                Pair[] pairs = new Pair[6];
                pairs[0] = new Pair<View,String>(createOfficeId,"transitionMail");
                pairs[1] = new Pair<View,String>(createRankId,"transitionPassword");
                pairs[2] = new Pair<View,String>(createOfficeButton,"transitionSignToReg");
                pairs[3] = new Pair<View,String>(createJoinOfficeButton,"transitionRegToSign");
                pairs[4] = new Pair<View,String>(createOfficeLogo,"officeRoomLogoImage");
                pairs[5] = new Pair<View,String>(createOfficeNameText,"officeRoomText");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(CreateOfficeActivity.this,pairs);
                startActivity(intent,options.toBundle());
            }
        },SPLASH_DURATION);
    }

    private boolean checkConditions(String text_office_name, String text_office_id, String text_office_type) {
        if(TextUtils.isEmpty(text_office_name) && TextUtils.isEmpty(text_office_id) && TextUtils.isEmpty(text_office_type)){
            Toast.makeText(this, "Enter Office Name, Id and Type", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(TextUtils.isEmpty(text_office_name) && TextUtils.isEmpty(text_office_id)){
            Toast.makeText(this, "Enter Office Name and Id ", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(TextUtils.isEmpty(text_office_name) && TextUtils.isEmpty(text_office_type)){
            Toast.makeText(this, "Enter Office Name and Type", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(TextUtils.isEmpty(text_office_id) && TextUtils.isEmpty(text_office_type)){
            Toast.makeText(this, "Enter Office Id and Type", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(TextUtils.isEmpty(text_office_name)) {
            Toast.makeText(this, "Enter Office Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(TextUtils.isEmpty(text_office_id)) {
            Toast.makeText(this, "Enter Office Id", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(TextUtils.isEmpty(text_office_type)) {
            Toast.makeText(this, "Enter Office Type", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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

        multiAutoCompleteTextView = findViewById(R.id.set_communication_text_id);

    }
}