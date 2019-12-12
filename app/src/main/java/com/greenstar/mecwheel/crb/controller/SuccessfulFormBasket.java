package com.greenstar.mecwheel.crb.controller;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.greenstar.mecwheel.R;
import com.greenstar.mecwheel.crb.adapter.SuccessfulFormAdapter;
import com.greenstar.mecwheel.crb.db.AppDatabase;
import com.greenstar.mecwheel.crb.model.CRBForm;

import java.util.ArrayList;
import java.util.List;

public class SuccessfulFormBasket extends Fragment {
    View view= null;
    ListView lvBasket;
    SuccessfulFormAdapter basketAdapter;
    AppDatabase db =null;
    List<CRBForm> crbForms = new ArrayList<>();

    private SuccessfulFormBasket.OnFragmentInteractionListener mListener;
    Activity activity;

    public SuccessfulFormBasket() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private List<CRBForm> getData(){
        List<CRBForm> crbForms = db.getCRBFormDAO().getSuccessfulRBForms();

        return crbForms ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pending_forms_basket, container, false);
        db = AppDatabase.getAppDatabase(getActivity());
        lvBasket = view.findViewById(R.id.lvBasket);

        List<CRBForm> crbForms = new ArrayList<>();
        crbForms = getData();
        basketAdapter = new SuccessfulFormAdapter(getActivity(),crbForms);
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
