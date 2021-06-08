package com.example.now.UI.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.now.Adapter.ImagesAdapter;
import com.example.now.R;

import java.util.ArrayList;


public class BrandNameFragment extends Fragment implements View.OnClickListener {

    ArrayList<Uri> uriArrayList;
    ImagesAdapter imagesAdapter;
    private RecyclerView rv_images;
    private LinearLayout back_button;
    private ImageView img_add;
    private TextView tv_next;
    private EditText et_brand;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand_name, container, false);
        initViews(view);


        return view;
    }

    private void initViews(View view) {
        if (getArguments() != null) {
            uriArrayList = getArguments().getParcelableArrayList("images");
            imagesAdapter = new ImagesAdapter(getActivity(), uriArrayList);
            rv_images = view.findViewById(R.id.rv_images);
            rv_images.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            rv_images.setAdapter(imagesAdapter);
        }
        et_brand = view.findViewById(R.id.et_brand);
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
                if(!TextUtils.isEmpty(et_brand.getText())){
                    gotoProductNameFragment();
                }else{
                    Toast.makeText(getActivity(), "Please enter brand name", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void gotoProductNameFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("images", uriArrayList);
        bundle.putString("brand",et_brand.getText().toString());
        ProductNameFragment productNameFragment = new ProductNameFragment();
        productNameFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, productNameFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}