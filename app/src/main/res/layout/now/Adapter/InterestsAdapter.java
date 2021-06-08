package com.example.now.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.now.Model.Interests;
import com.example.now.R;

import java.util.List;

public class InterestsAdapter extends BaseAdapter {

    Context context;
    List<Interests> interestsList;

    public InterestsAdapter(Context context, List<Interests> interestsList) {
        this.context = context;
        this.interestsList = interestsList;
    }

    @Override
    public int getCount() {
        return interestsList.size();
    }

    @Override
    public Object getItem(int position) {
        return interestsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.interests_item, null);

            ImageView interest_image = convertView.findViewById(R.id.interest_image);
            TextView interest_title = convertView.findViewById(R.id.interest_title);

            RelativeLayout container_img = convertView.findViewById(R.id.container_img);

            viewHolder = new ViewHolder(interest_image, interest_title,container_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context)
                .load(interestsList.get(position).getImage())
                .centerCrop()
                .placeholder(R.drawable.shirt)
                .into(viewHolder.interest_image);

        viewHolder.interest_title.setText(interestsList.get(position).getTitle());


        return convertView;
    }


    public class ViewHolder {
        ImageView interest_image;
        TextView interest_title;
        RelativeLayout container_img;

        public ViewHolder(ImageView interest_image, TextView interest_title,RelativeLayout container_img) {
            this.interest_image = interest_image;
            this.interest_title = interest_title;
            this.container_img = container_img;
        }
    }

}
