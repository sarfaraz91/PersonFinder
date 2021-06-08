package com.example.now.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.now.GeneralClasses.OnItemClick;
import com.example.now.R;

import java.util.ArrayList;

public class UploadImagesAdapter extends BaseAdapter {

    Context context;
    OnItemClick onItemClick;

    public UploadImagesAdapter(Context context, OnItemClick onItemClick) {
        this.context = context;
        this.onItemClick = onItemClick;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.upload_iamges_item,null);
        }

        ImageView image = view.findViewById(R.id.image);
        ImageView img_delete = view.findViewById(R.id.img_delete);

        RelativeLayout rl_item = view.findViewById(R.id.rl_item);
        final View finalView = view;
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(position, finalView);
            }
        });
        
        
        return view;
    }

}



