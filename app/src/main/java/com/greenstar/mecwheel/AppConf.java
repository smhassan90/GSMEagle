package com.greenstar.mecwheel;

import java.util.Calendar;

public class AppConf {
    public static boolean isOctober(){
        boolean isOctober = false;

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        month++;
        if(month==10 && year==2020){
            isOctober = true;
        }else{
            isOctober=false;
        }

        return isOctober;
    }

    public static int getHeaderDarkColor(){
        int color=0;

        if(isOctober()){
            color = R.color.whiteColor;
        }else{
            color=R.color.maroon;
        }

        return color;
    }

    public static String getThemeLightColor(){
        String color="";

        if(isOctober()){
            color="#ea7b92";
        }else{
            color="#d1362e";
        }

        return color;
    }
}
