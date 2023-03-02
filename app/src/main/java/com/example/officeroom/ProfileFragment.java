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

    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setStatusBarColor(getResources().getColor(R.color.white));
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findAllId(view);

        String nm = "Mokles Mia";
        //name.setMovementMethod(new ScrollingMovementMethod());
        name.setText("Name : "+nm);


        String em = auth.getCurrentUser().getEmail();
        //email.setMovementMethod(new ScrollingMovementMethod());
        email.setText("Email : "+em);

        long ts_creation_time = auth.getCurrentUser().getMetadata().getCreationTimestamp();
        Date creation_date = (new Date(ts_creation_time));
        SimpleDateFormat creation_sfd = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss",Locale.getDefault());
        String creation_time = creation_sfd.format(creation_date);
        //created.setMovementMethod(new ScrollingMovementMethod());
        created.setText("Account Created : "+creation_time);


        String rn = "CEO";
        //rank_name.setMovementMethod(new ScrollingMovementMethod());
        rank_name.setText("Rank : "+rn);

        String in = "Vivasoft";
        //institute_name.setMovementMethod(new ScrollingMovementMethod());
        institute_name.setText("Institute : "+in);

        long ts_latest_login = auth.getCurrentUser().getMetadata().getLastSignInTimestamp();
        Date latest_login_date = (new Date(ts_latest_login));
        SimpleDateFormat latest_login_sfd = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss",Locale.getDefault());
        String latest_login = latest_login_sfd.format(latest_login_date);
        //last_login.setMovementMethod(new ScrollingMovementMethod());
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