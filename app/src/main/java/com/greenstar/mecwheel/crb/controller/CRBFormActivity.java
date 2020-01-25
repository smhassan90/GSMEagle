package com.greenstar.mecwheel.crb.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.greenstar.mecwheel.R;
import com.greenstar.mecwheel.crb.adapter.ClientAgeAdapter;
import com.greenstar.mecwheel.crb.adapter.CurrentMethodAdapter;
import com.greenstar.mecwheel.crb.adapter.ReferredByAdapter;
import com.greenstar.mecwheel.crb.adapter.ServiceTypeAdapter;
import com.greenstar.mecwheel.crb.adapter.TimingFPServiceAdapter;
import com.greenstar.mecwheel.crb.db.AppDatabase;
import com.greenstar.mecwheel.crb.model.CRBForm;
import com.greenstar.mecwheel.crb.model.DropdownCRBData;
import com.greenstar.mecwheel.crb.utils.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CRBFormActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    AppDatabase db =null;

    EditText etVisitDate, etClientName, etAddress, etContact, etNoOfChildren;

    TextView tvProviderName;

    Spinner spReferredBy, spClientAge, spCurrentMethod, spTimingFPService, spServiceType;

    RadioGroup rgIsCurrentUser;
    RadioButton rbIsCurrentUserYes, rbIsCurrentUserNo;

    RadioGroup rgIsNeverUser;
    RadioButton rbIsNeverUserYes, rbIsNeverUserNo;

    RadioGroup rgIsEverUser;
    RadioButton rbIsEverUserYes, rbIsEverUserNo;

    RadioGroup rgIsWithin12Months;
    RadioButton rbIsWithin12MonthsYes, rbIsWithin12MonthsNo;

    Button btnSubmit;

    DatePickerDialog.OnDateSetListener date = null;
    final Calendar myCalendar = Calendar.getInstance();

    TableRow trCurrentUser, trNeverUser, trEverUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crb_form_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeVariables();

        populateForm();
    }

    private void initializeVariables(){
        etVisitDate = findViewById(R.id.etVisitDate);
        etVisitDate.setOnClickListener(this);

        etClientName = findViewById(R.id.etClientName);
        etAddress = findViewById(R.id.etAddress);
        etContact = findViewById(R.id.etContact);
        etNoOfChildren = findViewById(R.id.etNoOfCurrentChildren);

        spReferredBy = findViewById(R.id.spReferredBy);
        spClientAge = findViewById(R.id.spClientAge);
        spCurrentMethod = findViewById(R.id.spCurrentMethod);
        spTimingFPService = findViewById(R.id.spTimingFPService);
        spServiceType = findViewById(R.id.spServiceType);

        rgIsCurrentUser = findViewById(R.id.rgIsCurrentUser);
        rbIsCurrentUserYes = findViewById(R.id.rbIsCurrentUserYes);
        rbIsCurrentUserNo = findViewById(R.id.rbIsCurrentUserNo);
        rgIsCurrentUser.setOnCheckedChangeListener(this);

        rgIsNeverUser = findViewById(R.id.rgIsNeverUser);
        rbIsNeverUserYes = findViewById(R.id.rbIsNeverUserYes);
        rbIsNeverUserNo = findViewById(R.id.rbIsNeverUserNo);
        rgIsNeverUser.setOnCheckedChangeListener(this);

        rgIsEverUser = findViewById(R.id.rgIsEverUser);
        rbIsEverUserYes = findViewById(R.id.rbIsEverUserYes);

        rgIsWithin12Months = findViewById(R.id.rgIsWithin12Months);
        rbIsWithin12MonthsYes = findViewById(R.id.rbIsWithin12MonthsYes);
        rbIsWithin12MonthsNo = findViewById(R.id.rbIsWithin12MonthsNo);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        tvProviderName = findViewById(R.id.tvProviderName);

        trCurrentUser = findViewById(R.id.trCurrentUser);
        trNeverUser = findViewById(R.id.trNeverUser);
        trEverUser = findViewById(R.id.trEverUser);

    }

    private void populateForm(){

        trNeverUser.setVisibility(View.GONE);
        trEverUser.setVisibility(View.GONE);

        updateVisitDate();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateVisitDate();
            }

        };

        SharedPreferences shared = getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String providerName = shared.getString("name", "");
        tvProviderName.setText(providerName);

        List<DropdownCRBData> dropdownCRBDataReferredBy = new ArrayList<>();
        List<DropdownCRBData> dropdownCRBDataClientAge = new ArrayList<>();
        List<DropdownCRBData> dropdownCRBDataCurrentMethod = new ArrayList<>();
        List<DropdownCRBData> dropdownCRBDataTimingFPUser = new ArrayList<>();
        List<DropdownCRBData> dropdownCRBDataServiceType = new ArrayList<>();

        DropdownCRBData referredByDummy = new DropdownCRBData();
        referredByDummy.setDetailEnglish("Referred By");
        referredByDummy.setId(0);

        DropdownCRBData clientAgeDummy = new DropdownCRBData();
        clientAgeDummy.setDetailEnglish("Client Age");
        clientAgeDummy.setId(0);

        DropdownCRBData currentMethodDummy = new DropdownCRBData();
        currentMethodDummy.setDetailEnglish("Current Method");
        currentMethodDummy.setId(0);

        DropdownCRBData timingOfFPDummy = new DropdownCRBData();
        timingOfFPDummy.setDetailEnglish("When took service");
        timingOfFPDummy.setId(0);

        DropdownCRBData serviceTypeDummy = new DropdownCRBData();
        serviceTypeDummy.setDetailEnglish("Service taken on this visit");
        serviceTypeDummy.setId(0);

        try{
            db = AppDatabase.getAppDatabase(this);
            dropdownCRBDataReferredBy = db.getDropdownCRBDataDAO().getAllReferredBy();
            dropdownCRBDataClientAge = db.getDropdownCRBDataDAO().getAllClientAge();
            dropdownCRBDataCurrentMethod = db.getDropdownCRBDataDAO().getAllCurrentMethod();
            dropdownCRBDataTimingFPUser = db.getDropdownCRBDataDAO().getAllTimingFPService();
            dropdownCRBDataServiceType = db.getDropdownCRBDataDAO().getAllServiceType();
        }catch(Exception e){

        }
        dropdownCRBDataReferredBy.add(0,referredByDummy);
        ReferredByAdapter referredByAdapter = new ReferredByAdapter(this, R.layout.dropdown_layout, R.id.tvNames, dropdownCRBDataReferredBy);
        spReferredBy.setAdapter(referredByAdapter);

        dropdownCRBDataClientAge.add(0,clientAgeDummy);
        ClientAgeAdapter clientAgeAdapter = new ClientAgeAdapter(this, R.layout.dropdown_layout, R.id.tvNames, dropdownCRBDataClientAge);
        spClientAge.setAdapter(clientAgeAdapter);

        dropdownCRBDataCurrentMethod.add(0,currentMethodDummy);
        CurrentMethodAdapter currentMethodAdapter = new CurrentMethodAdapter(this, R.layout.dropdown_layout, R.id.tvNames, dropdownCRBDataCurrentMethod);
        spCurrentMethod.setAdapter(currentMethodAdapter);

        dropdownCRBDataTimingFPUser.add(0,timingOfFPDummy);
        TimingFPServiceAdapter timingFPServiceAdapter = new TimingFPServiceAdapter(this, R.layout.dropdown_layout, R.id.tvNames, dropdownCRBDataTimingFPUser);
        spTimingFPService.setAdapter(timingFPServiceAdapter);

        dropdownCRBDataServiceType.add(0,serviceTypeDummy);
        ServiceTypeAdapter serviceTypeAdapter = new ServiceTypeAdapter(this, R.layout.dropdown_layout, R.id.tvNames, dropdownCRBDataServiceType);
        spServiceType.setAdapter(serviceTypeAdapter);

    }

    private void updateVisitDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etVisitDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void submitForm(){
        CRBForm form = new CRBForm();
        form.setAddress(etAddress.getText().toString());
        DropdownCRBData dataReferredBy = (DropdownCRBData)spReferredBy.getSelectedItem();
        form.setReferredBy(dataReferredBy.getDetailEnglish());

        DropdownCRBData data = (DropdownCRBData)spClientAge.getSelectedItem();
        form.setClientAge(data.getDetailEnglish());

        DropdownCRBData dataServiceType = (DropdownCRBData)spServiceType.getSelectedItem();
        form.setServiceType(dataServiceType.getDetailEnglish());

        DropdownCRBData dataTimingFPService = (DropdownCRBData)spTimingFPService.getSelectedItem();
        form.setTimingFPService(dataTimingFPService.getDetailEnglish());

        DropdownCRBData dataCurrentMethod = (DropdownCRBData)spCurrentMethod.getSelectedItem();
        form.setCurrentMethod(dataCurrentMethod.getDetailEnglish());

        form.setAddress(etAddress.getText().toString());
        form.setClientName(etClientName.getText().toString());
        form.setContactNumber(etContact.getText().toString());
        form.setVisitDate(etVisitDate.getText().toString());
        form.setProviderCode(tvProviderName.getText().toString());
        form.setNoOfChildren(Integer.valueOf(etNoOfChildren.getText().toString()));
        form.setMethodWithin12Months(rbIsWithin12MonthsYes.isChecked()==true?1:0);
        if(rbIsCurrentUserYes.isChecked()){
            form.setFpUserCategory(Codes.currentUser);
        }else if(rbIsNeverUserYes.isChecked()){
            form.setFpUserCategory(Codes.neverUser);
        }else{
            form.setFpUserCategory(Codes.everUser);
        }
        form.setId(Util.getNextCRBFormID(this));
        AppDatabase.getAppDatabase(this).getCRBFormDAO().insert(form);

        Toast.makeText(this,"Form successfully submitted!",Toast.LENGTH_SHORT).show();
        this.finish();
    }

    private boolean isValid(){
        boolean isValid=true;

        if(spClientAge.getSelectedItemId()==0 ||
                //spReferredBy.getSelectedItemId()==0 ||
               // spCurrentMethod.getSelectedItemId()==0 ||
                spTimingFPService.getSelectedItemId()==0 ||
                spServiceType.getSelectedItemId() ==0 ||
                "".equals(etClientName.getText().toString()) ||
                "".equals(etContact.getText().toString()) ||
                "".equals(etNoOfChildren.getText().toString())){
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void onClick(View v) {


        if(v.getId()==R.id.btnSubmit){
            if(isValid()){
                new AlertDialog.Builder(this)
                        .setTitle("Save Form")
                        .setMessage("Once submitted, you will not be able to edit this form. Are you sure you want to submit?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                submitForm();

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }else{
                Toast.makeText(this,"Form is incomplete", Toast.LENGTH_SHORT).show();
            }



        }
        else if(v.getId()==R.id.etVisitDate){
            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        final Activity activity = this;
        new AlertDialog.Builder(this)
                .setTitle("Discard Form")
                .setMessage("Are you sure you want to discard this form?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(group.getId() == R.id.rgIsCurrentUser){
            if (checkedId == R.id.rbIsCurrentUserNo) {
                trNeverUser.setVisibility(View.VISIBLE);
                spCurrentMethod.setVisibility(View.VISIBLE);
            } else  if (checkedId == R.id.rbIsCurrentUserYes) {
                rgIsEverUser.clearCheck();
                rgIsNeverUser.clearCheck();
                trEverUser.setVisibility(View.GONE);
                trNeverUser.setVisibility(View.GONE);
                spCurrentMethod.setVisibility(View.GONE);
            }
        }else if(group.getId() == R.id.rgIsNeverUser){
            if (checkedId == R.id.rbIsNeverUserYes) {
                trEverUser.setVisibility(View.GONE);
                rgIsEverUser.clearCheck();
            } else  if (checkedId == R.id.rbIsNeverUserNo) {
                trEverUser.setVisibility(View.VISIBLE);
                rbIsEverUserYes.setSelected(true);
            }
        }
    }
}
