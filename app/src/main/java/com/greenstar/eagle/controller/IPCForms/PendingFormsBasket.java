package com.greenstar.eagle.controller.IPCForms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.greenstar.eagle.R;
import com.greenstar.eagle.adapters.crf.submittedforms.PendingFormAdapter;
import com.greenstar.eagle.controller.Codes;
import com.greenstar.eagle.dao.FormDeleteListener;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.CRForm;
import com.greenstar.eagle.utils.Util;
import com.greenstar.eagle.utils.WebserviceResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Syed Muhammad Hassan
 * 15th July, 2019
 */

public class PendingFormsBasket extends Fragment implements FormDeleteListener,WebserviceResponse {
    ProgressDialog progressBar = null;
    View view= null;
    ListView lvBasket;
    PendingFormAdapter basketAdapter;
    AppDatabase db =null;
    List<CRForm> forms = new ArrayList<>();

    private OnFragmentInteractionListener mListener;
    Activity activity;

    public PendingFormsBasket() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private List<CRForm> getData(){
        List<CRForm> forms = db.getCrFormDAO().getAllPendingForms();

        return forms ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pending_forms_basket, container, false);
        db = AppDatabase.getAppDatabase(getActivity());
        lvBasket = view.findViewById(R.id.lvBasket);

        List<CRForm> forms = new ArrayList<>();
        forms = getData();
        basketAdapter = new PendingFormAdapter(getActivity(),forms, this);
        lvBasket.setAdapter(basketAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void deleteQTVForm(long orderId){
        try{
            db.getCrFormDAO().deleteFormById(orderId);
            Toast.makeText(getActivity(),"QTV Form deleted",Toast.LENGTH_SHORT).show();
            basketAdapter = new PendingFormAdapter(getActivity(),getData(), this);
            lvBasket.setAdapter(basketAdapter);
            basketAdapter.notifyDataSetChanged();
        }catch (Exception e){
            Crashlytics.logException(e);
        }

    }

    @Override
    public void deleteForm(final long orderId) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this form?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteQTVForm(orderId);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    @Override
    public void SyncForm(long id) {

        if(Util.isNetworkAvailable(getActivity())){
            Util util = new Util();
            util.setResponseListener(this);
            progressBar = new ProgressDialog(getActivity());
            progressBar.setCancelable(false);//you can cancel it by pressing back button
            progressBar.setMessage("Syncing this form");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();//displays the progress bar
            util.performSingleFormSync(getActivity(),id, Codes.SINGLE_CR_FORM);

        }
    }

    @Override
    public void responseAlert(String response) {
        basketAdapter = new PendingFormAdapter(getActivity(),getData(), this);
        lvBasket.setAdapter(basketAdapter);
        basketAdapter.notifyDataSetChanged();
        progressBar.dismiss();
        Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
