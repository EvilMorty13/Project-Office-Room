package com.example.officeroom;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class AddPostFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;

    String office_id,rank_id,office_name,rank_name,announcement_by;

    TextInputLayout addTitle,addAnnouncement;
    Button fragment_announcement_button;
    TextView setFragOfficeName,setFragRankName;

    MultiAutoCompleteTextView multiAutoCompleteTextView;
    ArrayList<String> communication_partner;
    ArrayAdapter<String> arrayAdapter;

    ArrayList<String> announcements_to_list = new ArrayList<>();

    ArrayList<Integer> selected_item_list = new ArrayList<>();



    String[] selected_array;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setStatusBarColor(getResources().getColor(R.color.backgroundColor));

        View view = inflater.inflate(R.layout.fragment_add_post, container, false);
        findAllId(view);

        Bundle data = getArguments();
        if(data!=null){
            office_name = data.getString("office_name_string");
            rank_name = data.getString("rank_name_string");
            office_id = data.getString("text_office_id");
            rank_id = data.getString("text_rank_id");
        }
        setFragOfficeName.setText(office_name);
        setFragRankName.setText(rank_name);

        String userID = auth.getCurrentUser().getUid();

        db.collection("USER ID").document(userID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            announcement_by = documentSnapshot.getString("NAME");
                            announcement_by = announcement_by + ", " + rank_name;
                        }
                    }
                });

        communication_partner = new ArrayList<>();
        db.collection(office_id).document(rank_id).collection("INFO").document("COMMUNICATION").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            communication_partner = (ArrayList<String>) documentSnapshot.get("Communications");
                            selected_array = new String[communication_partner.size()];
                            for(int j=0;j<communication_partner.size();j++){
                                selected_array[j] = communication_partner.get(j);
                            }
                            arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.list_item,selected_array);
                            multiAutoCompleteTextView.setAdapter(arrayAdapter);
                            multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Server problem", Toast.LENGTH_SHORT).show();
                    }
                });


        multiAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(!selected_item_list.contains(i)){
                    selected_item_list.add(i);
                    Collections.sort(selected_item_list);
                }

                StringBuilder stringBuilder = new StringBuilder();
                for(int j=0;j<selected_item_list.size();j++){
                    String temp_string = selected_array[selected_item_list.get(j)];
                    db.collection(office_id).document("RANK ID INFO").collection(temp_string).document(temp_string).get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if(documentSnapshot.exists()){
                                                String temp_val = documentSnapshot.getString(temp_string);
                                                if(!announcements_to_list.contains(temp_val)){
                                                    announcements_to_list.add(temp_val);
                                                }
                                            }else{
                                                Toast.makeText(getActivity(), "Rank doesn't exist", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                    stringBuilder.append(temp_string);
                    if(j!=selected_item_list.size()-1) stringBuilder.append(", ");
                }
                multiAutoCompleteTextView.setText(stringBuilder.toString());
                multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            }
        });

        fragment_announcement_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_title = addTitle.getEditText().getText().toString();
                String text_announcement = addAnnouncement.getEditText().getText().toString();

                Timestamp timestamp = Timestamp.now();

                HashMap <String,Object> announcements = new HashMap<>();
                announcements.put("Title",text_title);
                announcements.put("Announcements",text_announcement);
                announcements.put("From", announcement_by);
                announcements.put("Time",timestamp.toString());

                for(String str : announcements_to_list){
                    db.collection(office_id).document(str).collection("ANNOUNCEMENTS").document(timestamp.toString()).set(announcements)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });


        return view;
    }

    private void findAllId(View view) {

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        setFragOfficeName = view.findViewById(R.id.set_office_name_id);
        setFragRankName = view.findViewById(R.id.set_rank_name_id);

        addTitle = view.findViewById(R.id.fragment_add_post_title_id);
        addAnnouncement = view.findViewById(R.id.fragment_add_post_announcement_id);

        fragment_announcement_button = view.findViewById(R.id.fragment_announcement_button_id);


        multiAutoCompleteTextView = view.findViewById(R.id.fragment_select_announce_to_id);
    }


    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Activity activity = getActivity();
            if (activity != null) {
                activity.getWindow().setStatusBarColor(color);
            }
        }
    }
}