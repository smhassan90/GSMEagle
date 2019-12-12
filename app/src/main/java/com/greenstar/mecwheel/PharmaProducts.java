package com.greenstar.mecwheel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PharmaProducts extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String[] counselingCards = { "Enofer Dosage Calculator","Sign & Symptom of Anemia",""};
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final int titleLength = 25;
    private CounselingCardsFragment.OnFragmentInteractionListener mListener;

    public PharmaProducts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment .
     */
    // TODO: Rename and change types and number of parameters
    public static PharmaProducts newInstance(String param1, String param2) {
        PharmaProducts fragment = new PharmaProducts();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_counseling, container, false);
        LinearLayout llMainCounseling = view.findViewById(R.id.llMainCounseling);
        createOptions(llMainCounseling);
        getActivity().setTitle("Enofer");
        return view;
    }

    @SuppressLint("ResourceType")
    private void createOptions(LinearLayout llMainCounseling) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int cardIndex = 0;
        for(int i=0; i<counselingCards.length/3;i++) {
            View option = (View) inflater.inflate(R.layout.menu_item,
                    null);

            LinearLayout llMenuItem = option.findViewById(R.id.llMenuItem);
            LinearLayout llMenuItemInside = null;
            for(int j=0;j<3;j++){
                TextView tvCardName = null;
                if(j==0){
                    tvCardName = llMenuItem.findViewById(R.id.tvCardName1);
                    llMenuItemInside = llMenuItem.findViewById(R.id.llMenuItem1);
                }
                else if(j==1 && cardIndex == 1){
                    tvCardName = llMenuItem.findViewById(R.id.tvCardName2);
                    llMenuItemInside = llMenuItem.findViewById(R.id.llMenuItem2);

                }
                else if(j==2 && cardIndex == 2){
                    tvCardName = llMenuItem.findViewById(R.id.tvCardName3);
                    llMenuItemInside = llMenuItem.findViewById(R.id.llMenuItem3);
                    llMenuItemInside.setVisibility(View.INVISIBLE);

                }
                else if(j==2){
                    tvCardName = llMenuItem.findViewById(R.id.tvCardName3);
                    llMenuItemInside = llMenuItem.findViewById(R.id.llMenuItem3);

                }
                String name="";

                if(counselingCards[cardIndex].length()>titleLength){
                    name = counselingCards[cardIndex].substring(0, Math.min(counselingCards[cardIndex].length(), titleLength));
                    name+="...";
                }else{
                    name=counselingCards[cardIndex];
                }

                tvCardName.setText(name==null?"":name);
                llMenuItemInside.setId(cardIndex);
                llMenuItemInside.setOnClickListener(this);
                cardIndex++;
            }

            llMainCounseling.addView(llMenuItem);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        if(v.getId()==0){
            Intent myIntent = new Intent(getActivity(), EnoferDosageCalculator.class);
            myIntent.putExtra("id", v.getId()); //Optional parameters
            myIntent.putExtra("title", counselingCards[v.getId()]);
            getActivity().startActivity(myIntent);
        }else if(v.getId()==1){
            Intent myIntent = new Intent(getActivity(), SignAndSymptoms.class);
            myIntent.putExtra("id", v.getId()); //Optional parameters
            myIntent.putExtra("title", counselingCards[v.getId()]);
            getActivity().startActivity(myIntent);
        }


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
