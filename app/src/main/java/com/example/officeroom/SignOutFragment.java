package com.example.officeroom;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignOutFragment extends Fragment {
    Button sign_out;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setStatusBarColor(getResources().getColor(R.color.white));
        View view = inflater.inflate(R.layout.fragment_sign_out, container, false);
        findAllId(view);

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(),"Signed Out Succesfully",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void findAllId(View view) {
        sign_out = view.findViewById(R.id.signOut);
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