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


public class WebsiteNameFragment extends Fragment implements View.OnClickListener {

    ArrayList<Uri> uriArrayList;
    ImagesAdapter imagesAdapter;
    private RecyclerView rv_images;
    private LinearLayout back_button;
    private ImageView img_add;
    private TextView tv_next;
    private EditText et_web_link;
    private EditText et_youtube_link;
    private String brand = "";
    private String product = "";
    private String model_number = "";
    private String offerName = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_website_link, container, false);
        initViews(view);


        return view;
    }

    private void initViews(View view) {
        if (getArguments() != null) {
            uriArrayList = getArguments().getParcelableArrayList("images");
            brand = getArguments().getString("brand");
            product = getArguments().getString("product");
            model_number = getArguments().getString("model_number");
            offerName = getArguments().getString("offerName");
            imagesAdapter = new ImagesAdapter(getActivity(), uriArrayList);
            rv_images = view.findViewById(R.id.rv_images);
            rv_images.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            rv_images.setAdapter(imagesAdapter);
        }
        et_web_link = view.findViewById(R.id.et_web_link);
        et_youtube_link = view.findViewById(R.id.et_youtube_link);
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
                gotoOfferFragment();
                break;
        }
    }

    private void gotoOfferFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("images", uriArrayList);
        bundle.putString("product",product);
        bundle.putString("brand",brand);
        bundle.putString("model_number",model_number);
        bundle.putString("offerName",offerName);
        bundle.putString("web_link",et_web_link.getText().toString());
        bundle.putString("youtube_link",et_youtube_link.getText().toString());
        StoreLocationFragment storeLocationFragment = new StoreLocationFragment();
        storeLocationFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, storeLocationFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}