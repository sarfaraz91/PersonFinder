package com.example.now.UI.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.now.Adapter.ImagesAdapter;
import com.example.now.R;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.ArrayList;


public class OfferFragment extends Fragment implements View.OnClickListener {

    ArrayList<Uri> uriArrayList;
    ImagesAdapter imagesAdapter;
    private RecyclerView rv_images;
    private LinearLayout back_button;
    private ImageView img_add;
    private TextView tv_next;
    private EditText et_custom_offer;

    private String brand = "";
    private String product = "";
    private String model_number = "";
    private PowerSpinnerView spinner_offer;
    private String offerName = "";
    private boolean isCustomSelected = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer, container, false);
        initViews(view);

        spinner_offer.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
            @Override public void onItemSelected(int oldIndex, @Nullable String oldItem, int newIndex, String newItem) {
                if(newItem.equalsIgnoreCase("custom")){
                    et_custom_offer.setVisibility(View.VISIBLE);
                    isCustomSelected = true;
                    offerName = "";
                }else{
                    isCustomSelected = false;
                    et_custom_offer.setVisibility(View.GONE);
                    offerName = newItem;
                }

            }
        });

        return view;
    }

    private void initViews(View view) {
        if (getArguments() != null) {
            uriArrayList = getArguments().getParcelableArrayList("images");
            brand = getArguments().getString("brand");
            product = getArguments().getString("product");
            model_number = getArguments().getString("model_number");
            imagesAdapter = new ImagesAdapter(getActivity(), uriArrayList);
            rv_images = view.findViewById(R.id.rv_images);
            rv_images.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            rv_images.setAdapter(imagesAdapter);
        }
        back_button = view.findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        img_add = view.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);
        tv_next = view.findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);
        tv_next.setVisibility(View.VISIBLE);
        spinner_offer = view.findViewById(R.id.spinner_offer);
        et_custom_offer = view.findViewById(R.id.et_custom_offer);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back_button:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_next:
                if(validate()){
                    gotoMoreDetailsFragment();
                }else{
                    Toast.makeText(getActivity(), "Please enter offer", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private Boolean validate(){
        Boolean isValidate = false;

        if(isCustomSelected){
            if(!TextUtils.isEmpty(et_custom_offer.getText().toString())){
                offerName = et_custom_offer.getText().toString();
                isValidate = true;
            }
        }else if(!TextUtils.isEmpty(offerName)){
            isValidate = true;
        }

        return isValidate;
    }

    private void gotoMoreDetailsFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("images", uriArrayList);
        bundle.putString("product",product);
        bundle.putString("brand",brand);
        bundle.putString("model_number",model_number);
        bundle.putString("offerName",offerName);
        WebsiteNameFragment websiteNameFragment = new WebsiteNameFragment();
        websiteNameFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, websiteNameFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}