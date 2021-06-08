package com.example.now.UI.Fragments.Marketer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.now.Adapter.ImagesAdapter;
import com.example.now.R;
import com.example.now.UI.Fragments.BrandNameFragment;
import com.example.now.UI.MyOffersActivity;
import com.mohammedalaa.seekbar.DoubleValueSeekBarView;
import com.mohammedalaa.seekbar.OnDoubleValueSeekBarChangeListener;
import com.mohammedalaa.seekbar.RangeSeekBarView;

import org.jetbrains.annotations.Nullable;


public class SelectGenderFragment extends Fragment implements View.OnClickListener{

    private DoubleValueSeekBarView doubleValueSeekBarView;
    private LinearLayout ll_male;
    private LinearLayout ll_female;
    private LinearLayout ll_both;
    private TextView tv_next;
    private LinearLayout back_button;
    private ImageView img_add;
    String range = "30-55";
    String gender = "m";
    String lat;
    String lng;
    String km;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_select_gender, container, false);
        initViews(view);

        doubleValueSeekBarView.setOnRangeSeekBarViewChangeListener(new OnDoubleValueSeekBarChangeListener() {
            @Override
            public void onValueChanged(@Nullable DoubleValueSeekBarView doubleValueSeekBarView, int i, int i1, boolean b) {
                range = i+"-"+i1;
            }

            @Override
            public void onStartTrackingTouch(@Nullable DoubleValueSeekBarView doubleValueSeekBarView, int i, int i1) {

            }

            @Override
            public void onStopTrackingTouch(@Nullable DoubleValueSeekBarView doubleValueSeekBarView, int i, int i1) {

            }
        });

        return view;
    }

    private void initViews(View view) {
        if (getArguments() != null) {
            lat = getArguments().getString("lat");
            lng = getArguments().getString("lng");
            km = getArguments().getString("km");
        }
        doubleValueSeekBarView= view.findViewById(R.id.double_range_seekbar);
        ll_male = view.findViewById(R.id.ll_male);
        ll_male.setOnClickListener(this);
        ll_female = view.findViewById(R.id.ll_female);
        ll_female.setOnClickListener(this);
        ll_both = view.findViewById(R.id.ll_both);
        ll_both.setOnClickListener(this);
        tv_next = view.findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);
        tv_next.setVisibility(View.VISIBLE);
        back_button = view.findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        img_add = view.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.ll_male:
                gender = "m";
                ll_male.setBackground(getActivity().getDrawable(R.drawable.bg_rectangle_white_filled));
                ll_female.setBackground(null);
                ll_both.setBackground(null);
                break;
            case R.id.ll_female:
                gender = "f";
                ll_female.setBackground(getActivity().getDrawable(R.drawable.bg_rectangle_white_filled));
                ll_male.setBackground(null);
                ll_both.setBackground(null);
                break;
            case R.id.ll_both:
                gender = "both";
                ll_both.setBackground(getActivity().getDrawable(R.drawable.bg_rectangle_white_filled));
                ll_male.setBackground(null);
                ll_female.setBackground(null);
                break;
            case R.id.back_button:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_next:
                gotoSelectInterestsFragment();
                break;
        }
    }

    private void gotoSelectInterestsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("gender",gender);
        bundle.putString("range",range);
        bundle.putString("lat",lat);
        bundle.putString("lng",lng);
        bundle.putString("km",km);
        SelectInterestsFragment selectInterestsFragment = new SelectInterestsFragment();
        selectInterestsFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, selectInterestsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}