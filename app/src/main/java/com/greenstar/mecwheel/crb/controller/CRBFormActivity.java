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
import android.widget.AdapterView;
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

public class CRBFormActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    AppDatabase db =null;

    EditText etVisitDate, etClientName, etHusbandName, etAddress, etContact, etMarriageDuration, etNoOfChildren, etNumberOfAbortions, etCurrentUseYear;

    TextView tvProviderName, tvProviderCode;

    Spinner spReferredBy, spClientAge, spIPCReferralStatus, spCurrentMethod, spTimingFPService, spServiceType;

    RadioGroup rgCanWeContact;
    RadioButton rbCanWeContactYes, rbCanWeContactNo;

    RadioGroup rgIPCReferralStatus;
    RadioButton rbIPCReferralStatusFirst, rbIPCReferralStatusFollowup;

    RadioGroup rgIsEverUser;
    RadioButton rbIsEverUserYes, rbIsEverUserNo;

    RadioGroup rgNotInUse;
    RadioButton rbNotInUseMore, rbNotInUseLess;

    RadioGroup rgIsCurrentUser;
    RadioButton rbIsCurrentUserYes, rbIsCurrentUserNo;

    RadioGroup rgIsWithin12Months;
    RadioButton rbIsWithin12MonthsYes, rbIsWithin12MonthsNo;

    Button btnSubmit;

    DatePickerDialog.OnDateSetListener date = null;
    final Calendar myCalendar = Calendar.getInstance();

    TableRow trIPCReferralStatus, trNotInUse, trPeriodOfCurrentYears, trCurrentMethod, trIsMethodUseIn12Months;

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

        tvProviderCode = findViewById(R.id.tvProviderCode);
        tvProviderName = findViewById(R.id.tvProviderName);

        etClientName = findViewById(R.id.etClientName);
        etHusbandName = findViewById(R.id.etHusbandName);

        spClientAge = findViewById(R.id.spClientAge);

        etAddress = findViewById(R.id.etAddress);
        etContact = findViewById(R.id.etContact);

        rgCanWeContact = findViewById(R.id.rgCanWeContact);
        rbCanWeContactYes = findViewById(R.id.rbCanWeContactYes);
        rbCanWeContactNo = findViewById(R.id.rbCanWeContactNo);

        etMarriageDuration = findViewById(R.id.etMarriageDuration);
        etNoOfChildren = findViewById(R.id.etNoOfCurrentChildren);
        etNumberOfAbortions = findViewById(R.id.etNoOfAbortions);

        spReferredBy = findViewById(R.id.spIPCReferralStatus);

        spIPCReferralStatus = findViewById(R.id.spIPCReferralStatus);

        rgIPCReferralStatus = findViewById(R.id.rgIPCReferralStatus);
        rbIPCReferralStatusFirst = findViewById(R.id.rbIPCReferralStatusFirst);
        rbIPCReferralStatusFollowup = findViewById(R.id.rbIPCReferralStatusFollowup);

        rgIsEverUser = findViewById(R.id.rgIsEverUser);
        rbIsEverUserYes = findViewById(R.id.rbIsEverUserYes);
        rbIsEverUserNo = findViewById(R.id.rbIsEverUserNo);

        rgNotInUse = findViewById(R.id.rgNotInUse);
        rbNotInUseMore = findViewById(R.id.rbNotInUseMore);
        rbNotInUseLess = findViewById(R.id.rbNotInUseLess);

        rgIsCurrentUser = findViewById(R.id.rgIsCurrentUser);
        rbIsCurrentUserYes = findViewById(R.id.rbIsCurrentUserYes);
        rbIsCurrentUserNo = findViewById(R.id.rbIsCurrentUserNo);
        rgIsCurrentUser.setOnCheckedChangeListener(this);

        etCurrentUseYear = findViewById(R.id.etCurrentMethodYears);

        spCurrentMethod = findViewById(R.id.spCurrentMethod);
        spTimingFPService = findViewById(R.id.spTimingFPService);
        spServiceType = findViewById(R.id.spServiceType);
        spServiceType.setOnItemSelectedListener(this);

        rgIsWithin12Months = findViewById(R.id.rgIsWithin12Months);
        rbIsWithin12MonthsYes = findViewById(R.id.rbIsWithin12MonthsYes);
        rbIsWithin12MonthsNo = findViewById(R.id.rbIsWithin12MonthsNo);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        trIPCReferralStatus = findViewById(R.id.trIPCReferralStatus);
        trNotInUse = findViewById(R.id.trNotInUse);
        trPeriodOfCurrentYears = findViewById(R.id.trPeriodOfCurrentYears);
        trCurrentMethod = findViewById(R.id.trCurrentMethod);
        trIsMethodUseIn12Months = findViewById(R.id.trIsMethodUseIn12Months);

        rgIsEverUser.setOnCheckedChangeListener(this);

        spReferredBy.setOnItemSelectedListener(this);
    }

    private void populateForm(){

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
        String providerCode = shared.getString("code", "");
        tvProviderName.setText(providerName);
        tvProviderCode.setText(providerCode);

        List<DropdownCRBData> dropdownCRBDataReferredBy = new ArrayList<>();
        List<DropdownCRBData> dropdownCRBDataClientAge = new ArrayList<>();
        List<DropdownCRBData> dropdownCRBDataCurrentMethod = new ArrayList<>();
        List<DropdownCRBData> dropdownCRBDataTimingFPUser = new ArrayList<>();
        List<DropdownCRBData> dropdownCRBDataServiceType = new ArrayList<>();

        DropdownCRBData clientAgeDummy = new DropdownCRBData();
        clientAgeDummy.setDetailEnglish("Client Age");
        clientAgeDummy.setId(0);

        DropdownCRBData referredByDummy = new DropdownCRBData();
        referredByDummy.setDetailEnglish("Referred By");
        referredByDummy.setId(0);

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
        DropdownCRBData dataReferredBy = (DropdownCRBData)spReferredBy.getSelectedItem();
        form.setReferredBy(dataReferredBy.getDetailEnglish());

        DropdownCRBData data = (DropdownCRBData)spClientAge.getSelectedItem();
        form.setClientAge(data.getDetailEnglish());

        DropdownCRBData dataServiceType = (DropdownCRBData)spServiceType.getSelectedItem();
        form.setServiceType(dataServiceType.getDetailEnglish());

        DropdownCRBData dataTimingFPService = (DropdownCRBData)spTimingFPService.getSelectedItem();
        form.setTimingOfService(dataTimingFPService.getDetailEnglish());

        DropdownCRBData dataCurrentMethod = (DropdownCRBData)spCurrentMethod.getSelectedItem();
        form.setCurrentMethod(dataCurrentMethod.getDetailEnglish());

        form.setProviderCode(tvProviderCode.getText().toString());
        form.setProviderName(tvProviderName.getText().toString());
        form.setClientName(etClientName.getText().toString());
        form.setHusbandName(etHusbandName.getText().toString());
        form.setAddress(etAddress.getText().toString());
        form.setContactNumber(etContact.getText().toString());
        form.setAddress(etAddress.getText().toString());

        if(rbCanWeContactYes.isChecked()){
            form.setCanWeContact(1);
        }else{
            form.setCanWeContact(2);
        }

        form.setDurationOfMarriage(Double.valueOf(etMarriageDuration.getText().toString()));
        form.setNoOfChildren(Integer.valueOf(etNoOfChildren.getText().toString()));
        form.setNumberOfAbortion(Integer.valueOf(etNumberOfAbortions.getText().toString()));

        if(rbIPCReferralStatusFirst.isChecked()){
            form.setIpcReferralStatus(1);
        }else{
            form.setIpcReferralStatus(2);
        }

        if(rbIsEverUserYes.isChecked()){
            form.setIsEverUser(1);
        }else{
            form.setIsEverUser(2);
        }

        if(rbNotInUseMore.isChecked()){
            form.setMethodNotInUse(1);
        }else{
            form.setMethodNotInUse(2);
        }

        if(rbIsCurrentUserYes.isChecked()){
            form.setIsCurrentUser(1);
        }else{
            form.setIsCurrentUser(0);
        }

        if(rbIsCurrentUserYes.isChecked()){
            form.setIsCurrentUser(1);
        }else{
            form.setIsCurrentUser(0);
        }

        form.setCurrentUseYear(Double.valueOf(etCurrentUseYear.getText().toString()));
        form.setVisitDate(etVisitDate.getText().toString());

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
        if(group.getId()==R.id.rgIsEverUser){
            if(rbIsEverUserYes.isChecked()){
                trNotInUse.setVisibility(View.VISIBLE);
            }else{
                trNotInUse.setVisibility(View.GONE);
            }
        }else if(group.getId() == R.id.rgIsCurrentUser){
            if(rbIsCurrentUserYes.isChecked()){
                trPeriodOfCurrentYears.setVisibility(View.VISIBLE);
                trCurrentMethod.setVisibility(View.VISIBLE);
            }else{
                trPeriodOfCurrentYears.setVisibility(View.GONE);
                trCurrentMethod.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        DropdownCRBData dropdownCRBData = new DropdownCRBData();
        dropdownCRBData = (DropdownCRBData) parent.getSelectedItem();

        if(dropdownCRBData.getCategory()!=null && dropdownCRBData.getCategory().equals("Referred By")){
            if(dropdownCRBData.getDetailEnglish().equals("IPC-Community Educator")){
                trIPCReferralStatus.setVisibility(View.VISIBLE);
            }else{
                trIPCReferralStatus.setVisibility(View.GONE);
            }
        }else if(dropdownCRBData.getDetailEnglish()!=null){
            if(dropdownCRBData.getDetailEnglish().equals("Service taken on this visit")){
                trIsMethodUseIn12Months.setVisibility(View.GONE);
            }else{
                trIsMethodUseIn12Months.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
