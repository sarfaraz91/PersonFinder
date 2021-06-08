package com.example.now.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.now.Model.Offers;
import com.example.now.Networking.NetworkConstants;
import com.example.now.R;
import com.example.now.UI.Fragments.Marketer.SelectLocationFragment;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context context;
    List<Offers.Datum> offersList;

    public HomeAdapter(Context context, List<Offers.Datum> offersList) {
        this.context = context;
        this.offersList = offersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_items,null);
        return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Offers.Datum oDatum = offersList.get(position);
        holder.tv_type.setText(oDatum.getType());
        holder.tv_product_name.setText(oDatum.getProductName());
        holder.tv_schedule_date.setText(oDatum.getCreatedAt());
        holder.tv_brand_name.setText(oDatum.getBrandName());
        holder.tv_label.setText(oDatum.getLabel());


        if(oDatum.getAttachments().size() > 0){
            Glide.with(context)
                    .load(NetworkConstants.BASE_URL+"/"+oDatum.getAttachments().get(0).getLink())
                    .centerCrop()
                    .placeholder(R.drawable.dummy1)
                    .into(holder.img_offer);
        }


    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_offer;
        TextView tv_schedule_date;
        TextView tv_product_name;
        TextView tv_brand_name;
        TextView tv_type;
        TextView tv_label;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_offer = itemView.findViewById(R.id.img_offer);
            tv_brand_name = itemView.findViewById(R.id.tv_brand_name);
            tv_schedule_date = itemView.findViewById(R.id.tv_schedule_date);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_label = itemView.findViewById(R.id.tv_label);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
