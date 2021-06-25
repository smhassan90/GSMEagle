package com.greenstar.mecwheel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Calendar;

public class HomeFragment extends Fragment {
ImageView imgAbout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us, container, false);

        imgAbout = view.findViewById(R.id.imgHome);
        if(AppConf.isOctober()){
            imgAbout.setBackgroundResource(R.drawable.banner);
        }else{
            imgAbout.setBackgroundResource(R.drawable.home_pic);
        }


        getActivity().setTitle("About Us");
        return view;
    }
}
