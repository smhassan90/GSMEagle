package com.greenstar.eagle.controller.qat;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.greenstar.eagle.R;
import com.greenstar.eagle.adapters.qat.QATSuccessfulFormAdapter;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.QATFormHeader;

import java.util.ArrayList;
import java.util.List;

public class QATSuccessfulFormBasket extends Fragment {
    View view= null;
    ListView lvBasket;
    QATSuccessfulFormAdapter basketAdapter;
    AppDatabase db =null;
    List<QATFormHeader> forms = new ArrayList<>();
    Activity mActivity;

    private QATSuccessfulFormBasket.OnFragmentInteractionListener mListener;
    Activity activity;

    public QATSuccessfulFormBasket() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private List<QATFormHeader> getData(){
        if(db==null)
            db = AppDatabase.getAppDatabase(getActivity());
        List<QATFormHeader> forms = db.getQatFormHeaderDAO().getAllSuccessful();

        return forms ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pending_forms_basket, container, false);
        db = AppDatabase.getAppDatabase(getActivity());
        lvBasket = view.findViewById(R.id.lvBasket);

        List<QATFormHeader> qatFormHeaders = new ArrayList<>();
        qatFormHeaders = getData();
        if(getActivity()!=null){
            mActivity=getActivity();
        }
        basketAdapter = new QATSuccessfulFormAdapter(mActivity,qatFormHeaders);
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
        if (context instanceof QATPendingFormsBasket.OnFragmentInteractionListener) {
            mListener = (QATSuccessfulFormBasket.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        if(lvBasket!=null){
            List<QATFormHeader> qatForms = new ArrayList<>();
            qatForms = getData();
            mActivity =(Activity) context;
            basketAdapter = new QATSuccessfulFormAdapter(mActivity, qatForms);
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
