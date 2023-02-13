package com.example.officeroom;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class AddPostFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String office_id,rank_id,office_name,rank_name;

    TextView setFragOfficeName,setFragRankName;

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
        }
        setFragOfficeName.setText(office_name);
        setFragRankName.setText(rank_name);

        return view;
    }

    private void findAllId(View view) {
        setFragOfficeName = view.findViewById(R.id.set_office_name_id);
        setFragRankName = view.findViewById(R.id.set_rank_name_id);
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