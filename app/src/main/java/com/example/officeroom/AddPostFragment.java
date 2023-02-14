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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class AddPostFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String office_id,rank_id,office_name,rank_name;

    TextView setFragOfficeName,setFragRankName;

    EditText inputTitle,inputRankId,inputAnnouncement;

    Button announce_button;

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

        announce_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text_input_title = inputTitle.getText().toString();
                String text_input_rank_id = inputRankId.getText().toString();
                String text_input_announcement = inputAnnouncement.getText().toString();

                String final_announcement = "\n"+text_input_title+"\n\n"+text_input_announcement+"\n";

                Timestamp timestamp = Timestamp.now();
                HashMap<String,Object> announcements = new HashMap<>();
                announcements.put(timestamp.toString(),final_announcement);
                db.collection(office_id).document(text_input_rank_id).collection("ANNOUNCEMENTS").document(timestamp.toString())
                        .set(announcements);
            }
        });

        return view;
    }

    private void findAllId(View view) {
        setFragOfficeName = view.findViewById(R.id.set_office_name_id);
        setFragRankName = view.findViewById(R.id.set_rank_name_id);

        announce_button = view.findViewById(R.id.announce_button_id);

        inputTitle = view.findViewById(R.id.add_post_title_id);
        inputRankId = view.findViewById(R.id.add_post_rank_id);
        inputAnnouncement = view.findViewById(R.id.add_post_announce_here_id);
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