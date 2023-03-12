package com.example.officeroom;

import static java.util.TimeZone.getDefault;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUserMetadata;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.sql.Timestamp;
import java.util.Locale;

public class ProfileFragment extends Fragment {
    TextView name;

    TextView email;

    TextView created;

    TextView rank_name;

    TextView institute_name;

    TextView last_login;

    String office_name_string,rank_name_string,office_id_string,rank_id_string,user_name_string;

    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setStatusBarColor(getResources().getColor(R.color.backgroundColor));
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findAllId(view);

        Bundle data = getArguments();
        if(data!=null){
            office_name_string = data.getString("office_name_string");
            rank_name_string = data.getString("rank_name_string");
            office_id_string = data.getString("text_office_id");
            rank_id_string = data.getString("text_rank_id");
            user_name_string = data.getString("userName");
        }

        name.setText(user_name_string);
        rank_name.setText(rank_name_string);
        institute_name.setText(office_name_string);



        String em = auth.getCurrentUser().getEmail();
        email.setText(em);



        long ts_creation_time = auth.getCurrentUser().getMetadata().getCreationTimestamp();
        Date creation_date = (new Date(ts_creation_time));
        SimpleDateFormat creation_sfd = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss",Locale.getDefault());
        String creation_time = creation_sfd.format(creation_date);
        created.setText("Account Created : "+creation_time);





        long ts_latest_login = auth.getCurrentUser().getMetadata().getLastSignInTimestamp();
        Date latest_login_date = (new Date(ts_latest_login));
        SimpleDateFormat latest_login_sfd = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss",Locale.getDefault());
        String latest_login = latest_login_sfd.format(latest_login_date);
        last_login.setText("Last Login : "+latest_login);



        return view;
    }


    private void findAllId(View view) {
        auth = FirebaseAuth.getInstance();
        name = view.findViewById(R.id.profile_name);
        email = view.findViewById(R.id.profile_email);
        created = view.findViewById(R.id.profile_created);
        rank_name = view.findViewById(R.id.profile_rank);
        institute_name = view.findViewById(R.id.profile_institute);
        last_login = view.findViewById(R.id.profile_last_login);
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