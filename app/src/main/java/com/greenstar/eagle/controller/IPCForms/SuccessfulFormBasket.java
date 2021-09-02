package com.greenstar.eagle.controller.IPCForms;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.greenstar.eagle.R;
import com.greenstar.eagle.adapters.crf.submittedforms.SuccessfulFormAdapter;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.CRForm;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Syed Muhammad Hassan
 * 16th July, 2019
 */

public class SuccessfulFormBasket extends Fragment {
    View view= null;
    ListView lvBasket;
    SuccessfulFormAdapter basketAdapter;
    AppDatabase db =null;
    List<CRForm> forms = new ArrayList<>();

    private SuccessfulFormBasket.OnFragmentInteractionListener mListener;
    Activity activity;

    public SuccessfulFormBasket() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private List<CRForm> getData(){
        List<CRForm> forms = db.getCrFormDAO().getAllSuccessfulForms();

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
        basketAdapter = new SuccessfulFormAdapter(getActivity(),forms);
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
        if (context instanceof PendingFormsBasket.OnFragmentInteractionListener) {
            mListener = (SuccessfulFormBasket.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        List<CRForm> forms = new ArrayList<>();
        if(lvBasket!=null) {
            if (db == null)
                db = AppDatabase.getAppDatabase(getActivity());
            forms = getData();
            basketAdapter = new SuccessfulFormAdapter(getActivity(), forms);
            lvBasket.setAdapter(basketAdapter);
            basketAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
