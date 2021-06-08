package com.example.now.UI.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class MoreDetailsFragment extends Fragment implements View.OnClickListener {

    ArrayList<Uri> uriArrayList;
    ImagesAdapter imagesAdapter;
    private RecyclerView rv_images;
    private LinearLayout back_button;
    private ImageView img_add;
    private TextView tv_next;
    private EditText et_moreDetails;
    private String brand = "";
    private String product = "";
    private String model_number = "";
    private String offerName = "";
    private Button btn_preview;
    private double lat = 0;
    private double lng = 0;
    private String web_link = "";
    private String youtube_link = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_details, container, false);
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
            lat = getArguments().getDouble("lat");
            lng = getArguments().getDouble("long");
            web_link = getArguments().getString("web_link");
            youtube_link = getArguments().getString("youtube_link");
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
        tv_next.setVisibility(View.GONE);
        et_moreDetails = view.findViewById(R.id.et_moreDetails);
        btn_preview = view.findViewById(R.id.btn_preview);
        btn_preview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back_button:
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_preview:
                if(!TextUtils.isEmpty(et_moreDetails.getText().toString())){
                    gotoPreviewFragment();
                }else{
                    Toast.makeText(getActivity(), "Please enter more details", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void gotoPreviewFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("images", uriArrayList);
        bundle.putString("product",product);
        bundle.putString("brand",brand);
        bundle.putString("model_number",model_number);
        bundle.putString("offerName",offerName);
        bundle.putString("moreDetails",et_moreDetails.getText().toString());
        bundle.putString("web_link",web_link);
        bundle.putString("youtube_link",youtube_link);
        bundle.putDouble("lat",lat);
        bundle.putDouble("long",lng);
        PreviewFragment previewFragment = new PreviewFragment();
        previewFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, previewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}