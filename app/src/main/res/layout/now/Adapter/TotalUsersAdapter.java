package com.example.now.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.now.Model.TotalUsers;
import com.example.now.R;

import java.util.List;

public class TotalUsersAdapter extends BaseAdapter {

    Context context;
    List<TotalUsers.Data> totalUsers;

    public TotalUsersAdapter(Context context, List<TotalUsers.Data> totalUsers) {
        this.context = context;
        this.totalUsers = totalUsers;
    }

    @Override
    public int getCount() {
        return totalUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return totalUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TotalUsersAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.total_user_item, null);

            TextView tv_title = convertView.findViewById(R.id.tv_title);
            TextView tv_user_count = convertView.findViewById(R.id.tv_user_count);

            viewHolder = new TotalUsersAdapter.ViewHolder(tv_title, tv_user_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TotalUsersAdapter.ViewHolder) convertView.getTag();
        }


        viewHolder.tv_title.setText(""+totalUsers.get(position).getTitle());
        viewHolder.tv_user_count.setText(""+totalUsers.get(position).getUserCount());


        return convertView;
    }


    public class ViewHolder {
        TextView tv_title;
        TextView tv_user_count;

        public ViewHolder(TextView tv_title,TextView tv_user_count) {
            this.tv_title = tv_title;
            this.tv_user_count = tv_user_count;
        }
    }
}
