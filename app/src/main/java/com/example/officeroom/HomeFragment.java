package com.example.officeroom;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    String office_id,rank_id,office_name,rank_name;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    RecyclerView recyclerView;
    DataAdapter dataAdapter;
    List <AnnouncementsModelClass> announcementList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setStatusBarColor(getResources().getColor(R.color.white));
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findAllId(view);

        announcementList = new ArrayList<>();

        dataAdapter = new DataAdapter(announcementList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(dataAdapter);

        Bundle data = getArguments();
        if(data!=null){
            office_name = data.getString("office_name_string");
            rank_name = data.getString("rank_name_string");
            office_id = data.getString("text_office_id");
            rank_id = data.getString("text_rank_id");
        }
        
        receievedAnnouncements();

        return view;
    }

    private void receievedAnnouncements() {
        db.collection(office_id).document(rank_id).collection("ANNOUNCEMENTS").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange dc : value.getDocumentChanges()){
//                    String title;
//                    title = dc.getDocument().getString("Title");
//                    Log.d(TAG, "onEvent: "+title);

                    AnnouncementsModelClass announcementsModelClass = dc.getDocument().toObject(AnnouncementsModelClass.class);
                    announcementList.add(announcementsModelClass);

                    dataAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void findAllId(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewID);
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