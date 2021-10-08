package com.greenstar.eagle.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class ScrollableNestedListview extends ListView implements View.OnTouchListener {


    public ScrollableNestedListview(Context context) {
        super(context);
    }

    public ScrollableNestedListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableNestedListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Disallow ScrollView to intercept touch events.
                v.getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_UP:
                // Allow ScrollView to intercept touch events.
                v.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        // Handle ListView touch events.
        v.onTouchEvent(event);
        return true;
    }
}
