package com.ernestosalazar.planechaseandroid.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ernestosalazar.planechaseandroid.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by esalazar on 10/14/16.
 */

public class PhenomenonFragment extends DialogFragment {

    @BindView(R.id.phenomenon_container)
    LinearLayout mImageContainer;

    @BindView(R.id.close_dialog)
    Button mCloseDialog;

    @BindView(R.id.image1)
    ImageView mImage1;

    @BindView(R.id.image2)
    ImageView mImage2;

    @BindView(R.id.image3)
    ImageView mImage3;

    @BindView(R.id.image4)
    ImageView mImage4;

    @BindView(R.id.image5)
    ImageView mImage5;

    private int mEventNumber;

    public PhenomenonFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static PhenomenonFragment newInstance(int caseNumber) {
        PhenomenonFragment frag = new PhenomenonFragment();
        frag.mEventNumber = caseNumber;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phenomenon, container, false);
        ButterKnife.bind(this, view);
        getDialog().setCanceledOnTouchOutside(false);
        mCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideImagesOnEvent();
        Picasso.with(getActivity()).load(R.drawable.loading).resize(500, 300).into(mImage1);
        Picasso.with(getActivity()).load(R.drawable.basewp).resize(500, 300).into(mImage2);
        mImage3.setVisibility(View.GONE);
        mImage4.setVisibility(View.GONE);
        mImage5.setVisibility(View.GONE);
    }

    private void hideImagesOnEvent(){
        getDialog().setCanceledOnTouchOutside(true);
        if(mEventNumber == 2){
        }else if(mEventNumber == 7){
            mImage3.setVisibility(View.GONE);
            mImage4.setVisibility(View.GONE);
            mImage5.setVisibility(View.GONE);
        }else if(mEventNumber == 34){
            mImage4.setVisibility(View.GONE);
            mImage5.setVisibility(View.GONE);
        }else if(mEventNumber == 79){
            getDialog().setCanceledOnTouchOutside(false);
            mImage2.setVisibility(View.GONE);
            mImage3.setVisibility(View.GONE);
            mImage4.setVisibility(View.GONE);
            mImage5.setVisibility(View.GONE);
        }
    }
}
