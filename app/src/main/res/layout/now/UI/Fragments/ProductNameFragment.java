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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.now.Adapter.ImagesAdapter;
import com.example.now.R;

import java.util.ArrayList;


public class ProductNameFragment extends Fragment implements View.OnClickListener {

    ArrayList<Uri> uriArrayList;
    ImagesAdapter imagesAdapter;
    private RecyclerView rv_images;
    private LinearLayout back_button;
    private ImageView img_add;
    private TextView tv_next;
    private EditText et_product_name;
    private EditText et_model_number;
    private String brand = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_name, container, false);
        initViews(view);


        return view;
    }

    private void initViews(View view) {
        if (getArguments() != null) {
            uriArrayList = getArguments().getParcelableArrayList("images");
            brand = getArguments().getString("brand");
            imagesAdapter = new ImagesAdapter(getActivity(), uriArrayList);
            rv_images = view.findViewById(R.id.rv_images);
            rv_images.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            rv_images.setAdapter(imagesAdapter);
        }
        et_product_name = view.findViewById(R.id.et_product_name);
        et_model_number = view.findViewById(R.id.et_model_number);
        back_button = view.findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        img_add = view.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);
        tv_next = view.findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);
        tv_next.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back_button:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_next:
                if(!TextUtils.isEmpty(et_product_name.getText())){
                    gotoOfferFragment();
                }else{
                    Toast.makeText(getActivity(), "Please enter product name", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void gotoOfferFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("images", uriArrayList);
        bundle.putString("product",et_product_name.getText().toString());
        bundle.putString("brand",brand);
        bundle.putString("model_number",et_model_number.getText().toString());
        OfferFragment offerFragment = new OfferFragment();
        offerFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, offerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}