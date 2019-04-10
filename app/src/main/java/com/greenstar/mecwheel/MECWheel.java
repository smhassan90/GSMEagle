package com.greenstar.mecwheel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

public class MECWheel extends AppCompatActivity implements View.OnTouchListener {
    ImageView imgMECWheel = null;
    ImageView imgMECWheelFront = null;

    double fingerRotation;
    double newFingerRotation;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactorBackground = 1.41f;
    private float mScaleFactorForeground = 1.05f;
    private ImageView mImageView;
    float viewRotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mec_wheel);
        imgMECWheel = findViewById(R.id.btnMecWheel);
        imgMECWheelFront = findViewById(R.id.btnMecWheel);

        imgMECWheel.setOnTouchListener(this);
        imgMECWheelFront.setOnTouchListener(this);

        imgMECWheel.setScaleX(mScaleFactorBackground);
        imgMECWheel.setScaleY(mScaleFactorBackground);

        imgMECWheelFront.setScaleX(mScaleFactorForeground);
        imgMECWheelFront.setScaleY(mScaleFactorForeground);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final float x = event.getX();
        final float y = event.getY();

        final float xc = imgMECWheel.getWidth()/2;
        final float yc = imgMECWheel.getHeight()/2;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                viewRotation = imgMECWheel.getRotation();
                fingerRotation = Math.toDegrees(Math.atan2(x - xc, yc - y));
                break;
            case MotionEvent.ACTION_MOVE:
                newFingerRotation = Math.toDegrees(Math.atan2(x - xc, yc - y));
                imgMECWheel.setRotation((float)(viewRotation + newFingerRotation - fingerRotation));
                break;
            case MotionEvent.ACTION_UP:
                fingerRotation = newFingerRotation = 0.0f;
                break;
        }

        return true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
}
