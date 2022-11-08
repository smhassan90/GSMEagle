package com.greenstar.eagle.controller.IPCForms;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.greenstar.eagle.R;
import com.greenstar.eagle.controller.Codes;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.Dashboard;
import com.greenstar.eagle.utils.PartialSyncResponse;
import com.greenstar.eagle.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Notification extends AppCompatActivity implements View.OnClickListener, PartialSyncResponse {
    WebView wvNotification;
    AppDatabase db = null;
    EditText etSelectDate;
    LinearLayout llSelectDate;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener selectDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        wvNotification = findViewById(R.id.wvNotification);
        llSelectDate = findViewById(R.id.llSelectDate);
        etSelectDate = findViewById(R.id.etSelectDate);
        llSelectDate.setOnClickListener(this);
        etSelectDate.setOnClickListener(this);
        updateDate();
        selectDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.llSelectDate || v.getId() == R.id.etSelectDate){
            new DatePickerDialog(this, selectDate, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        }
    }

    private void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.notificationFormat);

        etSelectDate.setText(sdf.format(myCalendar.getTime()));
        callAPI();
    }

    private void callAPI(){
        if(Util.isNetworkAvailable(this)){
            Util util = new Util();
            util.setPSResponse(this);
            util.getNotification(this,etSelectDate.getText().toString());
        }else{
            Toast.makeText(this,"Please connect to the internet and try again.",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void response(String responseCode, String PSCode, String message) {
        wvNotification.loadData(message, "text/html", null);
    }
}
