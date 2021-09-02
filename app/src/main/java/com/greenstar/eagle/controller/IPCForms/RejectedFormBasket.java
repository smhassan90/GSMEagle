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
import com.greenstar.eagle.adapters.crf.submittedforms.RejectedFormAdapter;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.CRForm;

import java.util.ArrayList;
import java.util.List;

public class RejectedFormBasket extends Fragment {
    View view= null;
    ListView lvBasket;
    RejectedFormAdapter basketAdapter;
    AppDatabase db =null;
    List<CRForm> forms = new ArrayList<>();

    private RejectedFormBasket.OnFragmentInteractionListener mListener;
    Activity activity;

    public RejectedFormBasket() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private List<CRForm> getData(){
        List<CRForm> forms = db.getCrFormDAO().getAllRejectedForms();

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
        basketAdapter = new RejectedFormAdapter(getActivity(),forms);
        basketAdapter = new RejectedFormAdapter(getActivity(),forms);
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
            mListener = (RejectedFormBasket.OnFragmentInteractionListener) context;
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            List<CRForm> forms = new ArrayList<>();
            forms = getData();
            basketAdapter = new RejectedFormAdapter(getActivity(), forms);
            lvBasket.setAdapter(basketAdapter);
            basketAdapter.notifyDataSetChanged();
        }
    }
}
