package com.example.now.UI.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.now.Adapter.ImagesAdapter;
import com.example.now.Adapter.UploadImagesAdapter;
import com.example.now.GeneralClasses.OnItemClick;
import com.example.now.R;
import com.example.now.UI.MyOffersActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

public class UploadImagesFragment extends Fragment implements View.OnClickListener, OnItemClick {

    private ImageView logo_iv;
    private ImageView img_add;
    private TextView tv_next;
    private LinearLayout back_button;
    private GridView gv_upload_iamges;
    private UploadImagesAdapter UploadImagesAdapter;
    private RecyclerView rv_images;
    private ImagesAdapter imagesAdapter;
    private ArrayList<Uri> uriArrayList;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_images, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        uriArrayList = new ArrayList<>();
        imagesAdapter = new ImagesAdapter(getActivity(),uriArrayList);
        rv_images = view.findViewById(R.id.rv_images);
        rv_images.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_images.setAdapter(imagesAdapter);
        UploadImagesAdapter = new UploadImagesAdapter(getActivity(), this);
        gv_upload_iamges = view.findViewById(R.id.gv_upload_iamges);
        gv_upload_iamges.setAdapter(UploadImagesAdapter);
        tv_next = view.findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);
        tv_next.setVisibility(View.VISIBLE);
        back_button = view.findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        logo_iv = view.findViewById(R.id.logo_iv);
        logo_iv.setVisibility(View.GONE);
        img_add = view.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back_button:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_next:
                if(uriArrayList.size() > 0){
                    gotoBrandNameFragment();
                }else{
                    Toast.makeText(getActivity(), "Please upload atleast one image", Toast.LENGTH_SHORT).show();
                }
                
                break;
        }
    }

    private void gotoBrandNameFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("images", uriArrayList);
        BrandNameFragment brandNameFragment = new BrandNameFragment();
        brandNameFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, brandNameFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(int position, View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(getContext(), this);

        this.view = view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                setImage(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void setImage(final Uri uri) {
        if (this.view != null) {
            final ImageView image = this.view.findViewById(R.id.image);
            final ImageView img_delete = this.view.findViewById(R.id.img_delete);
            if(uri != null){
                image.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(uri)
                        .centerCrop()
                        .into(image);
                img_delete.setVisibility(View.VISIBLE);
                uriArrayList.add(uri);
                imagesAdapter.notifyDataSetChanged();
            }
            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    image.setVisibility(View.GONE);
                    img_delete.setVisibility(View.GONE);
                    uriArrayList.remove(uri);
                    imagesAdapter.notifyDataSetChanged();
                }
            });
        }

    }


}