package com.example.personfinder.ui.person_listing.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personfinder.R;
import com.example.personfinder.ui.person_listing.model.PersonsData;

import java.util.List;

public class PersonsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity activity;
    private List<PersonsData> personsDataList;
    private PersonsListAdapter.OnItemClickListener listener;
    private Context context;

    public PersonsListAdapter(Activity activity, Context context, List<PersonsData> personsDataList) {
        this.personsDataList = personsDataList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.persons_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            populateItemRows((ViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void populateItemRows(ViewHolder holder, int position) {
        PersonsData personsData = personsDataList.get(position);


        try {
            String vendorTitle = personsData.getName().substring(0, 1).toUpperCase() + personsData.getName().substring(1).toLowerCase();
            holder.vendorTitle.setText(vendorTitle);
            holder.companyName.setText(personsData.getCompanyName());
            holder.email.setText(personsData.getEmail());
            holder.contact.setText(personsData.getContact());
            holder.status.setText(personsData.getIsActiveText());

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    public int getItemCount() {
        return personsDataList == null ? 0 : personsDataList.size();
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
        viewHolder.progressBar.setVisibility(View.VISIBLE);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView vendorTitle, companyName, email, contact, status;
        ImageView editIcon, deleteIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            vendorTitle = itemView.findViewById(R.id.vendorTitle);
            companyName = itemView.findViewById(R.id.companyName);
            email = itemView.findViewById(R.id.email);
            contact = itemView.findViewById(R.id.contact);
            status = itemView.findViewById(R.id.status);
        }
    }
}
