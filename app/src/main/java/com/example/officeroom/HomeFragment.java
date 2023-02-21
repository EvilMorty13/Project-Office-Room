package com.example.officeroom;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    TextView title;
    TextView announcement;
    TextView from;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setStatusBarColor(getResources().getColor(R.color.white));
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findAllId(view);



        String ti = "This is a title . sdfnn sodfjo osdfoisdf oisdjfosfj sodijfsodf  sodifjo" +
                "sdfnkjsdf skjdnfjsdf  sdfnsjdf ksdfsdkf oidsfjsdf";
        title.setMovementMethod(new ScrollingMovementMethod());
        title.setText("Title : " +ti);

        String am = "This is an announcement. Very urgent meeting." +
                "Come quickly in office at 10 am. sldfk lsdfk lsdf lksdf ksdfm skdf sdnf sdikfk" +
                "sdfkknsdf lsdkfjskf ieojrof  dkfsdfn eorie  sdfn oweirj sdfn slkdfskldf lskdfnsdklf" +
                "sdlfksdfkl lsdkfjsdklf sdfl fsdkfnskldfsd lfldkfjsdfljwoifsd fsdlkfweoif " +
                "sdfksdlkfnsdlkfnsdf ljfsdklfsd lkfsdiofjweiofkfsdklf sf" +
                "dfnsdfsdfweuiofwefmndlkfsdlkfmsdf " +
                "fsdkfslkdfslfjweoirjweopirjwefjdfl;sdjfksdjflskdfjsdkfjweiof";
        announcement.setMovementMethod(new ScrollingMovementMethod());
        announcement.setText("Announcement : "+am);

        String fr = "Mokles";
        from.setMovementMethod(new ScrollingMovementMethod());
        from.setText("From : "+fr);


        return view;
    }

    private void findAllId(View view) {
        title = view.findViewById(R.id.home_title);
        announcement = view.findViewById(R.id.home_announcement);
        from = view.findViewById(R.id.home_from);

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