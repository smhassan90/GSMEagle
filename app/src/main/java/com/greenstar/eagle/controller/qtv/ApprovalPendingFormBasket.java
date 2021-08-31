package com.greenstar.eagle.controller.qtv;

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
import com.greenstar.eagle.adapters.ApprovalPendingFormAdapter;
import com.greenstar.eagle.db.AppDatabase;
import com.greenstar.eagle.model.approval.ApprovalQTVForm;

import java.util.ArrayList;
import java.util.List;

public class ApprovalPendingFormBasket extends Fragment {
    View view= null;
    ListView lvBasket;
    ApprovalPendingFormAdapter basketAdapter;
    AppDatabase db =null;
    List<ApprovalQTVForm> qtvForms = new ArrayList<>();

    private ApprovalPendingFormBasket.OnFragmentInteractionListener mListener;
    Activity activity;

    public ApprovalPendingFormBasket() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private List<ApprovalQTVForm> getData(){
        List<ApprovalQTVForm> qtvForms = db.getApprovalQTVFormDAO().getAllPendingForms();

        return qtvForms ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pending_forms_basket, container, false);
        db = AppDatabase.getAppDatabase(getActivity());
        lvBasket = view.findViewById(R.id.lvBasket);

        List<ApprovalQTVForm> qtvForms = new ArrayList<>();
        qtvForms = getData();
        basketAdapter = new ApprovalPendingFormAdapter(getActivity(),qtvForms);
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
        if (context instanceof ApprovalPendingFormBasket.OnFragmentInteractionListener) {
            mListener = (ApprovalPendingFormBasket.OnFragmentInteractionListener) context;
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
